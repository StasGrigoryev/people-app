package com.example.people_app;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.INACTIVE;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DataSourceFactory {
    private static DataSource ds;
    private static Logger LOGGER = Logger.getLogger(DataSourceFactory.class.getName());

    public DataSourceFactory() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        String pathToProperties = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
        .getResource("database.properties")).getPath();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(inputStream);

            //("jdbc:postgresql://localhost:5432/people");
            final String SERVER_NAME = properties.getProperty("serverName");
            final String DB_NAME = properties.getProperty("databaseName");
            final String PORT = properties.getProperty("port");
            dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://" + SERVER_NAME + ":" + PORT + "/" + DB_NAME);
            dataSource.setUser(properties.getProperty("user"));
            dataSource.setPassword(properties.getProperty("password"));

            dataSource.setInitialPoolSize(5);
            dataSource.setMinPoolSize(5);
            dataSource.setAcquireIncrement(5);
            dataSource.setMaxPoolSize(20);
            dataSource.setMaxStatements(100);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FATAL, "File database.properties not found", e);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "IO Error", e);
        } catch (PropertyVetoException e) {
            LOGGER.log(Level.FATAL, "Property Veto Exception", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.log(Level.FATAL, "Failed to close input stream");
                }
            }
        }
        ds = dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return SingletonHelper.INSTANCE.ds.getConnection();
    }

    public static class SingletonHelper {
        public static final DataSourceFactory INSTANCE = new DataSourceFactory();
    }
}
