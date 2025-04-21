package org.example.expert.domain.common.adminlogging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Aspect
@Slf4j
@Component
public class AdminLogAspect {

    private final Logger logger = LoggerFactory.getLogger(AdminLogAspect.class);
    private final ObjectMapper objectMapper;

    public AdminLogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    public void adminComment() {}

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public void adminUser() {}

    @Pointcut("adminComment()||adminUser()")
    public void all() {}

    @Around("all()")
    public Object logging (ProceedingJoinPoint pjp) throws Throwable {
        // 메서드 실행 전
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        // 요청에서 꺼내올 정보들
        String id = String.valueOf(request.getAttribute("userId")); // 유저 아이디
        String time = LocalDateTime.now().toString(); // 요청 시간
        String url = request.getRequestURI(); // 요청 url
        String requestBody = ""; // 요청 본문 (json)
        if(request instanceof ContentCachingRequestWrapper wrapper) { // 래퍼로 감싼게 맞는지 검증
            byte[] content = wrapper.getContentAsByteArray();
            requestBody = new String(content, StandardCharsets.UTF_8);
        }

        logger.info("[어드민 API 요청] \n 사용자 ID - {} \n 시각 - {}  \n URL - {}  \n Body - {} ", id, time, url, requestBody);

        // 메서드 실행
        Object result = pjp.proceed();

        // 메서드 실행 후

        // 응답에서 꺼내올 정보들
        // 응답 본문 (json)
        String responseBody = objectMapper.writeValueAsString(request);

        logger.info("[어드민 API 응답]  \n Body - {}", responseBody);


        return result;
    }

}
