package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static Faker faker = new Faker();

    private DataHelper() {
    }

    public static class UserData {
        private final String login;
        private final String password;

        public UserData(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static UserData getValidUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getInvalidPassword() {
        return faker.internet().password();
    }

    public static String validAuthCode(String login) {
        String authCode = SQLHelper.getValidAuthCode(login);
        return authCode;
    }

}


