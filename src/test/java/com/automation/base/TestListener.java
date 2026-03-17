package com.automation.base;

import com.automation.utils.ScreenshotHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for handling test events.
 * Takes screenshots on failure and logs test results.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(">>> TEST STARTED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("<<< TEST PASSED: {} ({}ms)",
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("<<< TEST FAILED: {} - {}",
                result.getMethod().getMethodName(),
                result.getThrowable().getMessage());

        // Take screenshot on failure
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) testInstance;
            if (baseTest.page != null) {
                ScreenshotHelper.takeFailureScreenshot(
                        baseTest.page,
                        result.getMethod().getMethodName()
                );
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("<<< TEST SKIPPED: {}", result.getMethod().getMethodName());
    }
}
