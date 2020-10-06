package com.xuecheng.aop.advice;

import com.xuecheng.aop.service.TestService;
import com.xuecheng.aop.service.declareparents.TestI;
import com.xuecheng.aop.service.declareparents.TestInter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class AspectTest {

//    @Around("pointCut()")
//    public void aroundTest(ProceedingJoinPoint proceedingJoinPoint) {
//        //获取目标方法的参数，并可以对其进行修改
////        Object[] args = joinPoint.getArgs();
//
//        try {
//            System.out.println("环绕通知之前执行");
//            proceedingJoinPoint.proceed();
//            System.out.println("环绕通知之后执行");
//        } catch (Throwable throwable) {
//            System.out.println("异常通知执行。。");
//        }
//    }

    @Pointcut("execution(* com.xuecheng.aop.service..*.*(..))")
    public void pointCut() {

    }

    @AfterReturning(value = "pointCut()",returning = "returnvalue")
    public void afterReturning(JoinPoint joinPoint,Object returnvalue){
        System.out.println("返回值是："+returnvalue);
        System.out.println("目标方法执行完毕后，执行。。。。");
    }

//    @Around("pointCut()")
//    public void before(ProceedingJoinPoint joinPoint)  {
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args) {
//            System.out.println("方法的参数是"+arg);
//        }
//
//        System.out.println("环绕之前。。。");
//        try {
//            String proceed = (String) joinPoint.proceed();
//            proceed="改变后的返回值";
//            System.out.println(proceed);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println("环绕之后。。。");
//
//
//    }

//    @After("pointCut()")
//    public void after(JoinPoint joinPoint){
//        String[] args = (String[]) joinPoint.getArgs();
//        for (String arg : args) {
//            System.out.println(arg);
//        }
//        System.out.println("after执行。。。。");
//    }
//    @Around("this(com.xuecheng.aop.service.TestService)")
//    public void around1(ProceedingJoinPoint proceedingJoinPoint){
//        try {
//            System.out.println("this环绕通知之前。。");
//            proceedingJoinPoint.proceed();
//            System.out.println("this环绕通知之后。。");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    @Around("target(com.xuecheng.aop.service.TestService)")
//    public void around2(ProceedingJoinPoint proceedingJoinPoint){
//        try {
//            System.out.println("target环绕通知之前。。");
//            proceedingJoinPoint.proceed();
//            System.out.println("target环绕通知之后。。");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//
//    @Around("@annotation(com.xuecheng.aop.annontation.ZiDin)")
//    public void around5(ProceedingJoinPoint proceedingJoinPoint){
//        try {
//            System.out.println("@annotation环绕通知之前。。");
//            proceedingJoinPoint.proceed();
//            System.out.println("@annotation环绕通知之后。。");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//    @Around("@within(com.xuecheng.aop.annontation.ZiDin)")
//    public void around4(ProceedingJoinPoint proceedingJoinPoint){
//        try {
//            System.out.println("@within环绕通知之前。。");
//            proceedingJoinPoint.proceed();
//            System.out.println("@within环绕通知之后。。");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    @Around("@args(com.xuecheng.aop.annontation.ZiDin)")
//    public void around3(ProceedingJoinPoint proceedingJoinPoint){
//        try {
//            System.out.println("@args环绕通知之前。。");
//            proceedingJoinPoint.proceed();
//            System.out.println("@args环绕通知之后。。");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    @DeclareParents(value = "com.xuecheng.aop.service.TestService",defaultImpl = TestI.class)
//    private TestInter testInter;

}