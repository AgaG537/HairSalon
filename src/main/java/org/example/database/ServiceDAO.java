package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static List<Service> getAllServices() {
    List<Service> services = new ArrayList<>();
    String query = "SELECT * FROM services ORDER BY service_type";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        services.add(new Service(
            rs.getInt("service_id"),
            rs.getString("service_type"),
            rs.getDouble("price")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return services;
  }

  public static String addService(Service service) {
    String query = "INSERT INTO services (service_id, service_type, price) VALUES (?, ?, ?)";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, service.getServiceId());
      stmt.setString(2, service.getType());
      stmt.setDouble(3, service.getPrice());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  // Usuwanie klienta
  public static String deleteService(int serviceId) {
    String query = "DELETE FROM services WHERE service_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, serviceId);
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String updateService(Service service) {
    String query = "UPDATE services SET service_type = ?, price= ? WHERE service_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, service.getType());
      stmt.setDouble(2, service.getPrice());
      stmt.setInt(3, service.getServiceId());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}
