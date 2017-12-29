package com.genpact.security.common.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class BaseConfig {
	@Bean
	public ServletRegistrationBean registDruidServlet(@Value("${druid.allow}") String allow) {
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		//设置数据库连接池只能本级访问提升安全性
		//http://localhost:8080/druid/index.html
		HashMap<String, String> initParameters = new HashMap<String,String>();
		initParameters.put("allow", allow);
		bean.setInitParameters(initParameters);
		return bean;
	}
	@Bean
	public FilterRegistrationBean registFilterBean(@Value("${druid.exclusions}") String exclusions) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", exclusions);
		return filterRegistrationBean;
	}
}
