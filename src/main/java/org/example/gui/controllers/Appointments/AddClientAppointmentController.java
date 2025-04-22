package org.example.gui.controllers.Appointments;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.example.database.*;
import org.example.model.Appointment;
import org.example.model.Client;
import org.example.model.Service;

import java.util.List;
import java.util.Objects;

public class AddClientAppointmentController {

  @FXML private ChoiceBox<String> clientChoiceBox;
  @FXML private ChoiceBox<String> serviceChoiceBox;
  @FXML private Button submitButton;
  @FXML private Button cancelButton;

  private Appointment appointment;
  private Runnable onSuccess;

  public void setOnSuccess(Runnable onSuccess) {
    this.onSuccess = onSuccess;
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
  }

  public void addClientChoiceBoxItems() {
    List<Client> clients = ClientDAO.getAllClients();
    for (Client client : clients) {
      String clientName = client.getFirstName() + " " + client.getLastName();
      int clientId = client.getId();
      clientChoiceBox.getItems().add(clientName + " (id: " + clientId + ")");
    }
  }

  public void addServiceChoiceBoxItems() {
    List<Service> services = ServiceDAO.getAllServices();
    for (Service service : services) {
      String serviceType = service.getType();
      int serviceId = service.getServiceId();
      serviceChoiceBox.getItems().add(serviceType + " (id: " + serviceId + ")");
    }
  }

  @FXML
  private void handleSubmit() {
    String clientInfo = clientChoiceBox.getValue().trim();
    String serviceInfo = serviceChoiceBox.getValue().trim();

    if (clientInfo.isEmpty() || serviceInfo.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    int index = clientInfo.indexOf("(id: ");
    String clientInfoSubstring = clientInfo.substring(index + 5);
    index = clientInfoSubstring.indexOf(")");
    int clientId = Integer.parseInt(clientInfoSubstring.substring(0, index));

    index = serviceInfo.indexOf("(id: ");
    String serviceInfoSubstring = serviceInfo.substring(index + 5);
    index = serviceInfoSubstring.indexOf(")");
    int serviceId = Integer.parseInt(serviceInfoSubstring.substring(0, index));

    appointment.setClientId(clientId);
    appointment.setServiceId(serviceId);
    String flag = ClientAppointmentDAO.addClientAppointment(appointment.getId(), clientId, serviceId);
    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      closeWindow();
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
