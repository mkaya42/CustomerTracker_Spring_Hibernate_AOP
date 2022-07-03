package com.mky.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	//Set up logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//Set up pointcut declarations
	
	/*forController*/
	@Pointcut("execution(* com.mky.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	/*forService*/
	@Pointcut("execution(* com.mky.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	/*forDao*/
	@Pointcut("execution(* com.mky.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	/*Combine all above together*/
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====> in @Before : calling method : "+theMethod);
	}
	//add @AfterReturning advice
	
	@AfterReturning(pointcut ="forAppFlow()",returning = "theResult" )
	public void afterReturning(JoinPoint theJoinPoint,Object theResult) {
		
		//display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====> in @AfterReturning : from method : "+theMethod);
		
		
		//display data returned
		myLogger.info("=====> in result : "+theResult);

		

	}
	
}
