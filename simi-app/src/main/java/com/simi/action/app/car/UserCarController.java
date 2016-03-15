package com.simi.action.app.car;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.MathBigDecimalUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.order.Orders;
import com.simi.po.model.user.UserCar;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.order.OrderQueryService;
import com.simi.service.user.UserCarService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.OrderSearchVo;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardListVo;
import com.simi.vo.card.CardRemindVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.order.OrderDetailVo;
import com.simi.vo.order.OrderListVo;

@Controller
@RequestMapping(value = "/app/car")
public class UserCarController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private UserCarService userCarService;
	
	@Autowired
    private OrderQueryService orderQueryService;

	// 用户车牌信息
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_car", method = RequestMethod.GET)
	public AppResultData<Object> getCar(@RequestParam("user_id") Long userId) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCar userCar = userCarService.selectByUserId(userId);

		if (userCar == null) {
			userCar = userCarService.initUserCar();
		}

		result.setData(userCar);

		return result;
	}

	// 用户车牌信息 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "post_car_no", method = RequestMethod.POST)
	public AppResultData<Object> postImgs(@RequestParam("user_id") Long userId, @RequestParam("car_no") String carNo) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		UserCar userCar = userCarService.selectByCarNo(carNo);
		if (userCar != null) {
			if (userCar.getUserId().equals(userId)) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg("车牌号已经绑定过.");
				return result;
			}
		}

		userCar = userCarService.selectByUserId(userId);

		if (userCar == null) {
			userCar = userCarService.initUserCar();
		}

		userCar.setUserId(userId);
		userCar.setCarNo(carNo);

		if (userCar.getId() > 0L) {
			userCarService.updateByPrimaryKey(userCar);
		} else {
			userCarService.insert(userCar);
		}

		return result;
	}
	
	@RequestMapping(value = "get_order_list", method = RequestMethod.GET)
	public AppResultData<Object> list(
			@RequestParam("user_id") Long userId, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		
		List<OrderListVo> orderListVo = new ArrayList<OrderListVo>();
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);
		
		if (u == null) {
			return result;
		}
		
		Long serviceTypeId = 258L;
		OrderSearchVo searchVo = new OrderSearchVo();
		searchVo.setUserId(userId);
		searchVo.setServiceTypeId(serviceTypeId);
		PageInfo list = orderQueryService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Orders> orderList = list.getList();
		
		for (Orders item : orderList) {			
			OrderListVo listVo = new OrderListVo();
			listVo = orderQueryService.getOrderListVo(item);
			
			OrderDetailVo detailVo = orderQueryService.getOrderDetailVo(item, listVo);
			orderListVo.add(detailVo);
		}
		
		result.setData(orderListVo);
		
		return result;

	}

}
