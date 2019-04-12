package cn.zb.commoms.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;

import cn.zb.base.entity.SynEntity;

@Entity
@Table(name = "person")
@DynamicUpdate
public class User implements SynEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8059420730923900919L;
	// 用户id
	@Id
	@GeneratedValue
	protected Integer id;

	protected Integer pLsm;
	// 用户名

	@Column(length = 100)
	protected String pName;
	// 用户编号

	@Column(length = 100)
	protected String workNo;
	// 用户密码

	@Column(length = 100)
	protected String password;
	// 用户状态
	protected Short pState;
	// 用户权限等级
	protected Short pLever;
	// 用户类型
	protected Integer pType;
	@Transient
	@Column(updatable = false, insertable = false)
	protected String roles;

	@Column(length = 50)
	protected String spellcode;

	protected Integer rid;
	// 联系方式

	@Column(length = 100)
	protected String linkcall;

	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	@Column(insertable = false, updatable = false)
	// 最后登录日期不进行修改
	protected Date lastlogintime;
	
	// 电子邮件
	@Column(length = 100)
	protected String email;

	// 通讯地址
	@Column(length = 200)
	protected String postaddress;
	// 联系qq

	@Column(length = 20)
	protected String linkqq;
	
	protected String corpcode;
	// 是否有效期

	@Column(nullable = false)
	protected Integer vtype;
	// 用户起效开始时间
	@JSONField(format = "yyyy-MM-dd")
	protected Date vsdate;
	// 用户起效结束时间
	@JSONField(format = "yyyy-MM-dd")
	protected Date vedate;

	@Column(length = 100)
	protected String registermemo;

	// 是否接受短信

	@Column(nullable = false)
	protected Integer allowsm;
	// 登录类型

	@Column(nullable = false)
	protected Integer logintype;
	// 是否接受短信认证登录
	protected Integer loginsm;

	@Column(nullable = false, columnDefinition = "bit default 0")
	protected Short isSyn;

	@Column(length = 100)
	protected String handphone;

	@Column(length = 50)
	protected String openid;

	protected Date smscodeexpired;
	// 浏览采购页面id
	/*
	 * 
	 * 
	 * protected Integer isagreecontract;
	 * 
	 * ;
	 */
	protected Integer isagreecontract;
	protected Integer pfId;

	@Column(nullable = false)
	protected Integer orderui;

	@Column(length = 50)
	protected String linkman;

	protected Integer deptNo;

	protected Integer buytype;

	protected Integer ischoose;
	// 主题id
	@Column(nullable = false)
	protected Integer themeid;
	// 检索码类型

	@Column(nullable = false)
	protected Integer spelltype;
	@Column(nullable = false)
	protected Integer parentcorp;

	@Column(nullable = false)
	protected Integer isadmin;
	@Column(nullable = false)
	protected Integer recommendpid;
	@Column(nullable = false)
	protected Integer isbind;

	protected Integer recommenduid;

	@Column(length = 400)
	protected String sig;

	@Column(length = 50, nullable = false)
	protected String pPhoto;

	@Column(length = 500, nullable = false)
	protected String pIntro;

	protected Double curPoint;

	protected Double dhPoint;

	/*
	 * public Integer getIsagreecontract() { return isagreecontract; }
	 * 
	 * public void setIsagreecontract(Integer isagreecontract) {
	 * this.isagreecontract = isagreecontract; }
	 * 
	 * public Integer getPfId() { return pfId; }
	 * 
	 * public void setPfId(Integer pfId) { this.pfId = pfId; }
	 */

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public Integer getPType() {
		return pType;
	}

	public void setPType(Integer pType) {
		this.pType = pType;
	}

	public String getSpellcode() {
		return spellcode;
	}

	public void setSpellcode(String spellcode) {
		this.spellcode = spellcode;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpLsm() {
		return pLsm;
	}

	public void setpLsm(Integer pLsm) {
		this.pLsm = pLsm;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getpState() {
		return pState;
	}

	public void setpState(Short pState) {
		this.pState = pState;
	}

	public Short getpLever() {
		return pLever;
	}

	public void setpLever(Short pLever) {
		this.pLever = pLever;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getLinkcall() {
		return linkcall;
	}

	public void setLinkcall(String linkcall) {
		this.linkcall = linkcall;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostaddress() {
		return postaddress;
	}

	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}

	public String getLinkqq() {
		return linkqq;
	}

	public void setLinkqq(String linkqq) {
		this.linkqq = linkqq;
	}

	public Integer getVtype() {
		return vtype;
	}

	public void setVtype(Integer vtype) {
		this.vtype = vtype;
	}

	public Date getVsdate() {
		return vsdate;
	}

	public void setVsdate(Date vsdate) {
		this.vsdate = vsdate;
	}

	public Date getVedate() {
		return vedate;
	}

	public void setVedate(Date vedate) {
		this.vedate = vedate;
	}

	public String getRegistermemo() {
		return registermemo;
	}

	public void setRegistermemo(String registermemo) {
		this.registermemo = registermemo;
	}

	public Integer getpType() {
		return pType;
	}

	public void setpType(Integer pType) {
		this.pType = pType;
	}

	public Integer getAllowsm() {
		return allowsm;
	}

	public void setAllowsm(Integer allowsm) {
		this.allowsm = allowsm;
	}

	public Integer getLogintype() {
		return logintype;
	}

	public void setLogintype(Integer logintype) {
		this.logintype = logintype;
	}

	public Integer getLoginsm() {
		return loginsm;
	}

	public void setLoginsm(Integer loginsm) {
		this.loginsm = loginsm;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public Short getIsSyn() {
		return isSyn;
	}

	@Override
	public void setIsSyn(Short isSyn) {
		this.isSyn = isSyn;
	}

	public String getCorpcode() {
		return corpcode;
	}

	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getSmscodeexpired() {
		return smscodeexpired;
	}

	public void setSmscodeexpired(Date smscodeexpired) {
		this.smscodeexpired = smscodeexpired;
	}

	public Integer getIsagreecontract() {
		return isagreecontract;
	}

	public void setIsagreecontract(Integer isagreecontract) {
		this.isagreecontract = isagreecontract;
	}

	public Integer getPfId() {
		return pfId;
	}

	public void setPfId(Integer pfId) {
		this.pfId = pfId;
	}

	public Integer getOrderui() {
		return orderui;
	}

	public void setOrderui(Integer orderui) {
		this.orderui = orderui;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public Integer getBuytype() {
		return buytype;
	}

	public void setBuytype(Integer buytype) {
		this.buytype = buytype;
	}

	public Integer getIschoose() {
		return ischoose;
	}

	public void setIschoose(Integer ischoose) {
		this.ischoose = ischoose;
	}

	public Integer getThemeid() {
		return themeid;
	}

	public void setThemeid(Integer themeid) {
		this.themeid = themeid;
	}

	public Integer getSpelltype() {
		return spelltype;
	}

	public void setSpelltype(Integer spelltype) {
		this.spelltype = spelltype;
	}

	public Integer getParentcorp() {
		return parentcorp;
	}

	public void setParentcorp(Integer parentcorp) {
		this.parentcorp = parentcorp;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}

	public Integer getRecommendpid() {
		return recommendpid;
	}

	public void setRecommendpid(Integer recommendpid) {
		this.recommendpid = recommendpid;
	}

	public Integer getIsbind() {
		return isbind;
	}

	public void setIsbind(Integer isbind) {
		this.isbind = isbind;
	}

	public Integer getRecommenduid() {
		return recommenduid;
	}

	public void setRecommenduid(Integer recommenduid) {
		this.recommenduid = recommenduid;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getpPhoto() {
		return pPhoto;
	}

	public void setpPhoto(String pPhoto) {
		this.pPhoto = pPhoto;
	}

	public String getpIntro() {
		return pIntro;
	}

	public void setpIntro(String pIntro) {
		this.pIntro = pIntro;
	}

	public Double getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(Double curPoint) {
		this.curPoint = curPoint;
	}

	public Double getDhPoint() {
		return dhPoint;
	}

	public void setDhPoint(Double dhPoint) {
		this.dhPoint = dhPoint;
	}

}
