package com.leo.fundservice.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 拦截器
 * @author leopard
 */
@Aspect
@Slf4j
@Component
public class LogAop {

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    private final String POINT_CUT = "execution(public * com.leo.fundservice.controller.*.*.*(..))";

    /**
     * 切点表达式中，..两个点表明多个，*代表一个，  上面表达式代表切入com.demo.controller包下的所有类的所有方法，
     * 方法参数不限，返回类型不限。  其中访问修饰符可以不写，不能用*，，第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，
     * ..两个点表示方法里的参数不限。  然后用@Pointcut切点注解，想在一个空方法上面，
     */
    /**
     * 指定切面，这里可以是一个空方法；
     */
    @Pointcut(POINT_CUT)
    private void pointCut() {
    }

    //请求method前打印内容
    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //打印请求内容
        log.info("=================[Before]请求内容===============");
        log.info("==================[Before]请求地址:{}", request.getRequestURL().toString());
        log.info("==================[Before]请求方式:{}", request.getMethod());
        log.info("==================[Before]请求类方法:{}", joinPoint.getSignature());
        log.info("==================[Before]请求类方法参数:{}", Arrays.toString(joinPoint.getArgs()));
        log.info("==================[Before]请求内容===============");
    }


    //环绕执行：定义需要匹配的切点表达式，同时需要匹配参数
    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("==================[Around]方法环绕前置start=====================>");
        //这句必须有 往下执行方法
        Object result = pjp.proceed();

        log.info("==================[Around]方法环绕后置start=====================>");
        log.info("==================[Around]耗时（毫秒）：{}", (System.currentTimeMillis() - startTime.get()));
        log.info("==================[Around]返回数据：{}", result);
        log.info("==================[Around]方法环绕end======================>");
        return result;
    }

    //方法执行后
    @After(value = "pointCut()")
    public void after(){
        log.debug("==================[After]切点方法执行后");
    }

    //方法异常拦截
    @AfterThrowing(value = "pointCut()")
    public void afterThrowing(){
        log.debug("==================[AfterThrowing]切点方法抛出异常");
    }


    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "pointCut()")
    public void afterReturning(Object o) {
        log.info("==================[AfterReturning]Response内容:" + JSON.toJSONString(o));
        log.info("==================[AfterReturning]请求消耗时间：{}ms", (System.currentTimeMillis() - startTime.get()));

    }

}