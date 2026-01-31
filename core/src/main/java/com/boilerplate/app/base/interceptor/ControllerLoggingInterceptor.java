package com.boilerplate.app.base.interceptor;

import com.boilerplate.app.base.constant.TraceConstants;
import com.boilerplate.app.base.logging.RequestResponseLogger;
import com.boilerplate.app.base.util.TraceIdUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Base logging interceptor for HTTP requests/responses.
 * Each service should create a @Component that extends this class.
 */
public class ControllerLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler
    ) {
        // Generate trace ID and store in MDC for use across the request lifecycle
        String traceId = TraceIdUtil.generateAndSet();
        long startTime = System.currentTimeMillis();

        request.setAttribute(TraceConstants.REQUEST_ATTRIBUTE_TRACE_ID, traceId);
        request.setAttribute(TraceConstants.REQUEST_ATTRIBUTE_START_TIME, startTime);

        // Log REQUEST here (before controller method executes) to ensure correct sequence:
        // REQUEST controller -> Service -> RESPONSE controller
        // Body is already cached by filter (ArtajasaHeaderValidationFilter) in request attribute
        Map<String, String> requestHeaders = getHeaders(request);
        byte[] requestBody = getRequestBody(request);
        RequestResponseLogger.logRequest(
            traceId,
            request.getMethod(),
            request.getRequestURL().toString(),
            requestHeaders,
            requestBody
        );

        return true;
    }

    @Override
    public void postHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler,
        @Nullable ModelAndView modelAndView
    ) {
        String traceId = (String) request.getAttribute(TraceConstants.REQUEST_ATTRIBUTE_TRACE_ID);
        Long startTime = (Long) request.getAttribute(TraceConstants.REQUEST_ATTRIBUTE_START_TIME);
        long elapsedTime = startTime != null ? System.currentTimeMillis() - startTime : 0;

        // Log RESPONSE here (after controller method executes) to ensure correct sequence:
        // REQUEST controller -> Service -> RESPONSE controller
        Map<String, String> headers = getResponseHeaders(response);
        byte[] body = getResponseBody(response);
        
        RequestResponseLogger.logResponse(traceId, response.getStatus(), headers, body, elapsedTime);
    }

    @Override
    public void afterCompletion(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler,
        @Nullable Exception ex
    ) {
        if (ex != null) {
            String traceId = TraceIdUtil.get();
            
            RequestResponseLogger.logError(
                traceId,
                request.getMethod(),
                request.getRequestURL().toString(),
                ex,
                response.getStatus()
            );
        }
        
        TraceIdUtil.clear();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            headers.put(headerName, request.getHeader(headerName));
        });
        return headers;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        response.getHeaderNames().forEach(headerName -> {
            headers.put(headerName, response.getHeader(headerName));
        });
        return headers;
    }

    private byte[] getRequestBody(HttpServletRequest request) {
        // First, try to get from request attribute (set by custom wrappers like ArtajasaHeaderValidationFilter)
        Object cachedBodyAttr = request.getAttribute(TraceConstants.REQUEST_ATTRIBUTE_CACHED_BODY);
        if (cachedBodyAttr instanceof byte[]) {
            return (byte[]) cachedBodyAttr;
        }
        
        // Fallback to ContentCachingRequestWrapper if available
        if (request instanceof ContentCachingRequestWrapper cachedRequest) {
            // By the time this is called in postHandle, Spring has already read the body
            // for @RequestBody and ContentCachingRequestWrapper has cached it.
            byte[] body = cachedRequest.getContentAsByteArray();
            if (body != null && body.length > 0) {
                return body;
            }
        }
        
        return null;
    }

    private byte[] getResponseBody(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper cachedResponse = (ContentCachingResponseWrapper) response;
            return cachedResponse.getContentAsByteArray();
        }
        return null;
    }
}

