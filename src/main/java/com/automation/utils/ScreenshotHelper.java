package com.automation.utils;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots during test execution.
 */
public class ScreenshotHelper {

    private static final Logger logger = LogManager.getLogger(ScreenshotHelper.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    private ScreenshotHelper() {
        // Prevent instantiation
    }

    /**
     * Takes a full-page screenshot and saves it with a timestamped name.
     */
    public static String takeScreenshot(Page page, String testName) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(fileName))
                .setFullPage(true));

        logger.info("Screenshot saved: {}", fileName);
        return fileName;
    }

    /**
     * Takes a screenshot on test failure.
     */
    public static String takeFailureScreenshot(Page page, String testName) {
        return takeScreenshot(page, "FAILURE_" + testName);
    }
}
