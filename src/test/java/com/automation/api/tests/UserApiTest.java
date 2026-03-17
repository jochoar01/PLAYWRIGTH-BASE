package com.automation.api.tests;

import com.automation.api.base.BaseApiTest;
import com.automation.api.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * API Tests for User endpoints using JSONPlaceholder API.
 * Demonstrates GET, POST, PUT, PATCH, DELETE operations.
 */
public class UserApiTest extends BaseApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ==================== GET REQUESTS ====================

    @Test(description = "Get a single user by ID - should return 200 and user data")
    public void testGetSingleUser() throws Exception {
        // Arrange
        int userId = 1;
        String endpoint = "/users/" + userId;
        
        logRequest("GET", endpoint, null);
        
        // Act
        APIResponse response = request.get(endpoint);
        
        // Assert - Status Code
        logResponse(response.status(), response.text());
        assertThat(response.ok()).isTrue();
        assertThat(response.status()).isEqualTo(200);
        
        // Assert - Response Headers
        assertThat(response.headers().get("content-type"))
                .contains("application/json");
        
        // Assert - Response Body
        User user = objectMapper.readValue(response.text(), User.class);
        
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getUsername()).isNotEmpty();
        
        logger.info("User retrieved: {}", user);
    }

    @Test(description = "Get non-existent user - should return 404")
    public void testGetNonExistentUser() {
        // Arrange
        String endpoint = "/users/999";
        
        logRequest("GET", endpoint, null);
        
        // Act
        APIResponse response = request.get(endpoint);
        
        // Assert
        logResponse(response.status(), response.text());
        assertThat(response.status()).isEqualTo(404);
        assertThat(response.text()).isEqualTo("{}");
    }

    @Test(description = "Get list of users - should return 200 and array of users")
    public void testGetUsersList() throws Exception {
        // Arrange
        String endpoint = "/users";
        
        logRequest("GET", endpoint, null);
        
        // Act
        APIResponse response = request.get(endpoint);
        
        // Assert
        logResponse(response.status(), response.text());
        assertThat(response.ok()).isTrue();
        assertThat(response.status()).isEqualTo(200);
        
        // Validate JSON structure - should be array
        User[] users = objectMapper.readValue(response.text(), User[].class);
        assertThat(users).isNotEmpty();
        assertThat(users.length).isEqualTo(10); // JSONPlaceholder has 10 users
        
        logger.info("Retrieved {} users", users.length);
    }

    // ==================== POST REQUESTS ====================

    @Test(description = "Create a new user - should return 201 with user data")
    public void testCreateUser() throws Exception {
        // Arrange
        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", "Morpheus");
        newUser.put("username", "morpheus");
        newUser.put("email", "morpheus@matrix.com");
        
        String requestBody = objectMapper.writeValueAsString(newUser);
        
        logRequest("POST", "/users", requestBody);
        
        // Act
        APIResponse response = request.post("/users",
                RequestOptions.create()
                        .setData(requestBody));
        
        // Assert - Status Code
        logResponse(response.status(), response.text());
        assertThat(response.status()).isEqualTo(201);
        
        // Assert - Response Body contains ID and created user data
        String responseText = response.text();
        assertThat(responseText)
                .contains("id")
                .contains("Morpheus")
                .contains("morpheus@matrix.com");
        
        logger.info("User created successfully");
    }

    // ==================== PUT REQUESTS ====================

    @Test(description = "Update user - should return 200 with updated data")
    public void testUpdateUser() throws Exception {
        // Arrange
        int userId = 1;
        Map<String, String> updateData = new HashMap<>();
        updateData.put("name", "Updated Name");
        updateData.put("username", "updated_username");
        updateData.put("email", "updated@example.com");
        
        String requestBody = objectMapper.writeValueAsString(updateData);
        
        String endpoint = "/users/" + userId;
        logRequest("PUT", endpoint, requestBody);
        
        // Act
        APIResponse response = request.put(endpoint,
                RequestOptions.create()
                        .setData(requestBody));
        
        // Assert
        logResponse(response.status(), response.text());
        assertThat(response.ok()).isTrue();
        assertThat(response.status()).isEqualTo(200);
        
        String responseBody = response.text();
        assertThat(responseBody)
                .contains("Updated Name")
                .contains("updated@example.com");
        
        logger.info("User {} updated successfully", userId);
    }

    // ==================== PATCH REQUESTS ====================

    @Test(description = "Partially update user - should return 200")
    public void testPatchUser() throws Exception {
        // Arrange
        int userId = 1;
        String patchData = "{\"email\": \"newemail@example.com\"}";
        
        String endpoint = "/users/" + userId;
        logRequest("PATCH", endpoint, patchData);
        
        // Act
        APIResponse response = request.patch(endpoint,
                RequestOptions.create()
                        .setData(patchData));
        
        // Assert
        logResponse(response.status(), response.text());
        assertThat(response.ok()).isTrue();
        assertThat(response.status()).isEqualTo(200);
        
        assertThat(response.text())
                .contains("email");
        
        logger.info("User {} patched successfully", userId);
    }

    // ==================== DELETE REQUESTS ====================

    @Test(description = "Delete user - should return 200")
    public void testDeleteUser() {
        // Arrange
        int userId = 1;
        String endpoint = "/users/" + userId;
        
        logRequest("DELETE", endpoint, null);
        
        // Act
        APIResponse response = request.delete(endpoint);
        
        // Assert
        logResponse(response.status(), "Empty response");
        assertThat(response.status()).isEqualTo(200);
        
        logger.info("User {} deleted successfully", userId);
    }

    // ==================== RESPONSE TIME VALIDATION ====================

    @Test(description = "Verify API response time is acceptable")
    public void testResponseTime() {
        // Arrange
        long startTime = System.currentTimeMillis();
        
        // Act
        APIResponse response = request.get("/users/1");
        long endTime = System.currentTimeMillis();
        
        // Assert
        long responseTime = endTime - startTime;
        assertThat(response.ok()).isTrue();
        assertThat(responseTime)
                .as("Response time should be less than 2 seconds")
                .isLessThan(2000);
        
        logger.info("Response time: {} ms", responseTime);
    }
}
