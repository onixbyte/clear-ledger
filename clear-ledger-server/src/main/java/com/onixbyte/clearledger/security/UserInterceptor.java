package com.onixbyte.clearledger.security;

import com.onixbyte.clearledger.data.dto.BizUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof BizUser currentUser) {
            UserHolder.setCurrentUser(currentUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.clearCurrentUser();
    }
}
