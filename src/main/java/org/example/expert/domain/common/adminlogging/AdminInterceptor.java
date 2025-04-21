package org.example.expert.domain.common.adminlogging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle( // Handler 가기 전 실행될 메서드(=타겟 메서드 수행전에 실행)
        HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {

        log.info("[어드민 사용자 접근] 요청 시각 = {}, 요청 URL = {}", LocalDateTime.now(), request.getRequestURI());

        return true;
    }

}
