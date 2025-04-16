package com.epam.training.login.service;

import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;

public interface UserService {

    LoginResult login(String loginName, String password);
    Address getLoggedInUserAddress();

}
