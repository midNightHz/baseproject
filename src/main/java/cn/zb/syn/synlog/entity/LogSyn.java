package cn.zb.syn.synlog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zb.base.entity.BaseEntity;

@Entity
@Table(name = "LOG_SYN")
public class LogSyn implements BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1734014932123582121L;

	@Id
	@GeneratedValue
	protected Integer id;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	protected Date synTime;
	
	protected String synUrl;
	
	@Column(length=20000,columnDefinition = "text")
	protected String synParams;

	protected Short resultType;
	@Column(length=20000,columnDefinition = "text")
	protected String resultMessage;

	public LogSyn() {

	}

	public LogSyn(Date synTime, String synUrl, String synParams, Short resultType, String resultMessage) {
		super();
		this.synTime = synTime;
		this.synUrl = synUrl;
		this.synParams = synParams;
		this.resultType = resultType;
		this.resultMessage = resultMessage;
	}

	public Date getSynTime() {
		return synTime;
	}

	public void setSynTime(Date synTime) {
		this.synTime = synTime;
	}

	public String getSynUrl() {
		return synUrl;
	}

	public void setSynUrl(String synUrl) {
		this.synUrl = synUrl;
	}

	public String getSynParams() {
		return synParams;
	}

	public void setSynParams(String synParams) {
		this.synParams = synParams;
	}

	public Short getResultType() {
		return resultType;
	}

	public void setResultType(Short resultType) {
		this.resultType = resultType;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
