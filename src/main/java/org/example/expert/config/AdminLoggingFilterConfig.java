package org.example.expert.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminLoggingFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> loggingFilter() {
     FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
     registrationBean.setFilter(new RequestCashingFilter());
     registrationBean.addUrlPatterns("/*"); // 모든 요청

     return registrationBean;
    }


}
