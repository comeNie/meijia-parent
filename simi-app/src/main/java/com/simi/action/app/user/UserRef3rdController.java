package com.simi.action.app.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.IPUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.UserLogined;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserCouponService;
import com.simi.service.user.UserLoginedService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserSmsTokenService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.user.UserPushBindVo;
import com.simi.vo.user.UserSearchVo;

@Controller
@RequestMapping(value = "/app/user")
public class UserRef3rdController extends BaseController {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserLoginedService userLoginedService;

	@Autowired
	private UserCouponService userCouponService;

	@Autowired
	private UserRef3rdService userRef3rdService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UserSmsTokenService userSmsTokenService;
	
	// 1、第三方登录接口
	@RequestMapping(value = "login-3rd", method = RequestMethod.POST)
	public AppResultData<Object> login3rd(
			HttpServletRequest request,
			@RequestParam("openid") String openid,
			@RequestParam("3rd_type") String thirdType,
			@RequestParam("name") String name,
			@RequestParam(value = "head_img", required = false, defaultValue = "") String headImg,
			@RequestParam("login_from") int loginFrom,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) {

		AppResultData<Object> result = new AppResultData<Object>(
				Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, new String());
		
		UserSearchVo searchVo = new UserSearchVo();
		searchVo.setOpenid(openid);
		searchVo.setThirdType(thirdType);
		
		List<Users> rs = userService.selectBySearchVo(searchVo);
		Users users = null;
		if (!rs.isEmpty()) {
			users = rs.get(0);
		}

		// users不为空，表示用户可能通过手机号或者第三方账号注册
		if (users != null) {
			long ip = IPUtil.getIpAddr(request);
			// 第三方登录成功，在user_logined表添加记录
			UserLogined userLogined = new UserLogined();
			userLogined.setMobile(users.getMobile());
			userLogined.setLoginFrom((short) loginFrom);
			userLogined.setLoginIp(ip);
			userLogined.setUserId(users.getId());
			userLogined.setAddTime(TimeStampUtil.getNow() / 1000);
			userLoginedService.insertSelective(userLogined);
			
			UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(users.getId());
			//如果第一次登陆未注册时未成功注册环信，则重新注册
			if(userRef3rd == null){
				userRef3rdService.genImUser(users);
			}
//			UserRefSec userRefSec  = userRefSecService.selectByUserId(users.getId());
//			//如果第一次登录未注册时未成功分配秘书，则重新分配
//			if(userRefSec == null){
//				userRef3rdService.allotSec(users);
//			}
			users.setName(name);
			if(headImg!=null && !headImg.isEmpty()){
				users.setHeadImg(headImg);
			}
			users.setAddFrom((short) loginFrom);
			userService.updateByPrimaryKeySelective(users);

		} else {
			users = userService.initUsers();
			
			users.setOpenid(openid);
			users.setAddFrom((short) loginFrom);
			users.setThirdType(thirdType);
			users.setName(name);
			if(headImg!=null && !headImg.isEmpty()){
				users.setHeadImg(headImg);
			}
			users.setAddFrom((short) loginFrom);
			users = userService.genUser(users);

			// 第三方账号注册绑定环信账号
			userRef3rdService.genImUser(users);
			//为第三方登录的用户分配秘书
//			userRef3rdService.allotSec(users);
			
			
			//发送给13810002890 ，做一个提醒
//			String code = name;
//			String[] content = new String[] { code, Constants.GET_CODE_MAX_VALID };
//			HashMap<String, String> sendSmsResult = SmsUtil.SendSms("13810002890",
//			Constants.GET_CODE_TEMPLE_ID, content);
		}

		UserPushBind userPushBind = userPushBindService.selectByUserId(users.getId());
		UserPushBindVo vo = new UserPushBindVo();
		BeanUtilsExp.copyPropertiesIgnoreNull(users, vo);

		vo.setAppId("");
		vo.setChannelId("");
		vo.setAppUserId("");
		vo.setClientId("");
		if (userPushBind != null) {
			vo.setClientId(userPushBind.getClientId());
		}
		
		vo.setIsNewUser((short) 0);
		int loginCount = userLoginedService.selectByCount(users.getId());
		
		if (loginCount == 0 || loginCount == 1) {
			vo.setIsNewUser((short) 1);
		}
		result.setData(vo);

		return result;
	}
}
