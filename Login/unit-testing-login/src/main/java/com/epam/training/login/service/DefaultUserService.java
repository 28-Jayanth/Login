package com.epam.training.login.service;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.domain.User;

public class DefaultUserService implements UserService {

    private static final int MAXIMUM_NUMBER_OF_LOGIN_ATTEMPTS = 3;

    private final UserStore userStore;
    private User loggedInUser;

    public DefaultUserService(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public LoginResult login(String loginName, String password) {
        User user = userStore.getUserByLoginName(loginName);
        LoginResult loginResult = LoginResult.UNSUCCESSFUL;
        if (user != null) {
            checkIfUserIsLocked(user);
            loginResult = checkCredentials(loginName, password, user);
        }
        return loginResult;
    }

    private LoginResult checkCredentials(String loginName, String password, User user) {
        LoginResult loginResult = LoginResult.UNSUCCESSFUL;
        if (!user.getPassword().equals(password)) {
            handleFailedLoginAttempt(user);
        } else {
            userStore.updateFailedLoginCounter(loginName, 0);
            loggedInUser = user;
            loginResult = LoginResult.SUCCESS;
        }
        return loginResult;
    }

    private void checkIfUserIsLocked(User user) {
        if (user.isLocked()) {
            throw new UserLockedException("User is locked");
        }
    }

    private void handleFailedLoginAttempt(User user) {
        int counter = userStore.getFailedLoginCounter(user.getLoginName()) + 1;
        userStore.updateFailedLoginCounter(user.getLoginName(), counter);
        if (counter >= MAXIMUM_NUMBER_OF_LOGIN_ATTEMPTS) {
            user.setLocked(true);
            throw new UserLockedException("User is locked");
        }
    }

    @Override
    public Address getLoggedInUserAddress() {
        return loggedInUser.getAddress();
    }
}
