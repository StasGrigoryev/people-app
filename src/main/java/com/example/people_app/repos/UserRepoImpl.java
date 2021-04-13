package com.example.people_app.repos;

import com.example.people_app.DataSourceFactory;
import com.example.people_app.models.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {
    private static final Logger LOGGER = Logger.getLogger(UserRepoImpl.class.getName());

    public UserRepoImpl() {
    }

    public static class SingletonHelper {
        private static final UserRepoImpl INSTANCE = new UserRepoImpl();
    }

    public static UserRepoImpl getRepo() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public boolean save(User user) {
        Connection connection = null;
        try {
            connection = DataSourceFactory.getConnection();
            String SQL = "INSERT INTO users(firstname, lastname, email, password_hash, phone_number) " +
                    "values(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getFirstName());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Rollback failed", ex);
            }
            LOGGER.log(Level.FATAL, "SQL Exception", e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exc) {
                LOGGER.log(Level.FATAL, "Failed to close connection", exc);
            }
        }
        return false;
    }

    @Override
    public User findUser(int id) {
        Connection connection = null;
        User user = null;

        try {
            connection = DataSourceFactory.getConnection();
            String sql = "select * from users where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPasswordHash(rs.getString("password_hash"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "SQL Exception", e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exc) {
                LOGGER.log(Level.FATAL, "Failed to close connection", exc);
            }
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DataSourceFactory.getConnection();
            String sql = "select * from users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPasswordHash(rs.getString("password_hash"));

                users.add(user);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "SQL Exception", e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exc) {
                LOGGER.log(Level.FATAL, "Failed to close connection", exc);
            }
        }
        return users;
    }

    @Override
    public boolean isExist(String email, String passwordHash) {
        List<User> users = findAll();

        return null != users.stream().filter(user -> user.getEmail().equals(email)
        && user.getPasswordHash().equals(passwordHash)).findAny().orElse(null);
    }
}
