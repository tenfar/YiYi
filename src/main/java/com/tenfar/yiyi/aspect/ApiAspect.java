package com.tenfar.yiyi.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ApiAspect {
    private final Logger logger = LoggerFactory.getLogger(ApiAspect.class);
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.tenfar.yiyi.controller..*(..)) )")
    public void log() {
    }

    /**
     * 执行方法前，记录相关请求数据
     *
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        logger.info("REQ_URL:{}, ARGS:{},  METHOD:{}, CLI_IP:{}", request.getRequestURL().toString(),
                JSON.toJSON(joinPoint.getArgs()), request.getMethod(), ip);
    }

    /**
     * 执行完成后，记录相关信息
     */
    @After("log()")
    public void doAfter() {
        // 记录执行时间
        logger.info("EXE_TIME: {}", System.currentTimeMillis() - startTime.get());
    }

}
