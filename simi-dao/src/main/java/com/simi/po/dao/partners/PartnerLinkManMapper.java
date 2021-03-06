package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerLinkMan;

public interface PartnerLinkManMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByPartnerId(Long partnerId);

    int insert(PartnerLinkMan record);

    int insertSelective(PartnerLinkMan record);

    PartnerLinkMan selectByPrimaryKey(Long id);

    List<PartnerLinkMan> selectByPartnerId(Long partnerId);

    int updateByPrimaryKeySelective(PartnerLinkMan record);

    int updateByPrimaryKey(PartnerLinkMan record);
}