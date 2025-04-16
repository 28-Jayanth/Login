package com.epam.training.login.service;

public class UserLockedException extends RuntimeException {

    public UserLockedException(String message) {
        super(message);
    }
}
