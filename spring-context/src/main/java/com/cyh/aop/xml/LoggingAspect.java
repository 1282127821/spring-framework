package com.cyh.aop.xml;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        LOGGER.debug("The method '" + methodName + "()' begins with " + Arrays.asList(args));
    }

    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.debug("The method '" + methodName + "()' ends");
    }

    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.debug("The method '" + methodName + "()' ends with " + result);
    }

    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.debug("The method '" + methodName + "()' occurs exception: " + e);
    }

    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();

        try {
            //前置通知
            LOGGER.debug("The method '" + methodName + "()' begins with " + Arrays.asList(pjd.getArgs()));
            //执行目标方法
            result = pjd.proceed();
            //返回通知
            LOGGER.debug("The method '" + methodName + "()' ends with " + result);
        } catch (Throwable e) {
            //异常通知
            LOGGER.debug("The method '" + methodName + "()' occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        LOGGER.debug("The method '" + methodName + "()' ends");

        return result;
    }
}
