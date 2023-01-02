# Banking Repository

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Banking [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need basic understanding of:

- JDK 17
- [Maven 3](https://maven.apache.org)
- Spring Boot 3.0

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com.poc.banking.BankingRepositoryApplication` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Accessing the application using Swagger

Application will be available at http://localhost:8080/banking-repository/swagger-ui/index.html

## Default Admin User

Using below credentials user will able to login to the system and add/remove account/user.

```
Username: admin
Password: admin
```

### Types of Roles:

- Admin
    - Allow to add user/account to the system
    - Allow to fetch user/account details
    - Allow user to add in Bank Account
    - Allow User to remove from Bank Account
- Account
    - Allow customer accounts transfer fund from one to another account
    - Allow to fetch customer account details/transactions/details
- User
    - Allow logged in user to see it's own account details

## Copyright

Released under the Apache License 2.0. See
the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
