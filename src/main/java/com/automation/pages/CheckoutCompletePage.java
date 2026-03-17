package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the Checkout Complete Page (/checkout-complete.html).
 */
public class CheckoutCompletePage extends BasePage {

    // ============ LOCATORS ============
    private final Locator pageTitle;
    private final Locator completeHeader;
    private final Locator completeText;
    private final Locator backHomeButton;
    private final Locator ponyExpressImage;

    // ============ CONSTRUCTOR ============
    public CheckoutCompletePage(Page page) {
        super(page);
        this.pageTitle = page.locator("[data-test='title']");
        this.completeHeader = page.locator("[data-test='complete-header']");
        this.completeText = page.locator("[data-test='complete-text']");
        this.backHomeButton = page.locator("[data-test='back-to-products']");
        this.ponyExpressImage = page.locator("[data-test='pony-express']");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Get the confirmation header text.
     */
    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    /**
     * Get the confirmation description text.
     */
    public String getCompleteText() {
        return getText(completeText);
    }

    /**
     * Click Back Home to return to inventory.
     */
    public InventoryPage backToHome() {
        click(backHomeButton);
        logger.info("Navigated back to home");
        return new InventoryPage(page);
    }

    /**
     * Check if the pony express image is visible.
     */
    public boolean isPonyExpressImageVisible() {
        return isVisible(ponyExpressImage);
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(completeHeader) && isVisible(backHomeButton);
    }
}
