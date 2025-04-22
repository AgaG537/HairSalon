package org.example.gui.controllers.UserInfo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.CurrUserDAO;
import org.example.model.User;

import java.util.Objects;

public class EditUserController {

  @FXML private TextField usernameField;
  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;

  private User user;
  private Runnable onSuccessCallback;

  public void setUser(User user) {
    this.user = user;
    usernameField.setText(user.getUsername());
    firstNameField.setText(user.getFirstName());
    lastNameField.setText(user.getLastName());
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
    String username = usernameField.getText();
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();

    if (username.isEmpty() ||firstName.isEmpty() || lastName.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    user.setUsername(username);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    if (Objects.equals(user.getRole(), "admin")) {
      if (CurrUserDAO.updateAdminDetails(user)) {
        onSuccessCallback.run();
        ((Stage) firstNameField.getScene().getWindow()).close();
      } else {
        showAlert("Error", "User data could not be updated.");
      }
    } else {
      if (CurrUserDAO.updateAssistantDetails(user)) {
        onSuccessCallback.run();
        ((Stage) firstNameField.getScene().getWindow()).close();
      } else {
        showAlert("Error", "User data could not be updated.");
      }
    }
  }

  @FXML
  public void handleCancel() {
    ((Stage) firstNameField.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
