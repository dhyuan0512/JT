package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 利用JSONP实现用户信息校验
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param, @PathVariable Integer type, String callback) {
		// 校验用户信息
		boolean result = userService.checkUser(param, type);
		SysResult sysResult = SysResult.success(result);
		return new JSONPObject(callback, sysResult);
	}

	/*
	 * //http://sso.jt.com/user/query/d365b2842d231e9d13c85a69cadba4b3
	 * ?callback=jsonp1571282058554&_=1571282058600
	 * 根据ticket信息,查询用户JSON数据.之后将数据回传给客户端
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket, String callback) {
		String userJSON = jedisCluster.hget(ticket, "JT_USER");
		if (StringUtils.isEmpty(userJSON)) {
			// 用户使用的ticket有问题
			return new JSONPObject(callback, SysResult.fail());
		}
		return new JSONPObject(callback, SysResult.success(userJSON));
	}
}
