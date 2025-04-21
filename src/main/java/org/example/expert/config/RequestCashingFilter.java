package org.example.expert.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.swing.text.AbstractDocument.Content;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class RequestCashingFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);

        filterChain.doFilter(cachingRequest,servletResponse);
    }
}
