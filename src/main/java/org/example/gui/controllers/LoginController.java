package org.example.gui.controllers;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.*;
import org.example.gui.loaders.UserInfo.MainRestrictedViewLoader;
import org.example.gui.loaders.UserInfo.MainViewLoader;
import org.example.model.User;

import java.util.Objects;

public class LoginController {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;

  @FXML
  public void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    try {
      HikariDataSource dataSource = HikariDataSourceFactory.createDataSource(username, password);

      ClientDAO.setDataSource(dataSource);
      CurrUserDAO.setDataSource(dataSource);
      HairdresserDAO.setDataSource(dataSource);
      ServiceDAO.setDataSource(dataSource);
      UserDAO.setDataSource(dataSource);
      AppointmentDAO.setDataSource(dataSource);
      ClientAppointmentDAO.setDataSource(dataSource);
      User user = CurrUserDAO.authenticate(username, password);

      if (user != null) {
        if (Objects.equals(user.getRole(), "admin")) {
          MainViewLoader.loadMainView(user, (Stage) usernameField.getScene().getWindow());
        } else {
          MainRestrictedViewLoader.loadMainView(user, (Stage) usernameField.getScene().getWindow());
        }

      } else {
        showAlert("Error", "Invalid user data.");
      }
    } catch (HikariPool.PoolInitializationException e) {
      showAlert("Error", "Invalid user data.");
    } catch (Exception e) {
      showAlert("Error", "Failed to connect to database.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }



}
