package org.example.gui.controllers.Appointments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database.AppointmentDAO;
import org.example.database.ClientAppointmentDAO;
import org.example.gui.loaders.Appointments.AddAppointmentViewLoader;
import org.example.gui.loaders.Appointments.AddClientAppointmentViewLoader;
import org.example.gui.loaders.Appointments.EditAppointmentViewLoader;
import org.example.gui.loaders.Appointments.EditClientAppointmentViewLoader;
import org.example.model.Appointment;

import java.util.Objects;
import java.util.function.Predicate;

public class AppointmentsViewController {

  @FXML private TableView<Appointment> appointmentTable;
  @FXML private TableColumn<Appointment, String> dateColumn;
  @FXML private TableColumn<Appointment, String> timeColumn;
  @FXML private TableColumn<Appointment, String> hairdresserInfoColumn;
  @FXML private TableColumn<Appointment, String> statusColumn;
  @FXML private TableColumn<Appointment, String> clientInfoColumn;
  @FXML private TableColumn<Appointment, String> serviceInfoColumn;

  @FXML public TextField dateField;
  @FXML public TextField timeField;
  @FXML private TextField hairdresserInfoField;
  @FXML private ChoiceBox<String> statusChoiceBox;
  @FXML private TextField clientInfoField;
  @FXML private TextField serviceInfoField;

  @FXML private TextField filterField;
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;

  private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
  private FilteredList<Appointment> filteredList;

  @FXML
  private void initialize() {
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
    hairdresserInfoColumn.setCellValueFactory(new PropertyValueFactory<>("hairdresserInfo"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    clientInfoColumn.setCellValueFactory(new PropertyValueFactory<>("clientInfo"));
    serviceInfoColumn.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));

    loadAppointments();

    filteredList = new FilteredList<>(appointmentList, p -> true);
    appointmentTable.setItems(filteredList);
  }

  private void loadAppointments() {
    appointmentList.setAll(AppointmentDAO.getAllAppointments());
  }

  @FXML
  private void handleAddAppointment() {
    AddAppointmentViewLoader.loadAddAppointmentView(() -> {
      loadAppointments();
      showAlert("Success", "Appointment added.");
    });
  }

  @FXML
  private void handleDeleteAppointment() {
    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
    if (selectedAppointment != null) {
      String flag = AppointmentDAO.deleteAppointment(selectedAppointment.getId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadAppointments();
      showAlert("Success", "Appointment deleted.");
    } else {
      showAlert("Error", "Choose an appointment to delete.");
    }
  }

  @FXML
  private void handleEditAppointment() {
    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
    if (selectedAppointment != null) {
      EditAppointmentViewLoader.loadEditAppointmentView(selectedAppointment, () -> {
        loadAppointments();
        showAlert("Success", "Appointment updated.");
      });
    } else {
      showAlert("Error", "Choose an appointment to update.");
    }
  }


  @FXML
  private void handleAddClientAppointment() {
    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
    if (selectedAppointment == null) {
      showAlert("Error", "Choose an appointment to be able to add Client.");
    }
    else if (Objects.equals(selectedAppointment.getStatus(), "booked")) {
      showAlert("Error", "This appointment is already booked.");
    }
    else {
      AddClientAppointmentViewLoader.loadAddClientAppointmentView(selectedAppointment, () -> {
        loadAppointments();
        showAlert("Success", "Client added to appointment.");
      });
    }
  }

  @FXML
  private void handleDeleteClientAppointment() {
    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
    if (selectedAppointment == null) {
      showAlert("Error", "Choose an appointment to be able to delete Client.");
    } else if (Objects.equals(selectedAppointment.getStatus(), "available")) {
      showAlert("Error", "There is no Client, nothing to delete.");
    } else {
      String flag = ClientAppointmentDAO.deleteClientAppointment(selectedAppointment.getId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadAppointments();
      showAlert("Success", "Client deleted from appointment.");
    }
  }

  @FXML
  private void handleEditClientAppointment() {
    Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
    if (selectedAppointment == null) {
      showAlert("Error", "Choose an appointment to be able to edit Client info.");
    } else if (Objects.equals(selectedAppointment.getStatus(), "available")) {
      showAlert("Error", "No Client, nothing to edit.");
    } else {
      EditClientAppointmentViewLoader.loadEditClientAppointmentView(selectedAppointment, () -> {
        loadAppointments();
        showAlert("Success", "Client info linked to appointment updated.");
      });
    }
  }

  @FXML
  private void handleFilterAppointments() {
    Predicate<Appointment> filter = appointment -> {
      String dateFilter = dateField.getText().toLowerCase();
      String timeFilter = timeField.getText().toLowerCase();
      String hairdresserInfoFilter = hairdresserInfoField.getText().toLowerCase();
      String statusFilter = statusChoiceBox.getValue();
      String clientInfoFilter = clientInfoField.getText().toLowerCase();
      String serviceInfoFilter = serviceInfoField.getText().toLowerCase();

      boolean matchesDate = appointment.getDate().toLowerCase().contains(dateFilter) || dateFilter.isEmpty();
      boolean matchesTime = appointment.getTime().toLowerCase().contains(timeFilter) || timeFilter.isEmpty();
      boolean matchesHairdresserInfo = appointment.getHairdresserInfo().toLowerCase().contains(hairdresserInfoFilter) || hairdresserInfoFilter.isEmpty();
      if (statusFilter != null) {
        boolean matchesStatus = appointment.getStatus().toLowerCase().contains(statusFilter) || statusFilter.isEmpty();
        boolean matchesClientInfo = appointment.getClientInfo().toLowerCase().contains(clientInfoFilter) || clientInfoFilter.isEmpty();
        boolean matchesServiceInfo = appointment.getServiceInfo().toLowerCase().contains(serviceInfoFilter) || serviceInfoFilter.isEmpty();
        return matchesDate && matchesTime && matchesHairdresserInfo && matchesStatus && matchesClientInfo && matchesServiceInfo;
      } else {
        boolean matchesClientInfo = appointment.getClientInfo().toLowerCase().contains(clientInfoFilter) || clientInfoFilter.isEmpty();
        boolean matchesServiceInfo = appointment.getServiceInfo().toLowerCase().contains(serviceInfoFilter) || serviceInfoFilter.isEmpty();
        return matchesDate && matchesTime && matchesHairdresserInfo && matchesClientInfo && matchesServiceInfo;
      }

    };

    filteredList.setPredicate(filter);
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleRefresh() {
    loadAppointments();
  }
}
