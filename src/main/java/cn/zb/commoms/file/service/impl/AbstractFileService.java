package cn.zb.commoms.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import cn.zb.commoms.file.service.IFileService;
import cn.zb.utils.FileUtils;
import net.coobird.thumbnailator.Thumbnails;

public abstract class AbstractFileService  implements IFileService {
    
    
    
    @Override
    public void downPic(String fileName, Integer size, OutputStream out, Double q) throws Exception {
        if (size == null || size <= 0) {
            downPic(fileName, out);
            return;
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }

        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();

        if (size >= width) {
            downPic(fileName, out);
            return;
        }
        // 获取缩放比例
        float scal = 1.0f * size / width;
        Thumbnails.of(file).scale(scal).outputQuality(q == null ? 1.0 : q).toOutputStream(out);

    }

    @Override
    public void downPic(String fileName, OutputStream out) throws Exception {
        FileUtils.outFile(new File(fileName), out);
    }

}
