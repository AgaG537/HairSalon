package org.example.gui.controllers.Appointments;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.example.database.*;
import org.example.model.Appointment;
import org.example.model.Client;
import org.example.model.Service;

import java.util.List;
import java.util.Objects;

public class EditClientAppointmentController {

  @FXML private ChoiceBox<String> clientChoiceBox;
  @FXML private ChoiceBox<String> serviceChoiceBox;

  private Appointment appointment;
  private Runnable onSuccessCallback;

  public void addClientChoiceBoxItems() {
    List<Client> clients = ClientDAO.getAllClients();
    for (Client client : clients) {
      int clientId = client.getId();
        clientChoiceBox.getItems().add(
            client.getFirstName() + " " + client.getLastName()
                + " (id: " + clientId + ")");
      if (clientId == appointment.getClientId()) {
        clientChoiceBox.setValue(
            client.getFirstName() + " " + client.getLastName()
                + " (id: " + clientId + ")");
      }
    }
  }

  public void addServiceChoiceBoxItems() {
    List<Service> services = ServiceDAO.getAllServices();
    for (Service service : services) {
      int serviceId = service.getServiceId();
      serviceChoiceBox.getItems().add(
          service.getType() + " (id: " + serviceId + ")");
      if (serviceId == appointment.getServiceId()) {
        serviceChoiceBox.setValue(
            service.getType() + " (id: " + serviceId + ")");
      }
    }
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
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
    String flag = ClientAppointmentDAO.updateClientAppointment(appointment.getId(), clientId, serviceId);

    if(!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    onSuccessCallback.run();
    ((Stage) serviceChoiceBox.getScene().getWindow()).close();
  }

  @FXML
  public void handleCancel() {
    ((Stage) serviceChoiceBox.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
