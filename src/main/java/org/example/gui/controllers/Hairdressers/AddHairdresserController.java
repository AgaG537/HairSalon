package org.example.gui.controllers.Hairdressers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.database.HairdresserDAO;
import org.example.model.Hairdresser;

import java.util.Objects;

public class AddHairdresserController {

  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField phoneNumberField;
  @FXML private TextField specializationField;
  @FXML private Button submitButton;
  @FXML private Button cancelButton;

  private Runnable onSuccess;

  public void setOnSuccess(Runnable onSuccess) {
    this.onSuccess = onSuccess;
  }

  @FXML
  private void handleSubmit() {
    String firstName = firstNameField.getText().trim();
    String lastName = lastNameField.getText().trim();
    String phoneNumber = phoneNumberField.getText().trim();
    String specialization = specializationField.getText().trim();

    if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || specialization.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    if (!checkPhone(phoneNumber) || phoneNumber.length() != 9) {
      showAlert("Error", "Phone number should consists of nine digits.");
      return;
    }

    Hairdresser newHairdresser = new Hairdresser(0, firstName, lastName, phoneNumber, specialization);
    String flag = HairdresserDAO.addHairdresser(newHairdresser);

    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    if (onSuccess != null) {
      onSuccess.run();
    }
    closeWindow();
  }

  @FXML
  private void handleCancel() {
    closeWindow();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void closeWindow() {
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
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
