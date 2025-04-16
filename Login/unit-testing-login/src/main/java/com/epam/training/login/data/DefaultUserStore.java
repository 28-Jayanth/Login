package com.epam.training.login.data;

import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.User;

import java.util.*;

public class DefaultUserStore implements UserStore {

    private final Map<String, Integer> failedLoginCounter;
    private final List<User> users;

    public DefaultUserStore() {
        failedLoginCounter = new HashMap<>();
        users = new ArrayList<>();
        initialize();
    }

    @Override
    public User getUserByLoginName(String loginName) {
        return users.stream()
                .filter(user -> user.getLoginName().equals(loginName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getFailedLoginCounter(String loginName) {
        Integer value = failedLoginCounter.get(loginName);
        return value == null ? 0 : value;
    }

    @Override
    public void updateFailedLoginCounter(String loginName, int counter) {
        if (failedLoginCounter.containsKey(loginName)) {
            failedLoginCounter.replace(loginName, counter);
        } else {
            failedLoginCounter.put(loginName, counter);
        }
    }

    private void initialize() {
        Address adamAddress = new Address();
        adamAddress.setCity("Belleville");
        adamAddress.setCountry("United States of America");
        adamAddress.setZipCode("07109");
        adamAddress.setAddressLine("Lincoln Street 2659");
        adamAddress.setName("Adam Davis");

        User adam = new User();
        adam.setLoginName("adam");
        adam.setPassword("secret_adam");
        adam.setAddress(adamAddress);
        adam.setLocked(false);

        users.add(adam);

        Address charlotteAddress = new Address();
        charlotteAddress.setCity("Bigotdan");
        charlotteAddress.setCountry("France");
        charlotteAddress.setZipCode("16456");
        charlotteAddress.setAddressLine("boulevard Guy Boulay 20");
        charlotteAddress.setName("Charlotte Monet");

        User charlotte = new User();
        charlotte.setLoginName("charlotte");
        charlotte.setPassword("secret_charlotte");
        charlotte.setAddress(charlotteAddress);
        charlotte.setAddress(charlotteAddress);
        charlotte.setLocked(false);

        users.add(charlotte);
    }
}
