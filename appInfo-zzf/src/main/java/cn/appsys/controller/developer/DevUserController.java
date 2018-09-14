package cn.appsys.controller.developer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value="/dev")
public class DevUserController {
	@Autowired
	private DevUserService service;
	private Logger logger = Logger.getLogger(DevUserController.class);	
	/**
	 * 跳转开发者登录页面
	 * @return 
	 */
	@RequestMapping(value="/login")
	private String login() {
		logger.debug("login 开发者登录===========>welcome");
		return "devlogin";
	}
	
	/**
	 * 开发者登录验证
	 * @param devCode
	 * @param password
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam(value="devCode") String devCode,
			@RequestParam(value="devPassword") String password,
			HttpServletRequest request,HttpSession session){
		logger.debug("dologin 开发者登录中---------------------");
		DevUser loginUser = service.login(devCode, password);
		if(loginUser!=null){
			session.setAttribute(Constants.DEV_USER_SESSION, loginUser);
			return "redirect:/dev/flatform/main";
		}
		request.setAttribute("error", "用户名或密码错误！");
		return "devlogin";
	}
	
	/**
	 * 跳转到开发者首页
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/flatform/main")
	public String main(HttpSession session){
		if(session.getAttribute(Constants.DEV_USER_SESSION) == null){
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	/**
	 * 开发者用户登出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String loginOut(HttpSession session){
		logger.debug("logout 开发者登出===========>goodBye");
		session.removeAttribute(Constants.DEV_USER_SESSION);
		return "redirect:/dev/login";
	}
	
	
	
}
