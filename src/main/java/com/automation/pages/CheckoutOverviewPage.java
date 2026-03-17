package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the Checkout Overview Page (/checkout-step-two.html).
 */
public class CheckoutOverviewPage extends BasePage {

    // ============ LOCATORS ============
    private final Locator pageTitle;
    private final Locator summaryItems;
    private final Locator subtotalLabel;
    private final Locator taxLabel;
    private final Locator totalLabel;
    private final Locator finishButton;
    private final Locator cancelButton;

    // ============ CONSTRUCTOR ============
    public CheckoutOverviewPage(Page page) {
        super(page);
        this.pageTitle = page.locator("[data-test='title']");
        this.summaryItems = page.locator("[data-test='inventory-item']");
        this.subtotalLabel = page.locator("[data-test='subtotal-label']");
        this.taxLabel = page.locator("[data-test='tax-label']");
        this.totalLabel = page.locator("[data-test='total-label']");
        this.finishButton = page.locator("[data-test='finish']");
        this.cancelButton = page.locator("[data-test='cancel']");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Get the subtotal text.
     */
    public String getSubtotal() {
        return getText(subtotalLabel);
    }

    /**
     * Get the tax text.
     */
    public String getTax() {
        return getText(taxLabel);
    }

    /**
     * Get the total text.
     */
    public String getTotal() {
        return getText(totalLabel);
    }

    /**
     * Get number of items in checkout summary.
     */
    public int getSummaryItemCount() {
        return summaryItems.count();
    }

    /**
     * Click Finish to complete the order.
     */
    public CheckoutCompletePage finishOrder() {
        click(finishButton);
        logger.info("Order finished");
        return new CheckoutCompletePage(page);
    }

    /**
     * Cancel and go back to inventory.
     */
    public InventoryPage cancel() {
        click(cancelButton);
        logger.info("Checkout overview cancelled");
        return new InventoryPage(page);
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(pageTitle) && isVisible(finishButton);
    }
}
