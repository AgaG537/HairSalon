package org.example.gui.controllers.UserInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.gui.loaders.LoginViewLoader;
import org.example.gui.loaders.UserInfo.ChangePasswordViewLoader;
import org.example.gui.loaders.UserInfo.EditUserViewLoader;
import org.example.model.User;

public class MainRestrictedController {

  @FXML private Label welcomeLabel;
  @FXML private Label usernameLabel;
  @FXML private Label userFirstNameLabel;
  @FXML private Label userLastNameLabel;
  @FXML private Label userRoleLabel;

  private User loggedInUser;

  public void initialize() {
    welcomeLabel.setText("Welcome to Hair Salon Management System!");
  }

  public void setUser(User user) {
    this.loggedInUser = user;
    updateUserInfo();
  }

  private void updateUserInfo() {
    usernameLabel.setText(String.format("Username: %s", loggedInUser.getUsername()));
    userFirstNameLabel.setText(String.format("First name: %s", loggedInUser.getFirstName()));
    userLastNameLabel.setText(String.format("Last name: %s", loggedInUser.getLastName()));
    userRoleLabel.setText(String.format("Role: %s", loggedInUser.getRole()));
  }

  @FXML
  public void handleEditUser() {
    EditUserViewLoader.loadEditUserView(loggedInUser, () -> {
      updateUserInfo();
      showAlert("Success", "Your data has been updated. If you changed "
          + "username, log out and log in for proper application functioning.");
    });
  }

  @FXML
  public void handleChangePassword() {
    ChangePasswordViewLoader.loadChangePasswordView(loggedInUser, () -> {
      showAlert("Success", "Your password has been changed.");
    });
  }

  @FXML
  public void handleLogout() {
    LoginViewLoader.loadLoginView((Stage) welcomeLabel.getScene().getWindow());
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
