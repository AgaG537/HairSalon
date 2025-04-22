package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static List<Client> getAllClients() {
    List<Client> clients = new ArrayList<>();
    String query = "SELECT * FROM Clients ORDER BY last_name, first_name";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        clients.add(new Client(
            rs.getInt("client_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("email")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return clients;
  }


  public static String addClient(Client client) {
    String query = "INSERT INTO Clients (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, client.getFirstName());
      stmt.setString(2, client.getLastName());
      stmt.setString(3, client.getPhoneNumber());
      stmt.setString(4, client.getEmail());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String deleteClient(int clientId) {
    String query = "DELETE FROM Clients WHERE client_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, clientId);
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String updateClient(Client client) {
    String query = "UPDATE Clients SET first_name = ?, last_name = ?, phone= ?, email = ? WHERE client_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, client.getFirstName());
      stmt.setString(2, client.getLastName());
      stmt.setString(3, client.getPhoneNumber());
      stmt.setString(4, client.getEmail());
      stmt.setInt(5, client.getId());
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}
