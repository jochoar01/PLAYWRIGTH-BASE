package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the Login Page (https://www.saucedemo.com/).
 * Encapsulates all login-related interactions and validations.
 */
public class LoginPage extends BasePage {

    // ============ LOCATORS ============
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator loginLogo;

    // ============ CONSTRUCTOR ============
    public LoginPage(Page page) {
        super(page);
        this.usernameInput = page.locator("[data-test='username']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-button']");
        this.errorMessage = page.locator("[data-test='error']");
        this.loginLogo = page.locator(".login_logo");
    }

    // ============ PAGE ACTIONS ============

    /**
     * Navigate to the login page.
     */
    public LoginPage open(String baseUrl) {
        navigateTo(baseUrl);
        logger.info("Login page opened");
        return this;
    }

    /**
     * Enter username in the username field.
     */
    public LoginPage enterUsername(String username) {
        fill(usernameInput, username);
        logger.info("Username entered: {}", username);
        return this;
    }

    /**
     * Enter password in the password field.
     */
    public LoginPage enterPassword(String password) {
        fill(passwordInput, password);
        logger.info("Password entered");
        return this;
    }

    /**
     * Click the login button.
     */
    public LoginPage clickLoginButton() {
        click(loginButton);
        logger.info("Login button clicked");
        return this;
    }

    /**
     * Perform a complete login with username and password.
     * Returns InventoryPage on successful login.
     */
    public InventoryPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Login performed with user: {}", username);
        return new InventoryPage(page);
    }

    /**
     * Perform login expecting failure (returns this page).
     */
    public LoginPage loginExpectingError(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Login attempted (expecting error) with user: {}", username);
        return this;
    }

    // ============ PAGE VALIDATIONS ============

    @Override
    public boolean isPageLoaded() {
        return isVisible(loginLogo) && isVisible(loginButton);
    }

    /**
     * Check if an error message is displayed.
     */
    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    /**
     * Get the error message text.
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if the username field is visible.
     */
    public boolean isUsernameFieldVisible() {
        return isVisible(usernameInput);
    }

    /**
     * Check if the password field is visible.
     */
    public boolean isPasswordFieldVisible() {
        return isVisible(passwordInput);
    }

    /**
     * Check if the login button is visible.
     */
    public boolean isLoginButtonVisible() {
        return isVisible(loginButton);
    }
}
