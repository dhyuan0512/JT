package com.jt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	//校验用户数据的有效性,是否可用
	@Override
	public boolean checkUser(String param, Integer type) {
		Map<Integer, String> columnMap=new HashMap<>();
		columnMap.put(1, "username");
		columnMap.put(2, "phone");
		columnMap.put(3, "email");
		String column= columnMap.get(type);
		QueryWrapper<User> queryWrapper =new QueryWrapper<User>();
		queryWrapper.eq(column, param);
		User userDB= userMapper.selectOne(queryWrapper);
		return userDB==null?false:true;
	}

}
