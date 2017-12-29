package com.genpact.security.common.aop;

//import org.aopalliance.intercept.MethodInterceptor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSONArray;
import com.genpact.security.modules.sys.user.entity.User;

@Aspect
@Configuration
public class WebRequestLogAspect  {
	private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);
	
	@Pointcut("execution(* com.genpact.security.modules.*.controller.*.*(..))")
	public void webRequestLog(){ }

    // @Order(5)
//    @Before("webRequestLog() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {

//            long beginTime = System.currentTimeMillis();
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String beanName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String uri = request.getRequestURI();
            String remoteAddr = getIpAddr(request);
            String sessionId = request.getSession().getId();
            String userName = null;
			if((SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof String){
				userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            }else{
            	User user  = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            	userName = user.getUsername();
            }
//            String user = (String) request.getSession().getAttribute("user");
            String method = request.getMethod();
            String params = "";
            if ("POST".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = JSONArray.toJSONString(paramsArray);
            } else {
                Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params = paramsMap.toString();
            }
            logger.info("=================================================");
            logger.info(" methodName ->" + methodName );
            logger.info(" uri->"+uri);
            logger.info(" beanName->" + beanName );
            logger.info(" params->" + params );
            logger.info(" user->" + userName);
            logger.info(" remoteAddr->" + remoteAddr );
            logger.info(" sessionId->"+sessionId);
            logger.info("=================================================");
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }
    }

    // @Order(5)
//    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
//    public void doAfterReturning(Object result) {
//        try {
//            // 处理完请求，返回内容
//            OperatorLog optLog = tlocal.get();
//            optLog.setResult(result.toString());
//            long beginTime = optLog.getRequestTime();
//            long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
//            optLog.setRequestTime(requestTime);
//
//            System.out.println("请求耗时：" + optLog.getRequestTime() + optLog.getUri() + "   **  " + optLog.getParams() + " ** "
//                    + optLog.getMethodName());
//            System.out.println("RESPONSE : " + result);
//
//            optLogService.saveLog(optLog);
//        } catch (Exception e) {
//            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
//        }
//    }


    /**
     * 获取登录用户远程主机ip地址
     * 
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

   
	
	
	
}
