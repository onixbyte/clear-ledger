package com.onixbyte.clearledger.security;

import com.onixbyte.clearledger.data.dto.BizUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * An interceptor for managing the current user in the request lifecycle.
 * <p>
 * Implements {@link HandlerInterceptor} to set and clear the current {@link BizUser} in
 * {@link UserHolder} based on the security context.
 *
 * @author zihluwang
 */
public class UserInterceptor implements HandlerInterceptor {

    /**
     * Executes before the request is handled, setting the current user in {@link UserHolder}.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param handler  the handler for the request
     * @return true to proceed with the request handling
     * @throws Exception if an error occurs during processing
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof BizUser currentUser) {
            UserHolder.setCurrentUser(currentUser);
        }
        return true;
    }

    /**
     19 import java.util.List;
     /**
     * Executes after the request is completed, clearing the current user from {@link UserHolder}.
     *
     * @param request   the HTTP request
     * @param response  the HTTP response
     * @param handler   the handler for the request
     * @param ex        the exception that occurred, if any
     * @throws Exception if an error occurs during processing
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserHolder.clearCurrentUser();
    }
}