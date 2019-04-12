package cn.zb.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * @ClassName:  QRCoderCreator   
 * @Description:二维码生成工具 
 * @author: 陈军
 * @date:   2019年1月28日 下午4:48:21   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class QRCoderCreator {

    /**
     * 默认宽
     */
    private static final int DEFAULT_WIDTH = 600;

    /**
     * 默认高
     */
    private static final int DEFAULT_HEIGHT = 600;
    /**
     * 默认图片的格式
     */
    private static final String DEFAULT_IMAGE_TYPE = "png";
    /**
     * log所占的比例
     */
    private static double LOGO_RATIO = 0.25;
    /**
     * 生成二维码宽
     */
    private int width;

    /**
     * 生成二维码高
     */
    private int height;

    /**
     * 生成二维码信息
     */
    private String message;
    /**
     * 生成二维码类型
     */
    private String imageType;
    /**
     * 生成二维码路径
     */
    private String imagePath = "../qrcode/" + System.currentTimeMillis() + ".png";
    /**
     * 是否生成带logo图片的二维码
     */
    private boolean hasLog = true;
    /**
     * 
     */
    private double logoRatio = LOGO_RATIO;
    /**
     * 纠错等级
     */
    private ErrorCorrectionLevel level;

    /**
     * logo是否设置为圆形
     */
    private boolean circleLogo = false;

    /**
     * logo图片,目前只支持网络图片
     */
    private String logoUrl;

    public void setCircleLogo(boolean circleLogo) {
        this.circleLogo = circleLogo;
    }

    public void setLevel(ErrorCorrectionLevel level) {
        this.level = level;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLogoRatio(double logoRatio) {
        this.logoRatio = logoRatio;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setHasLog(boolean hasLog) {
        this.hasLog = hasLog;
    }

    public QRCoderCreator(String message) {
        this.message = message;
        // this.imagePath = imagePath;
        this.imageType = DEFAULT_IMAGE_TYPE;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        setLevel(ErrorCorrectionLevel.H);
    }

    /**
     * logo比例
     */

    public QRCoderCreator(String message, String imagePath) {
        this.message = message;
        this.imagePath = imagePath;
        this.imageType = DEFAULT_IMAGE_TYPE;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        setLevel(ErrorCorrectionLevel.H);
    }

    public QRCoderCreator(String message, String imagePath, int width, int height) {
        this.message = message;
        this.imagePath = imagePath;
        this.imageType = DEFAULT_IMAGE_TYPE;
        this.width = width;
        this.height = height;
        setLevel(ErrorCorrectionLevel.H);
    }

    public QRCoderCreator(String message, String imagePath, int size) {
        this.message = message;
        this.imagePath = imagePath;
        this.imageType = DEFAULT_IMAGE_TYPE;
        this.width = size;
        this.height = size;
        setLevel(ErrorCorrectionLevel.H);
    }

    public QRCoderCreator(String message, String imagePath, int width, int height, String imageType) {
        this.message = message;
        this.imagePath = imagePath;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
        setLevel(ErrorCorrectionLevel.H);
    }

    /**
     * 生成二维码
     * 
     * @throws Exception
     */
    public boolean creatQRCode() throws Exception {
        if (hasLog && StringUtils.isNotBlank(logoUrl)) {
            return creatHasLogoQRCode();
        }
        // 设置二维码纠错级别ＭＡＰ
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, level); // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height, hintMap);
        // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int matrixHight = byteMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, matrixHight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        if (!FileUtils.exists(imagePath)) {
            // FileUtil.delFile(imagePath);
            FileUtils.createNewFile(imagePath);
        }
        OutputStream outputStream = new FileOutputStream(imagePath);
        return ImageIO.write(image, imageType, outputStream);
    }

    public boolean creatQRCode(OutputStream outputStream) throws Exception {
        // 设置二维码纠错级别ＭＡＰ
        if (hasLog && StringUtils.isNotBlank(logoUrl)) {
            return createHasLogoQRCode(outputStream);
        }
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, level); // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height, hintMap);
        // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int matrixHight = byteMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, matrixHight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        return ImageIO.write(image, imageType, outputStream);
    }

    private boolean createHasLogoQRCode(OutputStream outputStream) throws Exception {

        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, level); // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height, hintMap);
        // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int matrixHight = byteMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, matrixHight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        drawLogo();

        return ImageIO.write(image, imageType, outputStream);
    }

    private Graphics2D graphics;

    /**
     * 
     * @description 生成带logo的二维码
     * @return
     * @throws Exception
     *             author：chenjun
     * @date ：2018年9月3日 下午1:38:31
     * @return boolean
     */
    private boolean creatHasLogoQRCode() throws Exception {

        // 设置二维码纠错级别ＭＡＰ
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, level); // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height, hintMap);
        // 使BufferedImage勾画QRCode (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        int matrixHight = byteMatrix.getHeight();
        BufferedImage image = new BufferedImage(matrixWidth, matrixHight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        drawLogo();
        if (!FileUtils.exists(imagePath)) {
            // FileUtil.delFile(imagePath);
            FileUtils.createNewFile(imagePath);
        }
        OutputStream outputStream = new FileOutputStream(imagePath);
        return ImageIO.write(image, imageType, outputStream);
    }

    private static Logger logger = LoggerFactory.getLogger(QRCoderCreator.class);

    private void drawSquareLogo() {
        try {
            BufferedImage logo = ImageIO.read(new URL(logoUrl));
            // 图片的长和款
            int imageWidth = logo.getWidth();
            int imageHeigh = logo.getHeight();
            int max = imageWidth > imageHeigh ? imageWidth : imageHeigh;
            // 绘制logo
            int logoWidth = (int) (width * logoRatio * imageWidth / max);
            int logoHeight = (int) (height * logoRatio * imageHeigh / max);

            int x = width / 2 - logoWidth / 2;
            int y = height / 2 - logoHeight / 2;
            graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
        } catch (Exception e) {
            logger.error("获取logo异常" + logoUrl);
            return;
        }
    }

    private void drawLogo() {
        if (circleLogo) {
            drawCircleLogo();
            return;
        }
        drawSquareLogo();
    }

    private void drawCircleLogo() {
        try {
            BufferedImage logo = ImageIO.read(new URL(logoUrl));
            // 绘制logo
            int logoWidth = (int) (width * logoRatio);
            int logoHeight = (int) (height * logoRatio);
            int x = width / 2 - logoWidth / 2;
            int y = height / 2 - logoHeight / 2;
            logo = scaleByPercentage(logo, logoWidth, logoHeight);
            logo = convertCircular(logo);
            graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
        } catch (Exception e) {
            logger.error("获取logo异常" + logoUrl);
        }

    }

    /**
     * 
     * @description 将图片修改成未正方形
     * @param inputImage
     * @param newWidth
     * @param newHeight
     * @return
     * @throws Exception
     *             author：chenjun
     * @date ：2018年9月4日 下午6:02:48
     * @return BufferedImage
     */
    private BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
        // 获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        // 开启抗锯齿
        RenderingHints renderingHints =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 
     * @description 裁剪成正圆形
     * @param imageIn
     * @return
     * @throws IOException
     *             author：chenjun
     * @date ：2018年9月4日 下午6:03:16
     * @return BufferedImage
     */
    private BufferedImage convertCircular(BufferedImage imageIn) throws IOException {
        // 透明底的图片
        BufferedImage image = new BufferedImage(imageIn.getWidth(), imageIn.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, imageIn.getWidth(), imageIn.getHeight());
        Graphics2D g2 = image.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(imageIn, 0, 0, null);
        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return image;
    }

    /**
     * 
     * 生成动态大小的二维码
     * 
     * @return
     * @throws Exception
     */
    public boolean creatDynamicSizeQRCode() throws Exception {
        int lenth = message.length();
        int size = (lenth / 10 + 1) * 100 + 100;
        this.width = size;
        this.height = size;
        if (hasLog && StringUtils.isNotBlank(logoUrl))
            return creatHasLogoQRCode();
        return creatQRCode();
    }

    public boolean creatDynamicSizeQRCode(OutputStream os) throws Exception {
        int lenth = message.length();
        int size = (lenth / 10 + 1) * 100 + 100;
        this.width = size;
        this.height = size;
        if (hasLog && StringUtils.isNotBlank(logoUrl))
            return createHasLogoQRCode(os);
        return creatQRCode(os);
    }

    public String toBase64Image() throws Exception {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();

            creatQRCode(os);
            byte[] bytes = Base64.encodeBase64(os.toByteArray(), true);
            return "data:image/png;base64," + new String(bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String toDynamicSizeBase64Image() throws Exception {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();

            creatDynamicSizeQRCode(os);
            byte[] bytes = Base64.encodeBase64(os.toByteArray(), true);
            return "data:image/png;base64," + new String(bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        QRCoderCreator c=new QRCoderCreator("https://blog.csdn.net/qq_36330296/article/details/81034683");
        c.setLogoUrl("https://img-blog.csdn.net/20170417190514200?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbm9hbWFuX3dncw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast");
        c.setHasLog(true);
        c.setCircleLogo(true);
        c.creatQRCode();
    }

}
