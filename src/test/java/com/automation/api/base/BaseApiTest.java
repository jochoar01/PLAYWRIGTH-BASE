package com.automation.api.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Base class for API tests using Playwright.
 * Manages APIRequestContext lifecycle.
 */
public abstract class BaseApiTest {

    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);
    
    protected static Playwright playwright;
    protected static APIRequestContext request;
    
    // Base URLs for different environments
    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    
    @BeforeSuite(alwaysRun = true)
    public void setupApi() {
        logger.info("============ API TEST SUITE STARTED ============");
        
        playwright = Playwright.create();
        
        // Create API request context with default configuration
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URL)
                .setExtraHTTPHeaders(getDefaultHeaders())
                .setTimeout(30000)); // 30 seconds timeout
        
        logger.info("API Request Context created with base URL: {}", BASE_URL);
    }
    
    @AfterSuite(alwaysRun = true)
    public void teardownApi() {
        if (request != null) {
            request.dispose();
            logger.info("API Request Context disposed");
        }
        
        if (playwright != null) {
            playwright.close();
            logger.info("============ API TEST SUITE FINISHED ============");
        }
    }
    
    /**
     * Default headers for all API requests.
     * Override in subclasses if needed.
     */
    protected java.util.Map<String, String> getDefaultHeaders() {
        return java.util.Map.of(
            "Content-Type", "application/json",
            "Accept", "application/json"
        );
    }
    
    /**
     * Log request details for debugging.
     */
    protected void logRequest(String method, String endpoint, String body) {
        logger.info("API Request - Method: {}, Endpoint: {}", method, endpoint);
        if (body != null && !body.isEmpty()) {
            logger.debug("Request Body: {}", body);
        }
    }
    
    /**
     * Log response details for debugging.
     */
    protected void logResponse(int status, String body) {
        logger.info("API Response - Status: {}", status);
        logger.debug("Response Body: {}", body);
    }
}
