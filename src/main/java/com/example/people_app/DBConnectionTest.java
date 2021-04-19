package com.example.people_app;

import com.google.common.hash.Hashing;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.xml.crypto.Data;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Objects;

public class DBConnectionTest {
    final static Logger logger = Logger.getLogger(DBConnectionTest.class);

    public static void main(String[] args) {
//        String name = "dev";
//        String pwd = "dev";
//
//        String string = "kevin1990";
//        String hashedString = Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
//        System.out.println(hashedString);
//        System.out.println(Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString());
//
//        try (Connection con = DriverManager.getConnection(
//                "jdbc:postgresql://192.168.1.71:5432/people", name, pwd)){
//            Statement stat = con.createStatement();
//            ResultSet rs = stat.executeQuery("SELECT * FROM users");
//            while (rs.next()) {
//                System.out.println(rs.getString(1) + ". " +
//                        rs.getString(2) + " " + rs.getString(3));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            Connection conn = DataSourceFactory.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String pathToProperties = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
//                .getResource("database.properties")).getPath();
//
//        System.out.println(pathToProperties);
    }
}
