/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatajpa;

import com.example.accessingdatajpa.model.User;
import com.example.accessingdatajpa.repository.UserRepository;
import com.example.accessingdatajpa.services.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author camil
 */
/**
 * UserServiceUnitTests is a test class for the UserService class.
 * It includes tests for user management functionalities like creation and validation.
 */
@SpringBootTest
@Transactional
public class UserTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private String demoUsername;
    private String demoPassword;

    /**
     * Initialize test data before each individual test case.
     */
    @BeforeEach
    public void initTestData() {
        demoUsername = "demoUser";
        demoPassword = "demoPassword";
    }

    /**
     * Clean up the repository after each test case to maintain isolation.
     */
    @AfterEach
    public void cleanupDatabase() {
        userRepository.deleteAll();
    }

    /**
     * Test case for successful user creation.
     * Ensures that the user is properly created and stored in the repository.
     * 
     * @throws Exception if an error occurs during user creation
     */
    @Test
    public void shouldCreateUserSuccessfully() throws Exception {
        boolean isUserCreated = userService.createUser(demoUsername, demoPassword);
        assertTrue(isUserCreated, "User should be created without issues");

        User createdUser = userRepository.findByUsername(demoUsername);
        assertNotNull(createdUser, "The user should be retrievable from the repository");
        assertEquals(demoUsername, createdUser.getUsername(), "Username should match the input");
    }

    /**
     * Test case for user creation with a duplicate username.
     * Ensures that trying to create a user with an existing username fails.
     * 
     * @throws Exception if an error occurs during user creation
     */
    @Test
    public void shouldNotCreateUserWithDuplicateUsername() throws Exception {
        userService.createUser(demoUsername, demoPassword);
        boolean isDuplicateUserCreated = userService.createUser(demoUsername, "anotherPassword");
        
        assertFalse(isDuplicateUserCreated, "User creation should fail for duplicate username");
    }

    /**
     * Test case for successful user validation with correct credentials.
     * Ensures that a user can be authenticated when the correct password is provided.
     * 
     * @throws Exception if an error occurs during user validation
     */
    @Test
    public void shouldValidateUserSuccessfully() throws Exception {
        userService.createUser(demoUsername, demoPassword);
        boolean isUserValid = userService.validateUser(demoUsername, demoPassword);
        
        assertTrue(isUserValid, "User should be validated with correct credentials");
    }

    /**
     * Test case for failed user validation due to incorrect password.
     * Ensures that a user cannot be authenticated with an invalid password.
     * 
     * @throws Exception if an error occurs during user validation
     */
    @Test
    public void shouldFailValidationWithIncorrectPassword() throws Exception {
        userService.createUser(demoUsername, demoPassword);
        boolean isUserValid = userService.validateUser(demoUsername, "invalidPassword");
        
        assertFalse(isUserValid, "User validation should fail with incorrect password");
    }

    /**
     * Test case for validating a non-existent user.
     * Ensures that the validation process fails for users that do not exist.
     */
    @Test
    public void shouldNotValidateNonExistentUser() {
        boolean isUserValid = userService.validateUser("unknownUser", "password");
        
        assertFalse(isUserValid, "Non-existent users should not be validated");
    }
    
    /**
     * Test the creation of a user with a short username.
     * It verifies that creating a user with a short username still succeeds.
     * @throws Exception if an error occurs during user creation
     */
    @Test
    public void shouldCreateUserWithShortUsername() throws Exception {
        String shortUsername = "usr";
        boolean isUserCreated = userService.createUser(shortUsername, demoPassword);
        
        assertTrue(isUserCreated, "User creation should succeed with a short username");

        User user = userRepository.findByUsername(shortUsername);
        assertNotNull(user, "User should be found in the repository");
        assertEquals(shortUsername, user.getUsername(), "The username should match the input");
    }

    /**
     * Test the creation of a user with a long password.
     * It verifies that creating a user with a long password succeeds.
     * @throws Exception if an error occurs during user creation
     */
    @Test
    public void shouldCreateUserWithLongPassword() throws Exception {
        String longPassword = "thisisaverylongpassword12345";
        boolean isUserCreated = userService.createUser(demoUsername, longPassword);
        
        assertTrue(isUserCreated, "User creation should succeed with a long password");

        User user = userRepository.findByUsername(demoUsername);
        assertNotNull(user, "User should be found in the repository");
        assertEquals(demoUsername, user.getUsername(), "The username should match the input");
    }

    
}
