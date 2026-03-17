package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Cart and Checkout flow.
 * Covers the complete purchase flow from adding items to order confirmation.
 */
public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "createContextAndPage")
    public void loginAndNavigateToInventory() {
        inventoryPage = loginWithValidCredentials();
    }

    // ==================== CART PAGE TESTS ====================

    @Test(description = "Verify navigating to an empty cart shows no items")
    public void testEmptyCart() {
        CartPage cartPage = inventoryPage.goToCart();

        assertThat(cartPage.isPageLoaded())
                .as("Cart page should be loaded")
                .isTrue();

        assertThat(cartPage.getPageTitleText())
                .as("Cart page title should be 'Your Cart'")
                .isEqualTo("Your Cart");

        assertThat(cartPage.isCartEmpty())
                .as("Cart should be empty")
                .isTrue();
    }

    @Test(description = "Verify added items appear in the cart")
    public void testItemsAppearInCart() {
        inventoryPage
                .addItemToCartByIndex(0)
                .addItemToCartByIndex(1);

        List<String> expectedItems = inventoryPage.getAllItemNames()
                .subList(0, 2);

        CartPage cartPage = inventoryPage.goToCart();

        assertThat(cartPage.getCartItemCount())
                .as("Cart should contain 2 items")
                .isEqualTo(2);

        List<String> cartItemNames = cartPage.getCartItemNames();
        assertThat(cartItemNames)
                .as("Cart should contain the added items")
                .containsExactlyInAnyOrderElementsOf(expectedItems);
    }

    @Test(description = "Verify removing an item from the cart page")
    public void testRemoveItemFromCartPage() {
        inventoryPage.addItemToCartByIndex(0);

        CartPage cartPage = inventoryPage.goToCart();
        assertThat(cartPage.getCartItemCount()).isEqualTo(1);

        String itemName = cartPage.getCartItemNames().get(0);
        cartPage.removeItemByName(itemName);

        assertThat(cartPage.isCartEmpty())
                .as("Cart should be empty after removing the item")
                .isTrue();
    }

    @Test(description = "Verify Continue Shopping returns to inventory page")
    public void testContinueShopping() {
        CartPage cartPage = inventoryPage.goToCart();
        InventoryPage returnedPage = cartPage.continueShopping();

        assertThat(returnedPage.isPageLoaded())
                .as("Should return to inventory page")
                .isTrue();

        assertThat(page.url())
                .as("URL should contain 'inventory'")
                .contains("inventory");
    }

    // ==================== CHECKOUT TESTS ====================

    @Test(description = "Verify checkout requires filling in personal information")
    public void testCheckoutRequiresInfo() {
        inventoryPage.addItemToCartByIndex(0);

        CheckoutPage checkoutPage = inventoryPage
                .goToCart()
                .proceedToCheckout();

        assertThat(checkoutPage.isPageLoaded())
                .as("Checkout page should be loaded")
                .isTrue();

        // Try to continue without filling info
        checkoutPage.clickContinueExpectingError();

        assertThat(checkoutPage.isErrorDisplayed())
                .as("Error should be displayed when info is missing")
                .isTrue();

        assertThat(checkoutPage.getErrorMessage())
                .as("Error should mention first name is required")
                .contains("First Name");
    }

    @Test(description = "Verify checkout overview shows correct item count")
    public void testCheckoutOverviewItemCount() {
        inventoryPage
                .addItemToCartByIndex(0)
                .addItemToCartByIndex(1);

        CheckoutOverviewPage overviewPage = inventoryPage
                .goToCart()
                .proceedToCheckout()
                .fillCheckoutInfo("John", "Doe", "12345")
                .clickContinue();

        assertThat(overviewPage.isPageLoaded())
                .as("Checkout overview page should be loaded")
                .isTrue();

        assertThat(overviewPage.getSummaryItemCount())
                .as("Overview should show 2 items")
                .isEqualTo(2);
    }

    @Test(description = "Verify checkout overview displays price information")
    public void testCheckoutOverviewPriceInfo() {
        inventoryPage.addItemToCartByIndex(0);

        CheckoutOverviewPage overviewPage = inventoryPage
                .goToCart()
                .proceedToCheckout()
                .fillCheckoutInfo("Jane", "Smith", "54321")
                .clickContinue();

        assertThat(overviewPage.getSubtotal())
                .as("Subtotal should contain a dollar amount")
                .contains("$");

        assertThat(overviewPage.getTax())
                .as("Tax should contain a dollar amount")
                .contains("$");

        assertThat(overviewPage.getTotal())
                .as("Total should contain a dollar amount")
                .contains("$");
    }

    // ==================== END-TO-END PURCHASE FLOW ====================

    @Test(description = "Verify complete purchase flow from login to order confirmation")
    public void testCompletePurchaseFlow() {
        // Step 1: Add items to cart
        inventoryPage
                .addItemToCartByIndex(0)
                .addItemToCartByIndex(2);

        assertThat(inventoryPage.getCartBadgeCount())
                .as("Cart should show 2 items")
                .isEqualTo(2);

        // Step 2: Go to cart and verify items
        CartPage cartPage = inventoryPage.goToCart();
        assertThat(cartPage.getCartItemCount())
                .as("Cart should contain 2 items")
                .isEqualTo(2);

        // Step 3: Proceed to checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        assertThat(checkoutPage.isPageLoaded())
                .as("Checkout page should be loaded")
                .isTrue();

        // Step 4: Fill checkout info and continue
        CheckoutOverviewPage overviewPage = checkoutPage
                .fillCheckoutInfo("Test", "User", "90210")
                .clickContinue();

        assertThat(overviewPage.isPageLoaded())
                .as("Overview page should be loaded")
                .isTrue();

        assertThat(overviewPage.getSummaryItemCount())
                .as("Overview should show 2 items")
                .isEqualTo(2);

        // Step 5: Complete order
        CheckoutCompletePage completePage = overviewPage.finishOrder();

        assertThat(completePage.isPageLoaded())
                .as("Checkout complete page should be loaded")
                .isTrue();

        assertThat(completePage.getCompleteHeader())
                .as("Should display 'Thank you for your order!' message")
                .isEqualTo("Thank you for your order!");

        assertThat(completePage.isPonyExpressImageVisible())
                .as("Pony express image should be visible")
                .isTrue();

        // Step 6: Return to products
        InventoryPage returnedPage = completePage.backToHome();
        assertThat(returnedPage.isPageLoaded())
                .as("Should return to inventory page")
                .isTrue();
    }
}
