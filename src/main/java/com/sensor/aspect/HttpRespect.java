package com.sensor.aspect;


import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HttpRespect {
	
	private final static Logger logger=LoggerFactory.getLogger(HttpRespect.class);
	
	//去切谁
	@Pointcut("execution(public * com.sensor.controller.UserController.*(..))")
	public  void log() {
	}

	@Before("log()")
	public  void doBefore(JoinPoint joinpoint) {
		ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();		
		//url
		logger.info("url={}",request.getRequestURI());
		//method
		logger.info("method={}",request.getMethod());
		//ip
		logger.info("ip={}",request.getRemoteAddr());

		//类方法
		
		logger.info("class method={}",joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());	
	
		//参数
		logger.info("agrs={}",joinpoint.getArgs());
	}
	
	@After("log()")
	public void doAfter() {	
		logger.info("222");
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void doafterReturn(Object object) {
		logger.info("response={}",object.toString());
		
	}
	
	
	
}
