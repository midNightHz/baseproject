package cn.zb.commoms.file.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class FileConstants {

    private FileConstants() {
    }

    /**
     * 
     */
    private static final Map<String, String> PIC_CONTENT_TYPES = new HashMap<>();

    private static final String DEFAULT_PIC_CONTENT_TYPE = "image/jpeg";

    static {
        PIC_CONTENT_TYPES.put(".png", "image/png");
        PIC_CONTENT_TYPES.put(".tif", "image/tiff");
        PIC_CONTENT_TYPES.put(".tiff", "image/tiff");
        PIC_CONTENT_TYPES.put(".jfif", "image/jpeg");
        PIC_CONTENT_TYPES.put(".jpeg", "image/jpeg");
        PIC_CONTENT_TYPES.put(".jpe", "image/jpeg");
        PIC_CONTENT_TYPES.put(".jpg", "image/jpeg");
        PIC_CONTENT_TYPES.put(".gif", "image/gif");
        PIC_CONTENT_TYPES.put(".tif", "image/tiff");
        PIC_CONTENT_TYPES.put(".fax", "image/fax");
    }

    public static final String getPicContentType(String picSuffix) {
        String contentType = PIC_CONTENT_TYPES.get(picSuffix);
        if (StringUtils.isBlank(contentType)) {
            return DEFAULT_PIC_CONTENT_TYPE;
        }
        return contentType;
    }

}
