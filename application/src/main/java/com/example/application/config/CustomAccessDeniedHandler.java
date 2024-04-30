package com.example.application.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Set custom message
        String errorMessage = "Access denied. You don't have permission to access this resource.";

        // Log the access denied event
        logger.log(Level.WARNING, "Access denied for URL: " + request.getRequestURI() + ", User: " + request.getRemoteUser());

        // Set HTTP status code
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Set custom error message in response
        response.getWriter().write(errorMessage);
    }
}