package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

/**
 * Page Object for the Shopping Cart Page (/cart.html).
 */
public class CartPage extends BasePage {

    // ============ LOCATORS ============
    private final Locator pageTitle;
    private final Locator cartItems;
    private final Locator cartItemNames;
    private final Locator continueShoppingButton;
    private final Locator checkoutButton;

    // ============ CONSTRUCTOR ============
    public CartPage(Page page) {
        super(page);
        this.pageTitle = page.locator("[data-test='title']");
        this.cartItems = page.locator("[data-test='inventory-item']");
        this.cartItemNames = page.locator("[data-test='inventory-item-name']");
        this.continueShoppingButton = page.locator("[data-test='continue-shopping']");
        this.checkoutButton = page.locator("[data-test='checkout']");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Get the cart page title text.
     */
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    /**
     * Get the number of items in the cart.
     */
    public int getCartItemCount() {
        return cartItems.count();
    }

    /**
     * Get all item names in the cart.
     */
    public List<String> getCartItemNames() {
        return cartItemNames.allTextContents();
    }

    /**
     * Remove an item from the cart by its name.
     */
    public CartPage removeItemByName(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase()
                .replace(" ", "-");
        Locator removeButton = page.locator("[data-test='" + buttonId + "']");
        click(removeButton);
        logger.info("Item '{}' removed from cart", itemName);
        return this;
    }

    /**
     * Click Continue Shopping to go back to inventory.
     */
    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        logger.info("Navigated back to inventory");
        return new InventoryPage(page);
    }

    /**
     * Click Checkout button to proceed to checkout.
     */
    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        logger.info("Proceeding to checkout");
        return new CheckoutPage(page);
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(pageTitle);
    }

    /**
     * Check if cart is empty.
     */
    public boolean isCartEmpty() {
        return cartItems.count() == 0;
    }
}
