package org.example.gui.controllers.Clients;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.ClientDAO;
import org.example.model.Client;

import java.util.Objects;

public class EditClientController {

  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField phoneNumberField;
  @FXML private TextField emailField;

  private Client client;
  private Runnable onSuccessCallback;

  public void setClient(Client client) {
    this.client = client;
    firstNameField.setText(client.getFirstName());
    lastNameField.setText(client.getLastName());
    phoneNumberField.setText(client.getPhoneNumber());
    emailField.setText(client.getEmail());
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String phone = phoneNumberField.getText();
    String email = emailField.getText();

    if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    if (!email.isEmpty() && !email.contains("@")) {
      showAlert("Error", "Invalid email address.");
      return;
    }

    if (!checkPhone(phone) || phone.length() != 9) {
      showAlert("Error", "Phone number should consists of nine digits.");
      return;
    }

    client.setFirstName(firstName);
    client.setLastName(lastName);
    client.setPhoneNumber(phone);
    client.setEmail(email);

    String flag = ClientDAO.updateClient(client);

    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    onSuccessCallback.run();
    ((Stage) firstNameField.getScene().getWindow()).close();
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

  private boolean checkPhone(String phone) {
    try {
      if (!phone.isEmpty()) {
        for (int i = 0; i < phone.length(); i++) {
          Integer digit = Integer.parseInt(phone.substring(i, i + 1));
        }
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
