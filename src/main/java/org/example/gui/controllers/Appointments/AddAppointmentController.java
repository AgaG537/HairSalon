package org.example.gui.controllers.Appointments;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.database.AppointmentDAO;
import org.example.database.HairdresserDAO;
import org.example.model.Appointment;
import org.example.model.Hairdresser;

import java.util.List;
import java.util.Objects;

public class AddAppointmentController {

  @FXML private DatePicker datePicker;
  @FXML private ChoiceBox<String> timeChoiceBox;
  @FXML private ChoiceBox<String> hairdresserChoiceBox;
  @FXML private Button submitButton;
  @FXML private Button cancelButton;

  private Runnable onSuccess;

  public void initialize() {
    populateTimeChoiceBox();
  }

  public void setOnSuccess(Runnable onSuccess) {
    this.onSuccess = onSuccess;
  }

  public void addHairdresserChoiceBoxItems() {
    List<Hairdresser> hairdressers = HairdresserDAO.getAllHairdressers();
    for (Hairdresser hairdresser : hairdressers) {
      String hairdresserName = hairdresser.getFirstName() + " " + hairdresser.getLastName();
      int hairdresserId = hairdresser.getId();
      hairdresserChoiceBox.getItems().add(hairdresserName + " (id: " + hairdresserId + ")");
    }
  }

  private void populateTimeChoiceBox() {
    for (int hour = 9; hour <= 17; hour++) {
      timeChoiceBox.getItems().add(String.format("%02d:00", hour));
      timeChoiceBox.getItems().add(String.format("%02d:30", hour));
    }
  }

  @FXML
  private void handleSubmit() {
    String date = String.valueOf(datePicker.getValue());
    String time = timeChoiceBox.getValue();
    String hairdresserInfo = hairdresserChoiceBox.getValue().trim();

    if (date.isEmpty() || time.isEmpty() || hairdresserInfo.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    int index = hairdresserInfo.indexOf("(id: ");
    String hairdresserInfoSubstring = hairdresserInfo.substring(index + 5);
    index = hairdresserInfoSubstring.indexOf(")");
    int hairdresserId = Integer.parseInt(hairdresserInfoSubstring.substring(0, index));

    Appointment newAppointment = new Appointment(0, date, time, hairdresserId);
    String flag = AppointmentDAO.addAppointment(newAppointment);
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
}
