package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Appointment;
import org.example.model.Client;
import org.example.model.Hairdresser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static List<Appointment> getAllAppointments() {
    List<Appointment> appointments = new ArrayList<>();
    String query = "SELECT * FROM appointments WHERE date >= CURDATE() ORDER BY date, time";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Appointment appointment = new Appointment(
            rs.getInt("appointment_id"),
            rs.getString("date"),
            rs.getString("time"),
            rs.getInt("hairdresser_id")
        );
        setAdditionalAppointmentInfo(appointment, conn);
        if (Objects.equals(rs.getString("status"), "booked")) {
          appointment.setStatus("booked");
        }
        appointments.add(appointment);

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return appointments;
  }

  private static void setAdditionalAppointmentInfo(Appointment appointment, Connection conn) {
    setHairdresserInfo(appointment, conn);
    ClientAppointmentDAO.setClientAppointmentInfo(appointment, conn);
  }

  private static void setHairdresserInfo(Appointment appointment, Connection conn) {
    int hairdresserId = appointment.getHairdresserId();
    String query = "SELECT * FROM hairdressers WHERE hairdresser_id = " + hairdresserId;

    try (PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        appointment.setHairdresserInfo(
            rs.getString("first_name")
                + " " + rs.getString("last_name")
                + " (id: " + rs.getString("hairdresser_id") + ")"
                + "\nphone number: " + rs.getString("phone")
                + "\nspecialization: " + rs.getString("specialization"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static String addAppointment(Appointment appointment) {
    String query = "INSERT INTO appointments (date, time, hairdresser_id, status) VALUES (?, ?, ?, ?)";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, appointment.getDate());
      stmt.setString(2, appointment.getTime());
      stmt.setInt(3, appointment.getHairdresserId());
      stmt.setString(4, appointment.getStatus());
      stmt.executeUpdate();

    } catch (SQLException e) {
      return e.getMessage();
    }
    return "";
  }

  public static String deleteAppointment(int appointmentId) {
    String query = "DELETE FROM appointments WHERE appointment_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, appointmentId);
      stmt.executeUpdate();

    } catch (SQLException e) {
      return e.getMessage();
    }
    return "";
  }

  public static String updateAppointment(Appointment appointment) {
    String query = "UPDATE Appointments SET date = ?, time = ?, hairdresser_id = ?, status = ? WHERE appointment_id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, appointment.getDate());
      stmt.setString(2, appointment.getTime());
      stmt.setInt(3, appointment.getHairdresserId());
      stmt.setString(4, appointment.getStatus());
      stmt.setInt(5, appointment.getId());
      stmt.executeUpdate();

    } catch (SQLException e) {
      return e.getMessage();
    }
    return "";
  }
}
