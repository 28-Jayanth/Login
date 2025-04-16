# Login Application

Login Application is a simple command-line java application that demonstrates user login and accessing of protected data.

## The application program flow

1. The application welcomes the user.
2. The application asks the user to enter **login name** and **password** to authenticate.
3. User enters credentials.
4. The Application verifies login name and password:
   3. If user exists with the given login name, and user state is **locked**, then login **fails**.
   1. If user exists with the given login name, and the password matches, then login is **successful**.
   2. If user does not exist with the provided login name, then login **fails**.
   4. If user exists with the given login name, but the password does not match, then application increases failed login counter. If the failed login counter reaches the maximum (3), the application changes user state to **locked**.
5. Application prints login result to the console.
   1. If login was successful, then application writes *"Login successful"*.
   3. If login was unsuccessful due to locked user, then application writes *"User is locked"*. Application stops.
   2. If login was unsuccessful, the application writes *"Invalid login name or password"*, and asks the user to login again (go to step 2).
6. Application writes user details to the console: address.

## Application main classes

| Class                               | Description                                                                                                           |
|-------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| `Application`                       | Manages user interaction and the application flow                                                                     |
| `User`                              | User data                                                                                                             |
| `Address`                           | User's full name and address                                                                                          |
| `UserService`, `DefaultUserService` | Implementation of the Login functionality. Provides access to User data. Has a `UserStore` reference to access users. |
| `UserStore`, `DefaultUserStore`     | Stores user information                                                                                               |
| `LoginResult`                       | Result of the login attempt                                                                                           |
| `UserLockedException`               | Result of the login attempt                                                                                           |

`UserService.login()` method description:

- If login is successful, returns `LoginResult.SUCCESS`.
- If login is unsuccessful due to user locked state, it throws `UserLockedException`.
- If login is unsuccessful (user is unlocked), returns `LoginResult.UNSUCCESSFUL`.

## Unit Testing Exercise

Write unit test for the `DefaultUserService` class.  In order to isolate `UserService` and its `UserStore` dependency, create a mock object and use in the test instead of the real `UserStore`.

The following test cases must be covered:

1. Successful login
2. Unsuccessful login with already locked user
3. Unsuccessful login with existing user, wrong password
4. Unsuccessful login with existing user, wrong password, user gets locked with this login attempt
5. Unsuccessful login with non existing user
6. Query Address of the authenticated user after successful login

### Using the `UserStore` mock object

`DefaultUserService` works only if the `UserStore` under it returns predefined answers for certain method calls.
For example, it must return users:

    when(userStore.getUserByLoginName(...)).thenReturn(...)

Interactions on the mock object must also be verified, for example `updateFailedLoginCounter()` call.

### Running the application

`DefaultUserStore` class defines some hard-coded users that can be used when running the application. For example: 

    login name: adam
    password: secret_adam 
