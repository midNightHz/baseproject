package cn.zb.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;

/**
 * 文件读写工具类
 * 
 * @author chen
 *
 */
public class FileUtils {

    /**
     * 1kb缓存
     */
    public final static int KB_1 = 1024;
    /**
     * 4kb缓存
     */
    public final static int KB_4 = 1024 * 4;

    /**
     * 
     * title: Description:将文件写入输出流
     * 
     * @param fileName
     * @param os
     * @author chen.jun
     * @throws Exception
     * @date Created in 2017年12月1日 上午9:52:25
     */
    public static void outFile(String fileName, OutputStream os) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("文件名为空");
        }
        File file = new File(fileName);
        outFile(file, os);
    }

    /**
     * 
     * title: Description:将文件写入输出流
     * 
     * @param file
     * @param os
     * @author chen.jun
     * @throws Exception
     * @date Created in 2017年12月1日 上午9:52:45
     */
    public static void outFile(File file, OutputStream os) throws Exception {
        if (file == null || !file.exists()) {
            throw new Exception("文件不存在");
        }
        if (os == null) {
            throw new Exception("输出流为空");
        }
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            byte[] buffer = new byte[KB_1];
            int length;
            while ((length = input.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            os.close();
            if (input != null) {
                input.close();
            }
        }

    }

    /**
     * 
     * title: Description:判断文件是否存在
     * 
     * @param fileName
     * @return
     * @author chen.jun
     * @date Created in 2017年12月1日 上午9:55:26
     */
    public static boolean isFile(String fileName) {
        File file = new File(fileName);
        return isFile(file);
    }

    /**
     * 
     * title: Description:判断文件是否存在
     * 
     * @param file
     * @return
     * @author chen.jun
     * @date Created in 2017年12月1日 上午10:15:25
     */
    public static boolean isFile(File file) {
        return file.isFile();
    }

    /**
     * 
     * title: Description:新建文件，若文件目录不存在，则会逐级创建目录
     * 
     * @param filePathName
     * @return
     * @author chen.jun
     * @throws Exception
     * @date Created in 2017年12月1日 上午10:42:53
     */
    public static File createNewFile(String filePathName) throws Exception {
        String filePath = getFullPath(filePathName);
        // 若目录不存在，则建立目录
        if (!exists(filePath)) {
            if (!createDirectory(filePath)) {
                throw new IOException("文件目录创建失败");
            }
        }
        File file = null;
        try {
            file = new File(filePathName);
            file.createNewFile();
        } catch (IOException ex) {
            throw new IOException("文件创建失败");
        }
        return file;
    }

    /**
     * 
     * title: Description:新建文件，若文件目录不存在，则会逐级创建目录
     * 
     * @param file
     * @throws Exception
     * @author chen.jun
     * @date Created in 2017年12月5日 上午10:07:48
     */
    public static void createNewFile(File file) throws Exception {
        if (file.exists()) {
            return;
        }
        String fileName = file.getPath();
        createNewFile(fileName);
    }

    /**
     * 
     * title: Description:获取文件路径名 包含最后的分隔符
     * 
     * @param filename
     * @return
     * @author chen.jun
     * @date Created in 2017年12月1日 上午10:45:02
     */
    public static String getFullPath(String filename) {
        return FilenameUtils.getFullPath(filename);
    }

    /**
     * 
     * title: Description:判断文件目录是否存在
     * 
     * @param filePath
     * @return
     * @author chen.jun
     * @date Created in 2017年12月1日 上午10:44:26
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 
     * title: Description:递归创建目录
     * 
     * @param directory
     * @return
     * @author chen.jun
     * @date Created in 2017年12月1日 上午10:43:29
     */
    public static boolean createDirectory(String directory) {
        try {
            org.apache.commons.io.FileUtils.forceMkdir(new File(directory));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            // System.err.println("创建目录出错");
        }
        return false;
    }

    /**
     * 
     * title: Description:拼接文件路径 包含文件最后一个分隔符
     * 
     * @param strs
     * @return
     * @author chen.jun
     * @date Created in 2017年12月5日 上午9:26:08
     */
    public static String spellFilePath(String... strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str + File.separator);
        }
        return sb.toString();
    }

    /**
     * 
     * title: Description:在本地保存上传文件 保存完成后关闭输入流
     * 
     * @param is
     * @throws Exception
     * @author chen.jun
     * @date Created in 2017年12月18日 上午11:08:51
     */
    public static void saveUploadFile(InputStream is, String fullName) throws Exception {

        if (StringUtils.isEmpty(fullName)) {
            throw new Exception("文件名不能为空");
        }

        File file = createNewFile(fullName);
        OutputStream os = new FileOutputStream(file);
        try {

            byte[] buffer = new byte[KB_1];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            os.close();
            // if (is != null) {
            // is.close();
            // }
        }
    }

    public static void delFile(String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("文件名不能为空");
        }
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 校验文件后缀是否和文件名一致 --千万不要使用
     * 
     * @param file
     * @return
     */
    @Deprecated
    public boolean checkFileSuffix(File file) {
        return false;
    }

    /**
     * 文件下载
     * 
     * @param url
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String downLoadFile(String fileUrl, String filePath) throws Exception {

        String fileName = fileUrl.substring(0, fileUrl.indexOf("?") < 0 ? fileUrl.length() : fileUrl.indexOf("?"));
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        String fullName = filePath + '/' + fileName;
        if (!isFile(fullName)) {
            createNewFile(fullName);
        }
        URL url = null;
        url = new URL(fileUrl);
        URLConnection connection = url.openConnection();

        DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());

        FileOutputStream fileOutputStream = new FileOutputStream(new File(fullName));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = dataInputStream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        fileOutputStream.write(output.toByteArray());
        dataInputStream.close();
        fileOutputStream.close();
        return fullName;
    }

    // public static void main(String[] args) throws Exception {
    // downLoadFile("https://alibabagroup.com/cn/news/press_pdf/p180529a.pdf", "../../img/test");
    // }

    /**
     * 文件的下载
     * 
     * @param fileUrl
     * @param filePath
     * @param suffix
     * @return
     * @throws Exception
     */
    public static String downLoadFile(String fileUrl, String filePath, String suffix) throws Exception {

        URL url = null;
        url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        Map<String, List<String>> heads = connection.getHeaderFields();
        String fileName = "";
        if (heads.containsKey("Content-Disposition")) {
            fileName = heads.get("Content-Disposition").toString();
            fileName = fileName.substring(fileName.indexOf("\"") + 1, fileName.lastIndexOf("\""));
        } else {
            fileName = System.currentTimeMillis() + "." + suffix;
        }
        // 从相应体中获取文件名

        String fullName = filePath + '/' + fileName;
        if (!isFile(fullName)) {
            createNewFile(fullName);
        }
        DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());

        FileOutputStream fileOutputStream = new FileOutputStream(new File(fullName));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = dataInputStream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        fileOutputStream.write(output.toByteArray());
        dataInputStream.close();
        fileOutputStream.close();
        return fullName;
    }
    
    /**
	 * 将字符串写入到文件中
	 * 
	 * @param str
	 *            待写入字符串
	 * @param fileName
	 *            //文件名
	 * @param append
	 *            //是否在文件末尾添加
	 * @param charset
	 *            编码方式
	 */
	public static void writeStringToFile(String str, String fileName, Boolean append, Charset charset) {
		if (fileName == null || fileName.trim().length() == 0) {
			return;
		}
		Writer writer = null;
		if (append == null) {
			append = false;
		}
		if (charset == null) {
			charset = Charset.forName("utf-8");
		}
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				createNewFile(file);
			}
			// writer = new FileWriter(file);
			// writer = new BufferedWriter(new OutputStreamWriter(new
			// FileOutputStream(file), Charset.forName("utf-8")));
			writer = new FileWriterWithEncoding(file, charset, append);
			writer.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
