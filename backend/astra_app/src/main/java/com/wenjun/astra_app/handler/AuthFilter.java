package com.wenjun.astra_app.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenjun.astra_app.model.ResponseWrapper;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.extern.log4j.Log4j2;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static io.opentelemetry.instrumentation.api.incubator.log.LoggingContextConstants.TRACE_ID;

@Component
@Order(2)
@Log4j2
public class AuthFilter extends OncePerRequestFilter {
    private final FirebaseClient firebaseClient;

    public AuthFilter(FirebaseClient firebaseClient) {
        this.firebaseClient = firebaseClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");
            log.info("Request: {}", jwt);
            AuthenticatedUser user = firebaseClient.getUser(jwt);
            log.info("user: {}", user);
            ThreadLocalUser.set(user);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseWrapper wrapper = new ResponseWrapper<>(null, false, e.getMessage(), 401, MDC.get(TRACE_ID));
            mapper.writeValue(response.getWriter(), wrapper);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return "/users/register".equalsIgnoreCase(uri) || "options".equalsIgnoreCase(request.getMethod());
    }
}
