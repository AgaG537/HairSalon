package org.example.gui.controllers.Hairdressers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.HairdresserDAO;
import org.example.model.Hairdresser;

import java.util.Objects;

public class EditHairdresserController {

  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField phoneNumberField;
  @FXML private TextField specializationField;

  private Hairdresser hairdresser;
  private Runnable onSuccessCallback;

  public void setHairdresser(Hairdresser hairdresser) {
    this.hairdresser = hairdresser;
    firstNameField.setText(hairdresser.getFirstName());
    lastNameField.setText(hairdresser.getLastName());
    phoneNumberField.setText(hairdresser.getPhoneNumber());
    specializationField.setText(hairdresser.getSpecialization());
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String phoneNumber = phoneNumberField.getText();
    String specialization = specializationField.getText();

    if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || specialization.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    if (!checkPhone(phoneNumber) || phoneNumber.length() != 9) {
      showAlert("Error", "Phone number should consists of nine digits.");
      return;
    }

    hairdresser.setFirstName(firstName);
    hairdresser.setLastName(lastName);
    hairdresser.setPhoneNumber(phoneNumber);
    hairdresser.setSpecialization(specialization);

    String flag = HairdresserDAO.updateHairdresser(hairdresser);

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
