package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Inventory/Products page functionality.
 * Covers product listing, sorting, and cart operations.
 */
public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "createContextAndPage")
    public void loginAndNavigateToInventory() {
        inventoryPage = loginWithValidCredentials();
    }

    // ==================== PRODUCT LISTING TESTS ====================

    @Test(description = "Verify inventory page displays all 6 products")
    public void testInventoryDisplaysAllProducts() {
        assertThat(inventoryPage.isPageLoaded())
                .as("Inventory page should be loaded")
                .isTrue();

        assertThat(inventoryPage.getInventoryItemCount())
                .as("Should display exactly 6 products")
                .isEqualTo(6);
    }

    @Test(description = "Verify page title is 'Products'")
    public void testInventoryPageTitle() {
        assertThat(inventoryPage.getPageTitleText())
                .as("Page title should be 'Products'")
                .isEqualTo("Products");
    }

    @Test(description = "Verify all product names are not empty")
    public void testProductNamesAreNotEmpty() {
        List<String> itemNames = inventoryPage.getAllItemNames();

        assertThat(itemNames)
                .as("Product names list should not be empty")
                .isNotEmpty()
                .hasSize(6);

        assertThat(itemNames)
                .as("No product name should be blank")
                .noneMatch(String::isBlank);
    }

    // ==================== SORTING TESTS ====================

    @Test(description = "Verify products can be sorted A to Z")
    public void testSortProductsAtoZ() {
        inventoryPage.sortBy("az");
        List<String> names = inventoryPage.getAllItemNames();

        assertThat(names)
                .as("Products should be sorted alphabetically A-Z")
                .isSorted();
    }

    @Test(description = "Verify products can be sorted Z to A")
    public void testSortProductsZtoA() {
        inventoryPage.sortBy("za");
        List<String> names = inventoryPage.getAllItemNames();

        assertThat(names)
                .as("Products should be sorted alphabetically Z-A")
                .isSortedAccordingTo((a, b) -> b.compareTo(a));
    }

    @Test(description = "Verify products can be sorted by price low to high")
    public void testSortProductsByPriceLowToHigh() {
        inventoryPage.sortBy("lohi");
        List<String> prices = inventoryPage.getAllItemPrices();

        List<Double> numericPrices = prices.stream()
                .map(p -> Double.parseDouble(p.replace("$", "")))
                .toList();

        assertThat(numericPrices)
                .as("Prices should be sorted low to high")
                .isSorted();
    }

    @Test(description = "Verify products can be sorted by price high to low")
    public void testSortProductsByPriceHighToLow() {
        inventoryPage.sortBy("hilo");
        List<String> prices = inventoryPage.getAllItemPrices();

        List<Double> numericPrices = prices.stream()
                .map(p -> Double.parseDouble(p.replace("$", "")))
                .toList();

        assertThat(numericPrices)
                .as("Prices should be sorted high to low")
                .isSortedAccordingTo((a, b) -> Double.compare(b, a));
    }

    // ==================== ADD TO CART TESTS ====================

    @Test(description = "Verify adding a single item to cart updates the badge")
    public void testAddSingleItemToCart() {
        inventoryPage.addItemToCartByIndex(0);

        assertThat(inventoryPage.isCartBadgeDisplayed())
                .as("Cart badge should be visible after adding item")
                .isTrue();

        assertThat(inventoryPage.getCartBadgeCount())
                .as("Cart badge should show 1 item")
                .isEqualTo(1);
    }

    @Test(description = "Verify adding multiple items to cart updates the badge correctly")
    public void testAddMultipleItemsToCart() {
        inventoryPage
                .addItemToCartByIndex(0)
                .addItemToCartByIndex(1)
                .addItemToCartByIndex(2);

        assertThat(inventoryPage.getCartBadgeCount())
                .as("Cart badge should show 3 items")
                .isEqualTo(3);
    }

    @Test(description = "Verify removing an item from cart updates the badge")
    public void testRemoveItemFromCart() {
        List<String> itemNames = inventoryPage.getAllItemNames();
        String firstItemName = itemNames.get(0);

        inventoryPage.addItemToCartByIndex(0);
        assertThat(inventoryPage.getCartBadgeCount()).isEqualTo(1);

        inventoryPage.removeItemByName(firstItemName);

        assertThat(inventoryPage.isCartBadgeDisplayed())
                .as("Cart badge should not be visible after removing all items")
                .isFalse();
    }

    // ==================== LOGOUT TEST ====================

    @Test(description = "Verify user can successfully logout")
    public void testLogout() {
        LoginPage loginPage = inventoryPage.logout();

        assertThat(loginPage.isPageLoaded())
                .as("Login page should be displayed after logout")
                .isTrue();

        assertThat(page.url())
                .as("URL should be the base URL after logout")
                .isEqualTo(config.getBaseUrl());
    }
}
