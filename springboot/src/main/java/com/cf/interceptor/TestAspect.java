package com.cf.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void annotationMethod() {

    }

    @Around("annotationMethod()")
    public Object interceptMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("before");
        proceedingJoinPoint.proceed();
        System.out.println("after");
        return null;
    }
}
