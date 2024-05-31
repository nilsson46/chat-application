package com.example.application.config;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import com.example.application.service.AuthenticationService;
import com.example.application.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
@Slf4j
@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    public AuthHandshakeInterceptor(JwtService jwtService, AuthenticationService authenticationService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String token = request.getURI().getQuery();
        logger.info("Token: {}", token);
        System.out.println("Token: " + token);


        if (token != null) {
            String username = jwtService.extractUsername(token);
            logger.info("Username: {}", username);
            System.out.println("Username: " + username);
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            if (jwtService.isValid(token, userDetails)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Authentication successful");
                System.out.println("Authentication successful");
            } else {
                logger.info("Token is not valid");
                System.out.println("Token is not valid");
            }
        } else {
            logger.info("Token is null");
            System.out.println("Token is null");
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}