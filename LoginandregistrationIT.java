/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.loginandregistration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Student
 */
public class LoginandregistrationIT {

    // -------------------- assertEquals TESTS --------------------

    // Test 1: Username correctly formatted - "kyl_1"
    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured. Password successfully captured. Cell phone number successfully added. User registered successfully!", login.registerUser());
    }

    // Test 2: Username incorrectly formatted - "kyle!!!!!!!"
    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser());
    }

    // Test 3: Password meets complexity requirements - "Ch&&sec@ke99!"
    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured. Password successfully captured. Cell phone number successfully added. User registered successfully!", login.registerUser());
    }

    // Test 4: Password does not meet complexity - "password"
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser());
    }

    // Test 5: Cell phone correctly formatted - "+27838968976"
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured. Password successfully captured. Cell phone number successfully added. User registered successfully!", login.registerUser());
    }

    // Test 6: Cell phone incorrectly formatted - "08966553"
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", login.registerUser());
    }

    // -------------------- assertTrue/assertFalse TESTS --------------------

    // Test 7: Login Successful - returns true
    @Test
    public void testLoginSuccessful() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    // Test 8: Login Failed - returns false
    @Test
    public void testLoginFailed() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("wrongUser", "wrongPass"));
    }

    // Test 9: Username correctly formatted - returns true
    @Test
    public void testUsernameCorrectlyFormatted_assertTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    // Test 10: Username incorrectly formatted - returns false
    @Test
    public void testUsernameIncorrectlyFormatted_assertFalse() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    // Test 11: Password meets complexity - returns true
    @Test
    public void testPasswordMeetsComplexity_assertTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    // Test 12: Password does not meet complexity - returns false
    @Test
    public void testPasswordDoesNotMeetComplexity_assertFalse() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    // Test 13: Cell phone correctly formatted - returns true
    @Test
    public void testCellPhoneCorrectlyFormatted_assertTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    // Test 14: Cell phone incorrectly formatted - returns false
    @Test
    public void testCellPhoneIncorrectlyFormatted_assertFalse() {
        Login login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }
}
