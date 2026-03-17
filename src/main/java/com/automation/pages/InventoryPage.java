package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

/**
 * Page Object for the Inventory/Products Page.
 * Displayed after successful login at /inventory.html
 */
public class InventoryPage extends BasePage {

    // ============ LOCATORS ============
    private final Locator pageTitle;
    private final Locator inventoryItems;
    private final Locator shoppingCartBadge;
    private final Locator shoppingCartLink;
    private final Locator burgerMenuButton;
    private final Locator logoutLink;
    private final Locator sortDropdown;
    private final Locator inventoryItemNames;
    private final Locator inventoryItemPrices;

    // ============ CONSTRUCTOR ============
    public InventoryPage(Page page) {
        super(page);
        this.pageTitle = page.locator("[data-test='title']");
        this.inventoryItems = page.locator("[data-test='inventory-item']");
        this.shoppingCartBadge = page.locator("[data-test='shopping-cart-badge']");
        this.shoppingCartLink = page.locator("[data-test='shopping-cart-link']");
        this.burgerMenuButton = page.locator("#react-burger-menu-btn");
        this.logoutLink = page.locator("[data-test='logout-sidebar-link']");
        this.sortDropdown = page.locator("[data-test='product-sort-container']");
        this.inventoryItemNames = page.locator("[data-test='inventory-item-name']");
        this.inventoryItemPrices = page.locator("[data-test='inventory-item-price']");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Get the page title text.
     */
    public String getPageTitleText() {
        return getText(pageTitle);
    }

    /**
     * Get the number of inventory items displayed.
     */
    public int getInventoryItemCount() {
        return inventoryItems.count();
    }

    /**
     * Add an item to the cart by its index (0-based).
     */
    public InventoryPage addItemToCartByIndex(int index) {
        Locator addButton = inventoryItems.nth(index)
                .locator("button:has-text('Add to cart')");
        click(addButton);
        logger.info("Item at index {} added to cart", index);
        return this;
    }

    /**
     * Add an item to the cart by its name.
     */
    public InventoryPage addItemToCartByName(String itemName) {
        String buttonId = "add-to-cart-" + itemName.toLowerCase()
                .replace(" ", "-");
        Locator addButton = page.locator("[data-test='" + buttonId + "']");
        click(addButton);
        logger.info("Item '{}' added to cart", itemName);
        return this;
    }

    /**
     * Remove an item from the cart by its name.
     */
    public InventoryPage removeItemByName(String itemName) {
        String buttonId = "remove-" + itemName.toLowerCase()
                .replace(" ", "-");
        Locator removeButton = page.locator("[data-test='" + buttonId + "']");
        click(removeButton);
        logger.info("Item '{}' removed from cart", itemName);
        return this;
    }

    /**
     * Get the cart badge count (number of items in cart).
     */
    public int getCartBadgeCount() {
        if (isVisible(shoppingCartBadge)) {
            return Integer.parseInt(getText(shoppingCartBadge).trim());
        }
        return 0;
    }

    /**
     * Click on the shopping cart icon.
     */
    public CartPage goToCart() {
        click(shoppingCartLink);
        logger.info("Navigated to cart");
        return new CartPage(page);
    }

    /**
     * Sort products by a given option value.
     * Options: "az" (A-Z), "za" (Z-A), "lohi" (Low-High), "hilo" (High-Low)
     */
    public InventoryPage sortBy(String option) {
        sortDropdown.selectOption(option);
        logger.info("Products sorted by: {}", option);
        return this;
    }

    /**
     * Get all item names displayed on the page.
     */
    public List<String> getAllItemNames() {
        return inventoryItemNames.allTextContents();
    }

    /**
     * Get all item prices as strings.
     */
    public List<String> getAllItemPrices() {
        return inventoryItemPrices.allTextContents();
    }

    /**
     * Perform logout through the burger menu.
     */
    public LoginPage logout() {
        click(burgerMenuButton);
        logoutLink.waitFor();
        click(logoutLink);
        logger.info("User logged out");
        return new LoginPage(page);
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(pageTitle) && inventoryItems.count() > 0;
    }

    /**
     * Check if the cart badge is displayed.
     */
    public boolean isCartBadgeDisplayed() {
        return isVisible(shoppingCartBadge);
    }
}
