package com.epam.training.login;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.data.DefaultUserStore;
import com.epam.training.login.domain.Credentials;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.service.DefaultUserService;
import com.epam.training.login.service.UserLockedException;
import com.epam.training.login.service.UserService;

import java.util.Scanner;

public class Application {
    private final UserService userService;
    private final Scanner scanner;

    public Application(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        UserStore dataStore = new DefaultUserStore();
        UserService userService = new DefaultUserService(dataStore);
        new Application(userService).run();
    }

    private void run() {
        printWelcomeMessage();
        try {
            attemptLogin();
            printUserAddress();
        } catch (UserLockedException e) {
            System.out.println(e.getMessage());
            printEmptyLine();
            System.out.println("Exiting...");
        }
    }

    private void attemptLogin() throws UserLockedException {
        LoginResult loginResult;
        do {
            Credentials credentials = askForCredentials();
            loginResult = userService.login(credentials.getUsername(), credentials.getPassword());
            if (loginResult.equals(LoginResult.UNSUCCESSFUL)) {
                System.out.println("Invalid login name or password");
                printEmptyLine();
            }
        } while (!loginResult.equals(LoginResult.SUCCESS));
        System.out.println("Login successful");
        printEmptyLine();
    }

    private Credentials askForCredentials() {
        Credentials credentials = new Credentials();
        System.out.println("Please enter your credentials.");
        System.out.print("Login name: ");
        credentials.setUsername(scanner.nextLine());
        System.out.print("Password: ");
        credentials.setPassword(scanner.nextLine());
        return credentials;
    }

    private void printUserAddress() {
        System.out.println("Address:");
        System.out.println(userService.getLoggedInUserAddress().toString());
    }

    private void printWelcomeMessage() {
        System.out.println("Welcome!");
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

}
