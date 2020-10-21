package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(check = false)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("/{moduleName}")
	public String moduleName(@PathVariable String moduleName) {
		return moduleName;
	}

	// http://www.jt.com/user/doRegister
	// type:post
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		userService.insertUser(user);
		return SysResult.success();
	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
		// 1.动态获取用户Ip地址
		String ip = IPUtil.getIpAddr(request);
		// 2.获取检验结果
		String ticket = userService.findUserByUP(user, ip);
		if (StringUtils.isEmpty(ticket)) {
			// 表示用户名和密码错误
			return SysResult.fail();
		}
		// 3.数据保存到cookie中
		Cookie ticketCookie = new Cookie("JT_TICKET", ticket);
		// 7天有效
		ticketCookie.setMaxAge(7 * 24 * 3600);
		// cookie的权限设定(路径)
		ticketCookie.setPath("/");
		// cookie实现共享
		ticketCookie.setDomain("jt.com");
		response.addCookie(ticketCookie);
		return SysResult.success();
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if(cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("JT_TICKET")) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		if(!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
			Cookie cookie = new Cookie("JT_TICKET","");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			cookie.setDomain("jt.com");
			response.addCookie(cookie);
		}
		return "redirect:/";
	}
}