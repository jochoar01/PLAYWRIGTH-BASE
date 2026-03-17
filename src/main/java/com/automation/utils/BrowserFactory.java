package com.automation.utils;

import com.automation.config.ConfigManager;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory class to create and configure Playwright browser instances.
 * Supports Chromium, Firefox, and WebKit.
 */
public class BrowserFactory {

    private static final Logger logger = LogManager.getLogger(BrowserFactory.class);

    private BrowserFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a Browser instance based on configuration.
     */
    public static Browser createBrowser(Playwright playwright) {
        ConfigManager config = ConfigManager.getInstance();
        String browserType = config.getBrowser().toLowerCase();
        boolean headless = config.isHeadless();
        double slowMo = config.getSlowMo();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        Browser browser;

        switch (browserType) {
            case "firefox":
                logger.info("Launching Firefox browser (headless={})", headless);
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                logger.info("Launching WebKit browser (headless={})", headless);
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "chromium":
            default:
                logger.info("Launching Chromium browser (headless={})", headless);
                browser = playwright.chromium().launch(launchOptions);
                break;
        }

        return browser;
    }

    /**
     * Creates a new BrowserContext with configured viewport and timeout settings.
     */
    public static BrowserContext createContext(Browser browser) {
        ConfigManager config = ConfigManager.getInstance();

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(config.getViewportWidth(), config.getViewportHeight());

        BrowserContext context = browser.newContext(contextOptions);
        context.setDefaultTimeout(config.getDefaultTimeout());
        context.setDefaultNavigationTimeout(config.getNavigationTimeout());

        logger.info("Browser context created with viewport {}x{}",
                config.getViewportWidth(), config.getViewportHeight());

        return context;
    }
}
