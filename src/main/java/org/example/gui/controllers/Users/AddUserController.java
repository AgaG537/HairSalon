package org.example.gui.controllers.Users;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.UserDAO;
import org.example.model.User;

import java.util.Objects;

public class AddUserController {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private ChoiceBox<String> roleChoiceBox;

  private Runnable onSuccessCallback;

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void initialize() {
    roleChoiceBox.getItems().addAll("admin", "assistant");
  }

  @FXML
  public void handleAdd() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String role = roleChoiceBox.getValue();

    if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || role == null) {
      showAlert("Error", "All fields are required.");
      return;
    }

    User user = new User(0, username, password, role, firstName, lastName);
    String flag = UserDAO.addUser(user);

    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    onSuccessCallback.run();
    ((Stage) usernameField.getScene().getWindow()).close();
  }

  @FXML
  public void handleCancel() {
    ((Stage) usernameField.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
