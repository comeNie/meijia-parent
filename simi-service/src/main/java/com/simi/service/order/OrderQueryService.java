package com.simi.service.order;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.order.OrderViewVo;
import com.simi.po.model.order.Orders;

public interface OrderQueryService {

    Orders selectByOrderNo(String orderNo);

    Orders selectByPrimaryKey(Long id);

	List<OrderViewVo> getOrderViewList(List<Orders> list);
	
	PageInfo selectByListPage(OrderSearchVo orderSearchVo, int pageNo, int pageSize);

	OrderViewVo getOrderView(Orders order);
}