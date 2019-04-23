package cn.zb.mall.news.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zb.base.annotation.DefaultValue;
import cn.zb.base.annotation.FieldName;
import cn.zb.base.annotation.NotNull;
import cn.zb.base.entity.BaseEntity;

/**
 * 
 * @ClassName:  News   
 * @Description:新闻公告实体类  
 * @author: 陈军
 * @date:   2019年4月17日 上午11:06:20   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Entity
@Table(name="UMGNEWS")
public class News implements BaseEntity<Integer> {

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */
    private static final long serialVersionUID = -4484574462905713114L;
    @Id
    @GeneratedValue
    protected Integer newsid;
    
    @NotNull
    @FieldName("公告标题")
    protected String newstitle;
    
    @NotNull
    @FieldName("公告类型")
    protected Short newstype;
    
    @DefaultValue("0")
    @FieldName("质量类型")
    protected Short newstype2;
    @NotNull
    @FieldName("公告标题")
    protected String newscontent;
    
    protected Integer newsstatus;
    
    
    @Column(updatable=false)
    protected Date newstime;
    
    protected Integer creater;
    
    protected String newssrc;
    
    protected String writer;

    @Override
    public Integer getId() {
        return newsid;
    }

    @Override
    public void setId(Integer id) {
        this.newsid = id;
    }

    public Integer getNewsid() {
        return newsid;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public Short getNewstype() {
        return newstype;
    }

    public Short getNewstype2() {
        return newstype2;
    }

    public String getNewscontent() {
        return newscontent;
    }

    public Integer getNewsstatus() {
        return newsstatus;
    }

    public Date getNewstime() {
        return newstime;
    }

    public Integer getCreater() {
        return creater;
    }

    public String getNewssrc() {
        return newssrc;
    }

    public String getWriter() {
        return writer;
    }

    public void setNewsid(Integer newsid) {
        this.newsid = newsid;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public void setNewstype(Short newstype) {
        this.newstype = newstype;
    }

    public void setNewstype2(Short newstype2) {
        this.newstype2 = newstype2;
    }

    public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

    public void setNewsstatus(Integer newsstatus) {
        this.newsstatus = newsstatus;
    }

    public void setNewstime(Date newstime) {
        this.newstime = newstime;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public void setNewssrc(String newssrc) {
        this.newssrc = newssrc;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
    
    

}
