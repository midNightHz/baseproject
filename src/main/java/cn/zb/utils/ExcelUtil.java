package cn.zb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /*
     * public static String getCellTimeValue(Cell cell) { if (cell == null) { return ""; } DecimalFormat df = new
     * DecimalFormat("#");
     * 
     * String cellValue = ""; switch (cell.getCellTypeEnum()) { case STRING: cellValue =
     * cell.getRichStringCellValue().getString().trim(); break; case NUMERIC: if
     * (HSSFDateUtil.isCellDateFormatted(cell)) { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())); } else { cellValue =
     * df.format(cell.getNumericCellValue()); } break; default: cellValue = ""; }
     * 
     * return cellValue; }
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {
        case STRING:
            cellValue = cell.getRichStringCellValue().getString().trim();
            break;
        case NUMERIC:
            HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
            cellValue = dataFormatter.formatCellValue(cell);

            break;
        case BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
            break;
        case FORMULA:
            cellValue = cell.getStringCellValue();
            break;
        default:
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 
     * @Title: readDatas   
     * @Description: 读取excel表格的内容 
     * @author:陈军
     * @date 2019年1月21日 下午1:17:32 
     * @param sheet
     * @param startIndex
     * @param fields
     * @return
     * @throws Exception      
     * List<Map<String,String>>      
     * @throws
     */
    public static List<Map<String, String>> readDatas(Sheet sheet, int startIndex, List<String> fields) {
        int rows = sheet.getLastRowNum();// 从0 开始 0 123

        if (rows <= startIndex) {
            return null;
        }
        List<Map<String, String>> datas = new ArrayList<>();
        for (int i = startIndex; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = readDatas(fields, row, startIndex);
            if (map != null)
                datas.add(map);
        }
        return datas;

    }

    /**
     * 
     * @Title: readDatas   
     * @Description: 读取excel行内容
     * @author:陈军
     * @date 2019年1月21日 下午1:18:13 
     * @param fields
     * @param row
     * @return
     * @throws Exception      
     * Map<String,String>      
     * @throws
     */
    public static Map<String, String> readDatas(List<String> fields, Row row, int startIndex) {
        int maxCellNum = row.getLastCellNum();// 1 2 3 4
        int fieldSize = fields.size();
        maxCellNum = fieldSize < maxCellNum ? fieldSize : maxCellNum;
        Map<String, String> map = new HashMap<>();
        for (int i = startIndex; i < maxCellNum; i++) {
            Cell cell = row.getCell(i);
            map.put(fields.get(i), getCellValue(cell));
            if (logger.isDebugEnabled()) {
                logger.debug("index:" + i + "datas:" + map);
            }
        }
        return map;

    }

    /**
     * 
     * @Title: readDatas   
     * @Description: 读取excel表格中的数据  
     * @author:陈军
     * @date 2019年2月1日 下午2:11:24 
     * @param is 文件输入流
     * @param startIndex 开始读取的行
     * @param fields 读取字段映射关系
     * @param sheetIndex 表格的序列
     * @return
     * @throws IOException
     * @throws InvalidFormatException      
     * List<Map<String,String>>      
     * @throws
     */
    public static List<Map<String, String>> readDatas(InputStream is, int startIndex, List<String> fields,
            int sheetIndex) throws IOException, InvalidFormatException {

        if (logger.isDebugEnabled()) {
            logger.debug("index：" + startIndex);
            logger.debug("sheet:" + sheetIndex);
            logger.debug("fields:" + fields);
        }

        Workbook workBook = WorkbookFactory.create(is);

        Sheet sheet = workBook.getSheetAt(sheetIndex);

        return readDatas(sheet, startIndex, fields);
    }

    /**
     * 
     * @Title: readDatasToObj   
     * @Description: 读取excel表格中的数据  并实例化成为对象  
     * @author:陈军
     * @date 2019年2月1日 下午2:12:48 
     * @param is 文件输入流
     * @param startIndex 开始读取的行
     * @param fields 字段映射
     * @param sheetIndex 表格的序列
     * @param currClass //类名
     * @return
     * @throws EncryptedDocumentException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException      
     * List<T>      
     * @throws
     */
    public static <T> List<T> readDatasToObj(InputStream is, int startIndex, List<String> fields, int sheetIndex,
            Class<T> currClass)
            throws EncryptedDocumentException, IOException, InstantiationException, IllegalAccessException {
        Workbook workBook = WorkbookFactory.create(is);

        Sheet sheet = workBook.getSheetAt(sheetIndex);

        return readDatasToObj(sheet, startIndex, fields, currClass);

    }

    /**
     * 
     * @Title: readDatasToObj   
     * @Description: 读取excel表格并实例化成对象  
     * @author:陈军
     * @date 2019年2月1日 下午2:17:04 
     * @param sheet
     * @param startIndex
     * @param fields
     * @param currClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException      
     * List<T>      
     * @throws
     */
    public static <T> List<T> readDatasToObj(Sheet sheet, int startIndex, List<String> fields, Class<T> currClass)
            throws InstantiationException, IllegalAccessException {
        int rows = sheet.getLastRowNum();// 从0 开始 0 123

        if (rows <= startIndex) {
            return null;
        }
        List<T> datas = new ArrayList<>();
        for (int i = startIndex; i <= rows; i++) {
            Row row = sheet.getRow(i);
            datas.add(readDataToObj(fields, row, currClass, startIndex));
        }
        return datas;
    }
    /**
     * 
     * @Title: readDataToObj   
     * @Description: 读取excel行并实例化成对象
     * @author:陈军
     * @date 2019年2月1日 下午2:17:39 
     * @param fields
     * @param row
     * @param currClass
     * @param startIndex
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException      
     * T      
     * @throws
     */
    public static <T> T readDataToObj(List<String> fields, Row row, Class<T> currClass, int startIndex)
            throws InstantiationException, IllegalAccessException {
        Map<String, String> map = readDatas(fields, row, startIndex);
        return JSON.parseObject(JSONObject.toJSONString(map), currClass);
    }

}
