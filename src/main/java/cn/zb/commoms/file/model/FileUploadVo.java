package cn.zb.commoms.file.model;


/**
 * 
 * @ClassName:  PicUpdateVo   
 * @Description:图片上传返回值model   
 * @author: 陈军
 * @date:   2019年5月22日 上午8:21:07   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class FileUploadVo {
    
    /**
     * 图片的访问路径
     */
    private String src;
    
    /**
     * 图片的文件名
     */
    private String title;

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public FileUploadVo() {
    }

    public FileUploadVo(String src, String title) {
        super();
        this.src = src;
        this.title = title;
    }
    
    
    
    
    

}
