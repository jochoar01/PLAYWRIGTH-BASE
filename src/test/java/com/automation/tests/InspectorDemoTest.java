package com.automation.tests;

import com.automation.base.BaseTest;
import org.testng.annotations.Test;

/**
 * Demo test to practice using Playwright Inspector.
 * This test will pause and open the Inspector for interactive debugging.
 */
public class InspectorDemoTest extends BaseTest {

    @Test(description = "Demo: Using Playwright Inspector to explore and capture locators")
    public void testWithInspector() {
        // Navigate to SauceDemo
        page.navigate("https://www.saucedemo.com/");
        
        // Wait a moment for page to load
        page.waitForLoadState();
        
        // PAUSE - Inspector opens here!
        // You'll see a window with:
        // - Locator picker (click elements)
        // - Step controls (resume, step over)
        // - Console to test selectors
        page.pause();
        
        // After exploring, click Resume to continue...
        // The test will continue here
    }
}
