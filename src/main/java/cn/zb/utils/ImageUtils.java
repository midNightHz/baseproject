package cn.zb.utils;

import java.awt.image.BufferedImage;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {

    public static void savePic(BufferedImage image, Integer width, String fileName, Double quality) throws Exception {
        double scale = 1.0;
        int imageWidth = image.getWidth();
        if(!FileUtils.exists(fileName)) {
                FileUtils.createNewFile(fileName);
        }
        if (width != null && imageWidth > width) {
            scale *= width;
            scale /= imageWidth;

        }
        Thumbnails.of(image).scale(scale).outputQuality(quality == null ? 1.0 : quality).toFile(fileName);

    }

}
