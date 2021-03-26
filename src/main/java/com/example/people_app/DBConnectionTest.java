package com.example.people_app;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DBConnectionTest {
    public static void main(String[] args) {
        String name = "dev";
        String pwd = "dev";

        String string = "kevin1990";
        String hashedString = Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
        System.out.println(hashedString);
        System.out.println(Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString());

        try (Connection con = DriverManager.getConnection(
                "jdbc:postgresql://192.168.1.71:5432/people", name, pwd)){
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println(rs.getString(1) + ". " +
                        rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
