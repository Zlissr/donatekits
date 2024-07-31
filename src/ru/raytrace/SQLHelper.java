package ru.raytrace;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private Connection connection;

    private final String url;
    private final String user;
    private final String pass;

    public SQLHelper(
            String url,
            String user,
            String pass
    ) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public boolean buyKitQuery(String playerName, int price) {
        String sql = "UPDATE users SET money = money - ? WHERE login=? AND money >= ?";
        try (java.sql.PreparedStatement preparedStatement = getConnection().prepareStatement(
                             sql,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, playerName);
            preparedStatement.setInt(3, price);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows != 0;
        } catch (SQLException e) {
            e.fillInStackTrace();
        }

        return false;
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !connection.isValid(100)) {
            connection = DriverManager.getConnection(url, user, pass);

            return connection;
        }

        return connection;
    }

}
