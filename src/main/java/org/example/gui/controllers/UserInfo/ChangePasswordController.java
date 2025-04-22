package org.example.gui.controllers.UserInfo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.CurrUserDAO;
import org.example.model.User;

import java.util.Objects;

public class ChangePasswordController {

  @FXML private PasswordField currentPasswordField;
  @FXML private PasswordField newPasswordField;
  @FXML private PasswordField confirmPasswordField;

  private User user;
  private Runnable onSuccessCallback;

  public void setUser(User user) {
    this.user = user;
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleChangePassword() {
    String currentPassword = currentPasswordField.getText();
    String newPassword = newPasswordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    if (!newPassword.equals(confirmPassword)) {
      showAlert("Error", "Passwords do not match.");
      return;
    }

    if (Objects.equals(user.getRole(), "admin")) {
      if (CurrUserDAO.updateAdminPassword(user, currentPassword, newPassword)) {
        user.setPassword(newPassword);
        onSuccessCallback.run();
        ((Stage) newPasswordField.getScene().getWindow()).close();
      } else {
        showAlert("Error", "Password could not be updated.");
      }
    } else {
      if (CurrUserDAO.updateAssistantPassword(user, currentPassword, newPassword)) {
        user.setPassword(newPassword);
        onSuccessCallback.run();
        ((Stage) newPasswordField.getScene().getWindow()).close();
      } else {
        showAlert("Error", "Password could not be updated.");
      }
    }

  }

  @FXML
  public void handleCancel() {
    ((Stage) newPasswordField.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
