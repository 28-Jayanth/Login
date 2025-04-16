package com.epam.training.login.domain;

import java.util.Objects;

public class User {

    private String loginName;
    private String password;

    private boolean locked;
    private Address address;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return locked == user.locked && Objects.equals(loginName, user.loginName) && Objects.equals(password, user.password) && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginName, password, locked, address);
    }
}
