package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.UserGenerator.generateLogin;
import static ru.netology.UserGenerator.generatePassword;

public class Test {
    SelenideElement form = $x(".//form");
    SelenideElement error = $x(".//div[@data-test-id='error-notification']");

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    @org.junit.jupiter.api.Test
    public void testUserActive() {
        UserData userActive = UserGenerator.generateUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(userActive.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userActive.getPassword());
        form.$x(".//button").click();
        $x(".//h2").should(text("Личный кабинет"));
    }

    @org.junit.jupiter.api.Test
    void testUserBlocked() {
        UserData userBlocked = UserGenerator.generateUser("blocked");
        UserRegistration.registration(userBlocked);
        form.$x(".//span[@data-test-id='login']//input").val(userBlocked.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userBlocked.getPassword());
        form.$x(".//button").click();
        error.should(visible);
        error.$x(".//div[@class='notification__content']").should(text("Пользователь заблокирован"));
        error.$x(".//button").click();
        error.should(hidden);
    }

    @org.junit.jupiter.api.Test
    public void validUserWithFnInvalidPassword() {
        UserData userActive = UserGenerator.generateUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(userActive.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(generatePassword());
        form.$x(".//button").click();
        $x(".//h2").should(text("Ошибка! Неверно указан логин или пароль"));
    }

    @org.junit.jupiter.api.Test
    public void validUserWithFnInvalidLogin() {
        UserData userActive = UserGenerator.generateUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(generateLogin());;
        form.$x(".//span[@data-test-id='password']//input").val(userActive.getPassword());
        form.$x(".//button").click();
        $x(".//h2").should(text("Ошибка! Неверно указан логин или пароль"));
    }
}
