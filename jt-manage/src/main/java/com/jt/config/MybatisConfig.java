package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
public class MybatisConfig {
	/* <bean id="paginationInterceptor" class=""/> */
	//添加分页拦截器,否则分页有问题!!!!
	@Bean 
	 public PaginationInterceptor PaginationInterceptor() {
		
		return  new PaginationInterceptor();
		
	}
}
