package com.persons.Persons.apects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Around("@annotation(com.persons.Persons.annotations.LoggerAnnotation)")
    public Object logAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        logger.debug("Start {}", methodName);
        Object result = joinPoint.proceed();
        logger.debug("End {}", methodName);

        return result;
    }
}
