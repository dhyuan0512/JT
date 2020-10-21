package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	@Value("${server.port}")
	public String port;
	
	
	@RequestMapping("/getPort")
		public String getMsg() {
			return "当前服务器为:"+port;
		}
	}