package com.example.people_app.repos;

import com.example.people_app.DataSourceFactory;
import com.example.people_app.models.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public User findUser() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public boolean isExist(String email, String password) {
        return false;
    }
}
