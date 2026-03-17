package com.automation.base;

import com.automation.config.ConfigManager;
import com.automation.pages.LoginPage;
import com.automation.pages.InventoryPage;
import com.automation.utils.BrowserFactory;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Base test class that handles Playwright lifecycle management.
 * All test classes should extend this class.
 *
 * Lifecycle:
 * - @BeforeSuite: Creates Playwright instance
 * - @BeforeClass: Launches browser
 * - @BeforeMethod: Creates new context and page (test isolation)
 * - @AfterMethod: Closes context (clean state per test)
 * - @AfterClass: Closes browser
 * - @AfterSuite: Closes Playwright
 */
public abstract class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static ConfigManager config;

    private static Playwright playwright;
    private static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        config = ConfigManager.getInstance();
        playwright = Playwright.create();
        logger.info("============ TEST SUITE STARTED ============");
    }

    @BeforeClass(alwaysRun = true)
    public void launchBrowser() {
        browser = BrowserFactory.createBrowser(playwright);
        logger.info("Browser launched for test class: {}", this.getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void createContextAndPage() {
        context = BrowserFactory.createContext(browser);

        // Enable tracing for debugging (can be configured)
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));

        page = context.newPage();
        logger.info("New browser context and page created");
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext(ITestResult result) {
        if (context != null) {
            // Save trace with test name
            String testName = result.getMethod().getMethodName();
            String tracePath = "target/traces/" + testName + ".zip";
            
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(java.nio.file.Paths.get(tracePath)));
            
            logger.info("Trace saved: {}", tracePath);
            context.close();
            logger.info("Browser context closed");
        }
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
            logger.info("Browser closed for test class: {}", this.getClass().getSimpleName());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void globalTeardown() {
        if (playwright != null) {
            playwright.close();
            logger.info("============ TEST SUITE FINISHED ============");
        }
    }

    // ============ HELPER METHODS ============

    /**
     * Navigate to the base URL and return a LoginPage.
     */
    protected LoginPage openLoginPage() {
        return new LoginPage(page).open(config.getBaseUrl());
    }

    /**
     * Perform login with valid credentials and return InventoryPage.
     */
    protected InventoryPage loginWithValidCredentials() {
        LoginPage loginPage = openLoginPage();
        return loginPage.loginAs(config.getValidUsername(), config.getValidPassword());
    }
}
