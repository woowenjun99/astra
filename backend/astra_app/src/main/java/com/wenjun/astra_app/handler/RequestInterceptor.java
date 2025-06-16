package com.wenjun.astra_app.handler;

import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static io.opentelemetry.instrumentation.api.incubator.log.LoggingContextConstants.TRACE_ID;

@AllArgsConstructor
@Component
public class RequestInterceptor implements HandlerInterceptor {
    private final FirebaseClient firebaseClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        // Allow bypass if the method comes from preflight of CORs
        if ("options".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String jwt = request.getHeader("Authorization");
        AuthenticatedUser user = firebaseClient.getUser(jwt);
        ThreadLocalUser.set(user);
        return true;
    }
}
