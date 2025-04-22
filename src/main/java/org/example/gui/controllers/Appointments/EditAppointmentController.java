package org.example.gui.controllers.Appointments;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.example.database.AppointmentDAO;
import org.example.database.HairdresserDAO;
import org.example.model.Appointment;
import org.example.model.Hairdresser;

import java.util.List;
import java.util.Objects;

public class EditAppointmentController {

  @FXML private DatePicker datePicker;
  @FXML private ChoiceBox<String> timeChoiceBox;
  @FXML private ChoiceBox<String> hairdresserChoiceBox;

  private Appointment appointment;
  private Runnable onSuccessCallback;

  public void initialize() {
    populateTimeChoiceBox();
  }

  private void populateTimeChoiceBox() {
    for (int hour = 9; hour <= 17; hour++) {
      timeChoiceBox.getItems().add(String.format("%02d:00", hour));
      timeChoiceBox.getItems().add(String.format("%02d:30", hour));
    }
  }

  public void addHairdresserChoiceBoxItems() {
    List<Hairdresser> hairdressers = HairdresserDAO.getAllHairdressers();
    for (Hairdresser hairdresser : hairdressers) {
      int hairdresserId = hairdresser.getId();
        hairdresserChoiceBox.getItems().add(
            hairdresser.getFirstName() + " " + hairdresser.getLastName()
                + " (id: " + hairdresserId + ")");
      if (hairdresserId == appointment.getHairdresserId()) {
        hairdresserChoiceBox.setValue(
            hairdresser.getFirstName() + " " + hairdresser.getLastName()
                + " (id: " + hairdresserId + ")");
      }
    }
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
    datePicker.setValue(appointment.getFormattedDate());
    timeChoiceBox.setValue(appointment.getTime().substring(0, 5));
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
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

    appointment.setDate(date);
    appointment.setTime(time);
    appointment.setHairdresserId(hairdresserId);
    String flag = AppointmentDAO.updateAppointment(appointment);

    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    onSuccessCallback.run();
    ((Stage) datePicker.getScene().getWindow()).close();
  }

  @FXML
  public void handleCancel() {
    ((Stage) datePicker.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
