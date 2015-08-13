package com.simi.vo.partners;

import java.util.ArrayList;
import java.util.List;

import com.meijia.utils.common.extension.ArrayHelper;
import com.simi.po.model.partners.PartnerLinkMan;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.partners.Partners;
public class PartnerFormVo extends Partners {

	List<PartnerLinkMan> linkMan;
	
	String addr;
	
	String regionIds;

	private  List<PartnerServiceType> childList = new ArrayList<PartnerServiceType>() ;

	private  Long[]  serviceTypeIds;

	private Long partnerTypeId;
		
	private  Long[]  partnerTypeIds;
	
	String partnerCityId;
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPartnerCityId() {
		return partnerCityId;
	}
	public void setPartnerCityId(String partnerCityId) {
		this.partnerCityId = partnerCityId;
	}
	public String getPartnerTypeIdsString(){
		return ArrayHelper.LongtoString(partnerTypeIds, ",");
	}
	public Long getPartnerTypeId() {
		return partnerTypeId;
	}

	public void setPartnerTypeId(Long partnerTypeId) {
		this.partnerTypeId = partnerTypeId;
	}

	public Long[] getPartnerTypeIds() {
		return partnerTypeIds;
	}

	public void setPartnerTypeIds(Long[] partnerTypeIds) {
		this.partnerTypeIds = partnerTypeIds;
	}

	public Long[] getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(Long[] serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}

	public List<PartnerServiceType> getChildList() {
		return childList;
	}

	public void setChildList(List<PartnerServiceType> childList) {
		this.childList = childList;
	}

	public String getRegionIds() {
		return regionIds;
	}
	public void setRegionIds(String regionIds) {
		this.regionIds = regionIds;
	}
	public List<PartnerLinkMan> getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(List<PartnerLinkMan> linkMan) {
		this.linkMan = linkMan;
	}

	List<PartnerServiceTypeVo>  serviceTypes;

	public List<PartnerServiceTypeVo> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(List<PartnerServiceTypeVo> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

}