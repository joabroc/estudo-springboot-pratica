package org.example.analytics.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String correlationId = Optional.ofNullable(request.getHeader(CORRELATION_ID_HEADER))
                .filter(StringUtils::hasText)
                .orElseGet(() -> UUID.randomUUID().toString());

        String traceId = Optional.ofNullable(request.getHeader(TRACE_ID_HEADER))
                .filter(StringUtils::hasText)
                .orElse(correlationId);

        ThreadContext.put("correlationId", correlationId);
        ThreadContext.put("traceId", traceId);
        ThreadContext.put("http.method", request.getMethod());
        ThreadContext.put("http.path", request.getRequestURI());
        response.setHeader(CORRELATION_ID_HEADER, correlationId);
        response.setHeader(TRACE_ID_HEADER, traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            ThreadContext.put("http.status", String.valueOf(response.getStatus()));
            log.info("event=api.access entity=http operation=request outcome=completed durationMs={}",
                    System.currentTimeMillis() - start);
            ThreadContext.remove("correlationId");
            ThreadContext.remove("traceId");
            ThreadContext.remove("http.method");
            ThreadContext.remove("http.path");
            ThreadContext.remove("http.status");
        }
    }
}

