package com.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    private static BasicDataSource dataSource;

    static {

        dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/myapp");
        dataSource.setUsername("root");
        dataSource.setPassword("11");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // pool settings
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);
        dataSource.setMinIdle(2);
        dataSource.setMaxIdle(5);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
