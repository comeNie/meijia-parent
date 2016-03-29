package com.simi.po.dao.dict;

import java.util.List;

import com.simi.po.model.dict.DictExpress;

public interface DictExpressMapper {
    int deleteByPrimaryKey(Long expressId);

    int insert(DictExpress record);

    int insertSelective(DictExpress record);

    DictExpress selectByPrimaryKey(Long expressId);

    int updateByPrimaryKeySelective(DictExpress record);

    int updateByPrimaryKey(DictExpress record);

	List<DictExpress> selectAll();

	List<DictExpress> selectByT(Long t);
}