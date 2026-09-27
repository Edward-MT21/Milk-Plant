package com.module.Milk_Collection.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspecto que intercepta y registra el inicio y fin de los métodos en el paquete services.impl.
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    // Pointcut: Intercepta todos los métodos públicos del paquete services.impl
    @Pointcut("execution(* com.module.Milk_Collection.services.impl..*(..))")
    public void serviceImplMethods() {}

    @Around("serviceImplMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // 1. Log de Inicio (con argumentos)
        logger.debug("Start {} with arguments: {}", methodName, Arrays.toString(args));

        Object result;
        try {
            // 2. Ejecución del método real
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in {} with cause: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        // 3. Log de Fin
        logger.debug("End {}", methodName);

        return result;
    }

}
