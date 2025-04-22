package org.example.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceFactory {
  public static HikariDataSource createDataSource(String username, String password) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mariadb://localhost:3306/hairsalon");
    config.setUsername(username);
    config.setPassword(password);

    config.setMaximumPoolSize(10);
    config.setMinimumIdle(2);
    config.setIdleTimeout(30000);
    config.setMaxLifetime(1800000);

    return new HikariDataSource(config);
  }
}
