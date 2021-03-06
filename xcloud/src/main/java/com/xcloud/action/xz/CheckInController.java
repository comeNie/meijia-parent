package com.xcloud.action.xz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.baidu.BaiduConfigUtil;
import com.meijia.wx.utils.JsonUtil;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyCheckin;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XCompanyService;
import com.simi.service.xcloud.XCompanySettingService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.service.xcloud.XcompanyDeptService;
import com.simi.service.xcloud.XcompanyStaffService;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CheckinNetVo;
import com.simi.vo.xcloud.CheckinVo;
import com.simi.vo.xcloud.CompanyCheckinSearchVo;
import com.simi.vo.xcloud.CompanySettingSearchVo;
import com.simi.vo.xcloud.company.DeptSearchVo;
import com.xcloud.action.BaseController;
import com.xcloud.auth.AccountAuth;
import com.xcloud.auth.AuthHelper;
import com.xcloud.auth.AuthPassport;
import com.xcloud.common.Constant;

@Controller
@RequestMapping(value = "/xz/checkin")
public class CheckInController extends BaseController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private XCompanySettingService xCompanySettingService;

	@Autowired
	private XcompanyStaffService xcompanyStaffService;

	@Autowired
	private XCompanyService xCompanyService;

	@Autowired
	private XcompanyDeptService xcompanyDeptService;

	@Autowired
	private XcompanyCheckinService xcompanyCheckInService;

	@AuthPassport
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model, CompanyCheckinSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
		int pageNo = ServletRequestUtils.getIntParameter(request, Constant.PAGE_NO_NAME, Constant.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request, Constant.PAGE_SIZE_NAME, Constant.DEFAULT_PAGE_SIZE);

		// 获取登录的用户
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		searchVo.setCompanyId(companyId);
		
		
		if (StringUtil.isEmpty(searchVo.getSelectDay())) {
			searchVo.setSelectDay(DateUtil.getToday());
		}
		
		String startTimeStr = searchVo.getSelectDay() + " 00:00:00";
		String endTimeStr = searchVo.getSelectDay() + " 23:59:59";
		Long startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
		Long endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		
		model.addAttribute("searchModel", searchVo);
		
		PageInfo result = xcompanyCheckInService.selectByListPage(searchVo, pageNo, pageSize);

		List<XcompanyCheckin> list = result.getList();

		List<CheckinVo> vos = xcompanyCheckInService.getVos(list);

		for (int i = 0; i < list.size(); i++) {
			XcompanyCheckin item = list.get(i);

			for (CheckinVo vo : vos) {
				if (vo.getId().equals(item.getId())) {
					list.set(i, vo);
					break;
				}
			}
		}

		result = new PageInfo(list);

		model.addAttribute("contentModel", result);

		return "xz/checkin-list";
	}

	@AuthPassport
	@RequestMapping(value = "net", method = RequestMethod.GET)
	public String netList(HttpServletRequest request, Model model) {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long userId = accountAuth.getUserId();
		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("companyId", companyId);
		model.addAttribute("userId", userId);

		CompanySettingSearchVo searchVo = new CompanySettingSearchVo();

		searchVo.setCompanyId(companyId);
		searchVo.setSettingType(Constants.SETTING_CHICKIN_NET);

		List<XcompanySetting> list = xCompanySettingService.selectBySearchVo(searchVo);

		List<CheckinNetVo> result = new ArrayList<CheckinNetVo>();

		for (int i = 0; i < list.size(); i++) {
			XcompanySetting item = list.get(i);
			CheckinNetVo vo = xcompanyCheckInService.initCheckinNetVo();

			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();

				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);
			}
			result.add(vo);
		}
		model.addAttribute("contentModel", result);

		return "xz/checkin-net";
	}

	@AuthPassport
	@RequestMapping(value = "netForm", method = RequestMethod.GET)
	public String netForm(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false, defaultValue = "0") Long id) {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		model.addAttribute("id", id);

		CheckinNetVo vo = xcompanyCheckInService.initCheckinNetVo();
		XcompanySetting item = xCompanySettingService.initXcompanySetting();
		if (id > 0L) {
			item = xCompanySettingService.selectByPrimaryKey(id);
			if (item.getSettingValue() != null) {
				JSONObject setValue = (JSONObject) item.getSettingValue();

				vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

				Users u = usersService.selectByPrimaryKey(item.getUserId());
				vo.setUserName(u.getName());
			}
		}
		if (vo.getStatus() == null) vo.setStatus((short) 1);
		model.addAttribute("contentModel", vo);

		// 可选部门
		DeptSearchVo deptSearchVo = new DeptSearchVo();

		deptSearchVo.setCompanyId(companyId);

		List<XcompanyDept> deptList = xcompanyDeptService.selectBySearchVo(deptSearchVo);
		model.addAttribute("deptList", deptList);

		// 百度地图ak
		model.addAttribute("ak", BaiduConfigUtil.getInstance().getRb().getString("ak"));
		return "xz/checkin-net-form";
	}

	@AuthPassport
	@RequestMapping(value = "netForm", method = RequestMethod.POST)
	public String doNetForm(HttpServletRequest request, Model model, @ModelAttribute("contentModel") CheckinNetVo vo) {

		// 1. 获取登录的用户/公司 id
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long userId = accountAuth.getUserId();
		String userName = accountAuth.getName();
		vo.setUserName(userName);

		Long companyId = accountAuth.getCompanyId();

		Long id = Long.valueOf(request.getParameter("id"));

		XcompanySetting item = xCompanySettingService.initXcompanySetting();
		if (id > 0L) {
			item = xCompanySettingService.selectByPrimaryKey(id);
		}

		item.setUserId(userId);
		item.setCompanyId(companyId);
		item.setName("出勤地点");
		item.setSettingType(Constants.SETTING_CHICKIN_NET);
		item.setIsEnable(vo.getStatus());

		String json = JsonUtil.objecttojson(vo);

		Long addTime = TimeStampUtil.getNowSecond();
		if (id.equals(0L)) {
			item.setAddTime(addTime);
			item.setUpdateTime(addTime);
			item.setSettingValue(json);
			xCompanySettingService.insert(item);
			id = item.getId();
		}

		vo.setId(id);
		String addTimeStr = TimeStampUtil.timeStampToDateStr(addTime * 1000, "yyyy-MM-dd HH:MM");
		vo.setAddTimeStr(addTimeStr);
		// 处理jsonObject数据
		String deptIds = vo.getDeptIds();
		String[] deptAry = StringUtil.convertStrToArray(deptIds);

		// 可选部门
		DeptSearchVo deptSearchVo = new DeptSearchVo();
		deptSearchVo.setCompanyId(companyId);
		List<XcompanyDept> deptList = xcompanyDeptService.selectBySearchVo(deptSearchVo);

		String deptNames = "";

		if (deptIds.equals("0")) {
			deptNames = "全体成员";
		} else {
			for (int i = 0; i < deptAry.length; i++) {
				if (StringUtil.isEmpty(deptAry[i]))
					continue;
				Long deptId = Long.valueOf(deptAry[i]);

				for (XcompanyDept xd : deptList) {
					if (xd.getDeptId().equals(deptId)) {
						deptNames+= xd.getName() + ",";
						break;
					}
				}
			}
		}

		vo.setDeptNames(deptNames);
		json = JsonUtil.objecttojson(vo);

		item.setSettingValue(json);

		item.setUpdateTime(addTime);
		xCompanySettingService.updateByPrimaryKey(item);

		return "redirect:net";
	}

	// 删除会议
	@AuthPassport
	@RequestMapping(value = "net-del", method = RequestMethod.POST)
	public AppResultData<Object> netDel(HttpServletRequest request, Model model, @RequestParam("id") Long id) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (id.equals(0L))
			return result;
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);

		Long companyId = accountAuth.getCompanyId();

		XcompanySetting record = xCompanySettingService.selectByPrimaryKey(id);
		if (record != null) {
			if (record.getCompanyId().equals(companyId)) {
				record.setIsEnable((short) 0);
				record.setUpdateTime(TimeStampUtil.getNowSecond());
				
				if (record.getSettingValue() != null) {
					JSONObject setValue = (JSONObject) record.getSettingValue();

					CheckinNetVo vo = JSON.toJavaObject(setValue, CheckinNetVo.class);

					vo.setStatus((short) 0);
					
					String json = JsonUtil.objecttojson(vo);

					record.setSettingValue(json);
					
				}
				
				
				xCompanySettingService.updateByPrimaryKey(record);
			}
		}

		return result;

	}

	@AuthPassport
	@RequestMapping(value = "set", method = RequestMethod.GET)
	public String setForm(HttpServletRequest request, Model model) {

		return "xz/checkin-set";
	}

	@AuthPassport
	@RequestMapping(value = "set", method = RequestMethod.POST)
	public String baseSetting(HttpServletRequest request, Model model) {

		return "xz/checkin-set";
	}

}
