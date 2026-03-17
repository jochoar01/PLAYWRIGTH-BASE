package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base page class that all Page Objects inherit from.
 * Contains common methods and utilities shared across pages.
 */
public abstract class BasePage {

    protected final Page page;
    protected final Logger logger;

    protected BasePage(Page page) {
        this.page = page;
        this.logger = LogManager.getLogger(this.getClass());
    }

    /**
     * Navigate to a specific URL.
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        page.navigate(url);
    }

    /**
     * Get the current page title.
     */
    public String getPageTitle() {
        return page.title();
    }

    /**
     * Get the current URL.
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Wait for an element to be visible.
     */
    protected void waitForElementVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Wait for an element to be hidden.
     */
    protected void waitForElementHidden(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN));
    }

    /**
     * Click an element with logging.
     */
    protected void click(Locator locator) {
        logger.debug("Clicking element: {}", locator);
        locator.click();
    }

    /**
     * Fill a text field with logging.
     */
    protected void fill(Locator locator, String text) {
        logger.debug("Filling element with text: {}", text);
        locator.fill(text);
    }

    /**
     * Get text content from a locator.
     */
    protected String getText(Locator locator) {
        return locator.textContent();
    }

    /**
     * Check if an element is visible.
     */
    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    /**
     * Verify that the page has loaded by checking a key element.
     * Each page should override this method.
     */
    public abstract boolean isPageLoaded();
}
