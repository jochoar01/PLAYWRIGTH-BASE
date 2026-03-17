package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.InventoryPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Login functionality.
 * Covers positive and negative login scenarios.
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "createContextAndPage")
    public void navigateToLoginPage() {
        loginPage = openLoginPage();
    }

    // ==================== POSITIVE TESTS ====================

    @Test(description = "Verify login page loads correctly with all required elements")
    public void testLoginPageLoadsSuccessfully() {
        assertThat(loginPage.isPageLoaded())
                .as("Login page should be fully loaded")
                .isTrue();

        assertThat(loginPage.isUsernameFieldVisible())
                .as("Username field should be visible")
                .isTrue();

        assertThat(loginPage.isPasswordFieldVisible())
                .as("Password field should be visible")
                .isTrue();

        assertThat(loginPage.isLoginButtonVisible())
                .as("Login button should be visible")
                .isTrue();
    }

    @Test(description = "Verify successful login with valid credentials redirects to inventory page")
    public void testSuccessfulLogin() {
        InventoryPage inventoryPage = loginPage.loginAs(
                config.getValidUsername(),
                config.getValidPassword()
        );

        assertThat(inventoryPage.isPageLoaded())
                .as("Inventory page should be loaded after successful login")
                .isTrue();

        assertThat(page.url())
                .as("URL should contain 'inventory'")
                .contains("inventory");

        assertThat(inventoryPage.getPageTitleText())
                .as("Page title should be 'Products'")
                .isEqualTo("Products");
    }

    @Test(description = "Verify successful login displays inventory items")
    public void testLoginShowsProducts() {
        InventoryPage inventoryPage = loginPage.loginAs(
                config.getValidUsername(),
                config.getValidPassword()
        );

        assertThat(inventoryPage.getInventoryItemCount())
                .as("Inventory should have 6 products")
                .isEqualTo(6);
    }

    // ==================== NEGATIVE TESTS ====================

    @Test(description = "Verify error message for locked out user")
    public void testLockedOutUserLogin() {
        loginPage.loginExpectingError(
                config.getLockedUsername(),
                config.getValidPassword()
        );

        assertThat(loginPage.isErrorDisplayed())
                .as("Error message should be displayed for locked user")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error message should indicate user is locked out")
                .contains("locked out");
    }

    @Test(description = "Verify error message for empty username")
    public void testLoginWithEmptyUsername() {
        loginPage.loginExpectingError("", config.getValidPassword());

        assertThat(loginPage.isErrorDisplayed())
                .as("Error message should be displayed")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error should mention username is required")
                .contains("Username is required");
    }

    @Test(description = "Verify error message for empty password")
    public void testLoginWithEmptyPassword() {
        loginPage.loginExpectingError(config.getValidUsername(), "");

        assertThat(loginPage.isErrorDisplayed())
                .as("Error message should be displayed")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error should mention password is required")
                .contains("Password is required");
    }

    @Test(description = "Verify error message for invalid credentials")
    public void testLoginWithInvalidCredentials() {
        loginPage.loginExpectingError("invalid_user", "wrong_password");

        assertThat(loginPage.isErrorDisplayed())
                .as("Error message should be displayed for invalid credentials")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error should indicate credentials don't match")
                .contains("do not match");
    }
}
