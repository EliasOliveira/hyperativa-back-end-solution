package com.example.hyperativa_back_end.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ApiLoggingAspect {

    private final ObjectMapper objectMapper;

    @Around("execution(* com.example.hyperativa_back_end.controllers..*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("➡️ Incoming request: {} {}", method, uri);

        if (log.isDebugEnabled()) {
            try {
                String argsJson = objectMapper.writeValueAsString(joinPoint.getArgs());
                log.debug("Request body/params: {}", argsJson);
            } catch (Exception e) {
                log.debug("Could not serialize request arguments", e);
            }
        }

        Object result = joinPoint.proceed();

        if (response != null) {
            log.info("⬅️ Response status: {}", response.getStatus());
        }

        if (log.isDebugEnabled()) {
            try {
                String resultJson = objectMapper.writeValueAsString(result);
                log.debug("Response body: {}", resultJson);
            } catch (Exception e) {
                log.debug("Could not serialize response object", e);
            }
        }

        return result;
    }
}