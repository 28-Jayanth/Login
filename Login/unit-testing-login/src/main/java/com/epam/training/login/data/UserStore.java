package com.epam.training.login.data;

import com.epam.training.login.domain.User;

public interface UserStore {

    User getUserByLoginName(String loginName);

    int getFailedLoginCounter(String loginName);
    void updateFailedLoginCounter(String loginName, int counter);
}
