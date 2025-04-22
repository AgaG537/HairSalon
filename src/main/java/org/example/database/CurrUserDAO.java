package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.User;

import java.sql.*;

public class CurrUserDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static void closeDataSource() {
    dataSource.close();
  }

  public static User authenticate(String username, String password) {
    String query = "SELECT * FROM Users WHERE username = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, username);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String hashedPassword = rs.getString("password");

        if (PasswordUtils.verifyPassword(password, hashedPassword)) {
          return new User(
              rs.getInt("user_id"),
              rs.getString("username"),
              rs.getString("password"),
              rs.getString("role"),
              rs.getString("first_name"),
              rs.getString("last_name")
          );
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean updateAssistantDetails(User user) {
    String procedureCall = "{CALL UpdateAssistantDetails(?, ?, ?, ?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setInt(1, user.getId());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getFirstName());
      stmt.setString(4, user.getLastName());

      stmt.execute();
      return true;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean updateAdminDetails(User user) {
    String procedureCall = "{CALL UpdateAdminDetails(?, ?, ?, ?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setInt(1, user.getId());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getFirstName());
      stmt.setString(4, user.getLastName());

      stmt.execute();
      return true;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean updateAdminPassword(User user, String currentPassword, String newPassword) {
    if (PasswordUtils.verifyPassword(currentPassword, user.getPassword())) {
      String procedureCall = "{CALL UpdateAdminPassword(?, ?, ?)}";

      try (Connection conn = dataSource.getConnection();
           CallableStatement stmt = conn.prepareCall(procedureCall)) {

        stmt.setInt(1, user.getId());
        stmt.setString(2, newPassword);
        stmt.setString(3, PasswordUtils.hashPassword(newPassword));

        stmt.execute();
        return true;

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static boolean updateAssistantPassword(User user, String currentPassword, String newPassword) {
    if (PasswordUtils.verifyPassword(currentPassword, user.getPassword())) {
      String procedureCall = "{CALL UpdateAssistantPassword(?, ?, ?)}";

      try (Connection conn = dataSource.getConnection();
           CallableStatement stmt = conn.prepareCall(procedureCall)) {

        stmt.setInt(1, user.getId());
        stmt.setString(2, newPassword);
        stmt.setString(3, PasswordUtils.hashPassword(newPassword));

        stmt.execute();
        return true;

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return false;
  }


//  public static boolean updateOwnData(User user) {
//    String viewName = "AssistantOwnData_" + user.getUsername();
//
//    String query = "UPDATE " + viewName + " SET first_name = ?, last_name = ?, username = ?";
//
//    try (Connection conn = dataSource.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, user.getFirstName());
//      stmt.setString(2, user.getLastName());
//      stmt.setString(3, user.getUsername());
//      return stmt.executeUpdate() > 0;
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }

//  public static boolean updateOwnPassword(User user, String currentPassword, String newPassword) {
//    if (PasswordUtils.verifyPassword(currentPassword, user.getPassword())) {
//      String viewName = "AssistantOwnData_" + user.getUsername();
//      String query = "UPDATE " + viewName + " SET password = ?";
//
//      boolean flag = false;
//      try (Connection conn = dataSource.getConnection();
//           PreparedStatement stmt = conn.prepareStatement(query)) {
//
//        stmt.setString(1, PasswordUtils.hashPassword(newPassword));
//        flag = stmt.executeUpdate() > 0;
//
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }
//
//      if (!flag) {
//        return false;
//      }
//
//      query = "SET PASSWORD FOR '" + user.getUsername() + "'@'localhost' = PASSWORD( ? )";
//
//      try (Connection conn = dataSource.getConnection();
//           PreparedStatement stmt = conn.prepareStatement(query)) {
//
//        stmt.setString(1, newPassword);
//        stmt.executeUpdate();
//        return true;
//
//      } catch (SQLException e) {
//        e.printStackTrace();
//      }
//    }
//    return false;
//  }

}


//public static boolean checkIfPasswordValid(User user, String password) {
//  String viewName = "AssistantOwnData_" + user.getUsername();
//  String query = "SELECT password FROM " + viewName;
//
//  try (Connection conn = dataSource.getConnection();
//       PreparedStatement stmt = conn.prepareStatement(query);
//       ResultSet rs = stmt.executeQuery()) {
//
//    String hashedPassword = "";
//    while (rs.next()) {
//      hashedPassword = rs.getString("password");
//    }
//
//    if (PasswordUtils.verifyPassword(password, hashedPassword)) {
//      return true;
//    }
//
//  } catch (SQLException e) {
//    e.printStackTrace();
//  }
//  return false;
//}

//  public static boolean registerUser(User user) {
//    String query = "INSERT INTO Users (username, password, role, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
//    try (Connection conn = dataSource.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, user.getUsername());
//      stmt.setString(2, PasswordUtils.hashPassword(user.getPassword()));
//      stmt.setString(3, user.getRole());
//      stmt.setString(4, user.getFirstName());
//      stmt.setString(5, user.getLastName());
//
//      return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }

//  public static boolean updateUserData(User user) {
//    String query = "UPDATE Users SET first_name = ?, last_name = ?, username = ? WHERE user_id = ?";
//    try (Connection conn = dataSource.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, user.getFirstName());
//      stmt.setString(2, user.getLastName());
//      stmt.setString(3, user.getUsername());
//      stmt.setInt(4, user.getId());
//
//      return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }
//
//  public static boolean updatePassword(User user) {
//    String query = "UPDATE Users SET password = ? WHERE user_id = ?";
//    try (Connection conn = dataSource.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, PasswordUtils.hashPassword(user.getPassword())); // Hashowanie hasła
//      stmt.setInt(2, user.getId());
//
//      return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }