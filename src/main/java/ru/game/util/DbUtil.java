package ru.game.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final BasicDataSource dataSource = new BasicDataSource();

    public static void init(File propertiesFile) throws IOException, ClassNotFoundException {
        Properties prop = new Properties();
        Reader reader = new FileReader(propertiesFile);
        System.out.println("reader = " + reader );
        prop.load(reader);
        String driver = prop.getProperty("driver");
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        Class.forName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        reader.close();
    }

    public static void close() throws SQLException {
        dataSource.close();
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
