package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Hairdresser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HairdresserDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static List<Hairdresser> getAllHairdressers() {
    List<Hairdresser> hairdressers = new ArrayList<>();
    String query = "SELECT * FROM Hairdressers  ORDER BY last_name, first_name";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        hairdressers.add(new Hairdresser(
            rs.getInt("hairdresser_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("specialization")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return hairdressers;
  }

  public static String addHairdresser(Hairdresser hairdresser) {
    String query = "INSERT INTO Hairdressers (first_name, last_name, phone, specialization) VALUES (?, ?, ?, ?)";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, hairdresser.getFirstName());
      stmt.setString(2, hairdresser.getLastName());
      stmt.setString(3, hairdresser.getPhoneNumber());
      stmt.setString(4, hairdresser.getSpecialization());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String deleteHairdresser(int hairdresserId) {
    String query = "DELETE FROM Hairdressers WHERE hairdresser_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, hairdresserId);
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String updateHairdresser(Hairdresser hairdresser) {
    String query = "UPDATE Hairdressers SET first_name = ?, last_name = ?, phone = ?, specialization = ? WHERE hairdresser_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, hairdresser.getFirstName());
      stmt.setString(2, hairdresser.getLastName());
      stmt.setString(3, hairdresser.getPhoneNumber());
      stmt.setString(4, hairdresser.getSpecialization());
      stmt.setInt(5, hairdresser.getId());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}
