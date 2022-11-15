package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void reloadUserStatus(String login) {
        setUp();
        var sqlQuery = "UPDATE users SET status = 'active' WHERE login = ?;";
        runner.update(conn, sqlQuery, login);
    }

    @SneakyThrows
    public static String getValidAuthCode(String login) {
        setUp();
        var sqlQuery = "SELECT code FROM auth_codes " +
                "JOIN users ON user_id = users.id " +
                "WHERE login = ? " +
                "ORDER BY created DESC LIMIT 1; ";
        return runner.query(conn, sqlQuery, new ScalarHandler<>(), login);
    }

    @SneakyThrows
    public static void reloadAuthCode() {
        setUp();
        var sqlQuery = "DELETE FROM auth_codes;";
        runner.update(conn, sqlQuery);
    }

    @SneakyThrows
    public static void setDown() {
        setUp();
        var sqlQuery1 = "DELETE FROM auth_codes;";
        var sqlQuery2 = "DELETE FROM cards;";
        var sqlQuery3 = "DELETE FROM users;";

        runner.update(conn, sqlQuery1);
        runner.update(conn, sqlQuery2);
        runner.update(conn, sqlQuery3);
    }
}
