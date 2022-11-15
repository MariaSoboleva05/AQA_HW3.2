package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    DataHelper.UserData user;
    LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        user = DataHelper.getValidUser();
        loginPage = new LoginPage();
    }

    @AfterEach
    public void afterEach() {
        SQLHelper.reloadUserStatus(user.getLogin());
        SQLHelper.reloadAuthCode();
    }

    @AfterAll
    public static void afterAll() {
        SQLHelper.setDown();
    }

    @Test
    public void shouldAuthSuccessfully() {
        loginPage.insert(user.getLogin(), user.getPassword());
        VerificationPage verification = new VerificationPage();
        verification.insertCode(DataHelper.validAuthCode(user.getLogin()));
        DashboardPage dashboard = new DashboardPage();
        dashboard.visibleDashboardPage();
    }

    @Test
    public void shouldNoAuthWithInvalidPassword() {
        var password = DataHelper.getInvalidPassword();
        loginPage.insert(user.getLogin(), password);
        loginPage.invalidPassword(user.getLogin(), password);
    }

    @Test
    public void shouldBlockIfWrongPasswordEnteredThreeTimes() {
        loginPage.invalidPassword(user.getLogin(), DataHelper.getInvalidPassword());
        loginPage.invalidPassword(user.getLogin(), DataHelper.getInvalidPassword());
        loginPage.blocked(user.getLogin(), DataHelper.getInvalidPassword());
    }
}
