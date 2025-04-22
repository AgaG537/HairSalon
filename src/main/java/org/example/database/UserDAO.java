package org.example.database;

import com.zaxxer.hikari.HikariDataSource;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data Access Object
public class UserDAO {
  private static HikariDataSource dataSource;

  public static void setDataSource(HikariDataSource hikariDataSource) {
    dataSource = hikariDataSource;
  }

  public static List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String query = "SELECT * FROM Users ORDER BY last_name, first_name";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        users.add(new User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("role"),
            rs.getString("first_name"),
            rs.getString("last_name")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  public static String addUser(User user) {
    String procedureCall = "{CALL CreateUser(?, ?, ?, ?, ?, ?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, PasswordUtils.hashPassword(user.getPassword()));
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getRole());

      stmt.execute();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String deleteUser(int userId) {
    String procedureCall = "{CALL DeleteUser(?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setInt(1, userId);
      stmt.execute();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }

  public static String updateUserDetails(User user) {
    String procedureCall = "{CALL UpdateAdminDetails(?, ?, ?, ?)}";

    try (Connection conn = dataSource.getConnection();
         CallableStatement stmt = conn.prepareCall(procedureCall)) {

      stmt.setInt(1, user.getId());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getFirstName());
      stmt.setString(4, user.getLastName());

      stmt.execute();
      return "";

    } catch (SQLException e) {
      return e.getMessage();
    }
  }
}

//  public static void deleteUser(int userId) {
//    String query = "DELETE FROM Users WHERE user_id = ?";
//    String username = null;
//
//    try (Connection conn = dataSource.getConnection(); // root admin
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      // Pobieranie nazwy użytkownika przed usunięciem
//      String getUsernameQuery = "SELECT username FROM Users WHERE user_id = ?";
//      try (PreparedStatement getUsernameStmt = conn.prepareStatement(getUsernameQuery)) {
//        getUsernameStmt.setInt(1, userId);
//        ResultSet rs = getUsernameStmt.executeQuery();
//        if (rs.next()) {
//          username = rs.getString("username");
//        }
//      }
//
//      // Usuwanie z tabeli Users
//      stmt.setInt(1, userId);
//      stmt.executeUpdate();
//
//      // Usuwanie użytkownika z MariaDB
//      if (username != null) {
//        String dropUserQuery = "DROP USER ?@'localhost'";
//        try (PreparedStatement dropStmt = conn.prepareStatement(dropUserQuery)) {
//          dropStmt.setString(1, username);
//          dropStmt.executeUpdate();
//        }
//      }
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }







//  // Dodawanie użytkownika
//  public void addUser(User user) {
//    String query = "INSERT INTO Users (username, password, role, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
//
//    try (Connection conn = Database.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, user.getUsername());
//      stmt.setString(2, PasswordUtils.hashPassword(user.getPassword()));
//      stmt.setString(3, user.getRole());
//      stmt.setString(4, user.getFirstName());
//      stmt.setString(5, user.getLastName());
//      stmt.executeUpdate();
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }

//  // Usuwanie użytkownika
//  public void deleteUser(int userId) {
//    String query = "DELETE FROM Users WHERE user_id = ?";
//
//    try (Connection conn = Database.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setInt(1, userId);
//      stmt.executeUpdate();
//
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }



//public boolean updateUserPassword(User user) {
//  String query = "UPDATE Users SET password = ? WHERE user_id = ?";
//  try (Connection conn = Database.getConnection();
//       PreparedStatement stmt = conn.prepareStatement(query)) {
//
//    stmt.setString(1, user.getPassword().isEmpty() ? user.getPassword() : PasswordUtils.hashPassword(user.getPassword()));
//    stmt.setInt(2, user.getUserId());
//
//    return stmt.executeUpdate() > 0;
//  } catch (SQLException e) {
//    e.printStackTrace();
//  }
//  return false;
//}

//public class UserDAO {
//
//  public User authenticate(String username, String password) {
//    String query = "SELECT * FROM Users WHERE username = ?";
//    try (Connection conn = Database.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, username);
//
//      ResultSet rs = stmt.executeQuery();
//      if (rs.next()) {
//        String hashedPassword = rs.getString("password");
//
//        if (PasswordUtils.verifyPassword(password, hashedPassword)) {
//          return new User(
//              rs.getInt("user_id"),
//              rs.getString("username"),
//              rs.getString("password"),
//              rs.getString("role"),
//              rs.getString("first_name"),
//              rs.getString("last_name")
//          );
//        }
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return null;
//  }
//
//  public List<User> getAllUsers() {
//    List<User> users = new ArrayList<>();
//    String query = "SELECT user_id, username, password, role, first_name, last_name FROM Users";
//    try (Connection conn = Database.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query);
//         ResultSet rs = stmt.executeQuery()) {
//
//      while (rs.next()) {
//        User user = new User(
//            rs.getInt("user_id"),
//            rs.getString("username"),
//            rs.getString("password"),
//            rs.getString("role"),
//            rs.getString("first_name"),
//            rs.getString("last_name")
//        );
//        users.add(user);
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return users;
//  }
//
//  public void addUser(User user) {
//    String query = "INSERT INTO Users (username, password, role, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
//    try (Connection conn = Database.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//
//      stmt.setString(1, user.getUsername());
//      stmt.setString(2, user.getPassword());
//      stmt.setString(3, PasswordUtils.hashPassword(user.getRole()));
//      stmt.setString(4, user.getFirstName());
//      stmt.setString(5, user.getLastName());
//      stmt.executeUpdate();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
//
//}
//
