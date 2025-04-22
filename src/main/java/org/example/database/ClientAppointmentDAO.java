package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Appointment;
import org.example.model.Client;
import org.example.model.Hairdresser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientAppointmentDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static void setClientAppointmentInfo(Appointment appointment, Connection conn) {
    int appointmentId = appointment.getId();
    String query = "SELECT * FROM clientappointments WHERE appointment_id = " + appointmentId;

    try (PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int clientId = rs.getInt("client_id");
        int serviceId = rs.getInt("service_id");
        setClientInfo(appointment, conn, clientId);
        setServiceInfo(appointment, conn, serviceId);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void setClientInfo(Appointment appointment, Connection conn, int clientId) {
    if (clientId != -1) {
      String query = "SELECT * FROM Clients WHERE client_id = " + clientId;

      try (PreparedStatement stmt = conn.prepareStatement(query);
           ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
          String clientInfo;
          if (!Objects.equals(rs.getString("email"), "")) {
            clientInfo = rs.getString("first_name")
                + " " + rs.getString("last_name")
                + " (id: " + rs.getString("client_id") + ")"
                + "\nphone number: " + rs.getString("phone")
                + "\nemail: " + rs.getString("email");
          } else {
            clientInfo = rs.getString("first_name")
                + " " + rs.getString("last_name")
                + " (id: " + rs.getString("client_id") + ")"
                + "\nphone number: " + rs.getString("phone");
          }
          appointment.setClientInfo(clientInfo);
          appointment.setClientId(rs.getInt("client_id"));
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private static void setServiceInfo(Appointment appointment, Connection conn, int serviceId) {
    if (serviceId != -1) {
      String query = "SELECT * FROM services WHERE service_id = " + serviceId;

      try (PreparedStatement stmt = conn.prepareStatement(query);
           ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
          appointment.setServiceInfo(
              rs.getString("service_type")
                  + " (id: " + rs.getString("service_id") + ")"
                  + "\nprice: " + rs.getString("price"));
          appointment.setServiceId(rs.getInt("service_id"));
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static String addClientAppointment(int appointmentId, int clientId, int serviceId) {
    String procedureCall = "{CALL AddClientappointmentTransactionally(?, ?, ?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setInt(1, appointmentId);
      stmt.setInt(2, clientId);
      stmt.setInt(3, serviceId);
      stmt.execute();

    } catch (SQLException e) {
      return e.getMessage();
    }
    return "";
  }

  public static String deleteClientAppointment(int appointmentId) {
    String query = "DELETE FROM clientappointments WHERE appointment_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, appointmentId);
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String updateClientAppointment(int appointmentId, int clientId, int serviceId) {
    String query = "UPDATE clientappointments SET client_id = ?, service_id = ? WHERE appointment_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, clientId);
      stmt.setInt(2, serviceId);
      stmt.setInt(3, appointmentId);
      stmt.executeUpdate();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}
