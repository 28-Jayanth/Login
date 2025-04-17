package com.epam.training.login.service;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.domain.User;
import com.epam.training.login.service.DefaultUserService;
import com.epam.training.login.service.UserLockedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserServiceTest {

    private TestUserStore userStore;
    private DefaultUserService userService;

    private final String loginName = "testuser";
    private final String correctPassword = "password123";
    private final String wrongPassword = "wrong";

    @BeforeEach
    public void setup() {
        userStore = new TestUserStore();
        userService = new DefaultUserService(userStore);
    }

    @Test
    public void testSuccessfulLogin() {
        User user = createUser(false);
        userStore.addUser(user);

        LoginResult result = userService.login(loginName, correctPassword);

        assertEquals(LoginResult.SUCCESS, result);
        assertEquals(0, userStore.getFailedLoginCounter(loginName));
    }

    @Test
    public void testLoginWithLockedUser() {
        User user = createUser(true);
        userStore.addUser(user);

        assertThrows(UserLockedException.class, () -> {
            userService.login(loginName, correctPassword);
        });
    }

    @Test
    public void testLoginWithWrongPassword() {
        User user = createUser(false);
        userStore.addUser(user);
        userStore.updateFailedLoginCounter(loginName, 1);

        LoginResult result = userService.login(loginName, wrongPassword);

        assertEquals(LoginResult.UNSUCCESSFUL, result);
        assertEquals(2, userStore.getFailedLoginCounter(loginName));
    }

    @Test
    public void testLoginWithWrongPasswordUserGetsLocked() {
        User user = createUser(false);
        userStore.addUser(user);
        userStore.updateFailedLoginCounter(loginName, 2); // Already failed twice

        assertThrows(UserLockedException.class, () -> {
            userService.login(loginName, wrongPassword);
        });

        assertEquals(3, userStore.getFailedLoginCounter(loginName));
        assertTrue(user.isLocked());
    }

    @Test
    public void testLoginWithNonExistingUser() {
        LoginResult result = userService.login(loginName, correctPassword);
        assertEquals(LoginResult.UNSUCCESSFUL, result);
    }

    @Test
    public void testGetLoggedInUserAddress() {
        User user = createUser(false);
        userStore.addUser(user);

        userService.login(loginName, correctPassword);
        Address result = userService.getLoggedInUserAddress();

        assertEquals(user.getAddress(), result);
    }

    // Helper method to create a test user
    private User createUser(boolean locked) {
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(correctPassword);
        user.setLocked(locked);

        Address address = new Address();
        address.setCity("TestCity");
        address.setCountry("TestLand");
        address.setZipCode("12345");
        address.setAddressLine("123 Test St");
        address.setName("Test User");

        user.setAddress(address);
        return user;
    }

    // Simple in-memory implementation of UserStore for testing
    private static class TestUserStore implements UserStore {
        private final java.util.Map<String, User> userMap = new java.util.HashMap<>();
        private final java.util.Map<String, Integer> loginAttempts = new java.util.HashMap<>();

        public void addUser(User user) {
            userMap.put(user.getLoginName(), user);
        }

        @Override
        public User getUserByLoginName(String loginName) {
            return userMap.get(loginName);
        }

        @Override
        public int getFailedLoginCounter(String loginName) {
            return loginAttempts.getOrDefault(loginName, 0);
        }

        @Override
        public void updateFailedLoginCounter(String loginName, int counter) {
            loginAttempts.put(loginName, counter);
        }
    }
}
