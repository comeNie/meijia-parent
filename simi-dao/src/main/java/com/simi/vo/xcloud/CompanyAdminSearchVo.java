package com.simi.vo.xcloud;

import java.util.List;


public class CompanyAdminSearchVo{

	private Long companyId;
	
	private List<Long> companyIds;
	
	private String companyName;
	
	private Long userId;
	
	private String userName;
	
	private String passMd5;
		
	private Short isCreate;
	
	private Long startTime;
	
	private Long endTime;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassMd5() {
		return passMd5;
	}

	public void setPassMd5(String passMd5) {
		this.passMd5 = passMd5;
	}

	public Short getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Short isCreate) {
		this.isCreate = isCreate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<Long> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Long> companyIds) {
		this.companyIds = companyIds;
	}
	

	
}
