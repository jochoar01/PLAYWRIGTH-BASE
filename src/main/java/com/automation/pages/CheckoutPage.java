package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the Checkout Step One Page (/checkout-step-one.html).
 */
public class CheckoutPage extends BasePage {

    // ============ LOCATORS ============
    private final Locator pageTitle;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalCodeInput;
    private final Locator continueButton;
    private final Locator cancelButton;
    private final Locator errorMessage;

    // ============ CONSTRUCTOR ============
    public CheckoutPage(Page page) {
        super(page);
        this.pageTitle = page.locator("[data-test='title']");
        this.firstNameInput = page.locator("[data-test='firstName']");
        this.lastNameInput = page.locator("[data-test='lastName']");
        this.postalCodeInput = page.locator("[data-test='postalCode']");
        this.continueButton = page.locator("[data-test='continue']");
        this.cancelButton = page.locator("[data-test='cancel']");
        this.errorMessage = page.locator("[data-test='error']");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Fill in checkout information.
     */
    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        fill(firstNameInput, firstName);
        fill(lastNameInput, lastName);
        fill(postalCodeInput, postalCode);
        logger.info("Checkout info filled: {} {} {}", firstName, lastName, postalCode);
        return this;
    }

    /**
     * Click continue to go to checkout step two.
     */
    public CheckoutOverviewPage clickContinue() {
        click(continueButton);
        logger.info("Continuing to checkout overview");
        return new CheckoutOverviewPage(page);
    }

    /**
     * Click continue expecting an error (missing fields).
     */
    public CheckoutPage clickContinueExpectingError() {
        click(continueButton);
        logger.info("Continue clicked (expecting error)");
        return this;
    }

    /**
     * Cancel checkout and return to cart.
     */
    public CartPage cancelCheckout() {
        click(cancelButton);
        logger.info("Checkout cancelled");
        return new CartPage(page);
    }

    /**
     * Get error message text.
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed.
     */
    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(pageTitle) && isVisible(continueButton);
    }
}
