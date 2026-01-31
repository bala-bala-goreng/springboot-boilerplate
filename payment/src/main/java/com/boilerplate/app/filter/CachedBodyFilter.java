package com.boilerplate.app.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Service-specific cached body filter for Payment service.
 * Extends the base CachedBodyFilter.
 */
@Component
@Order(1)
public class CachedBodyFilter extends com.boilerplate.app.base.filter.CachedBodyFilter {
    // Service-specific implementation can be added here if needed
}
