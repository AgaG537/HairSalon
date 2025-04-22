package org.example.gui.controllers.Clients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database.ClientDAO;
import org.example.gui.loaders.Clients.AddClientViewLoader;
import org.example.gui.loaders.Clients.EditClientViewLoader;
import org.example.model.Client;

import java.util.Objects;
import java.util.function.Predicate;

public class ClientsViewController {

  @FXML private TableView<Client> clientTable;
  @FXML private TableColumn<Client, String> firstNameColumn;
  @FXML private TableColumn<Client, String> lastNameColumn;
  @FXML private TableColumn<Client, String> phoneNumberColumn;
  @FXML private TableColumn<Client, String> emailColumn;

  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField phoneNumberField;
  @FXML private TextField emailField;

  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;
  @FXML private Button filterButton;

  private ObservableList<Client> clientList = FXCollections.observableArrayList();
  private FilteredList<Client> filteredList;

  @FXML
  public void initialize() {
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    loadClients();

    filteredList = new FilteredList<>(clientList, p -> true);
    clientTable.setItems(filteredList);
  }

  private void loadClients() {
    clientList.setAll(ClientDAO.getAllClients());
  }

  @FXML
  private void handleAddClient() {
    AddClientViewLoader.loadAddClientView(() -> {
      loadClients();
      showAlert("Success", "Client added.");
    });
  }

  @FXML
  private void handleDeleteClient() {
    Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
    if (selectedClient != null) {
      String flag = ClientDAO.deleteClient(selectedClient.getId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadClients();
      showAlert("Success","Client deleted.");
    } else {
      showAlert("Error", "Choose a Client to delete.");
    }
  }

  @FXML
  private void handleEditClient() {
    Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
    if (selectedClient != null) {
      EditClientViewLoader.loadEditClientView(selectedClient, () -> {
        loadClients();
        showAlert("Success", "Client updated.");
      });
    } else {
      showAlert("Error", "Choose a Client to update.");
    }
  }

  @FXML
  private void handleFilterClients() {
    Predicate<Client> filter = client -> {
      String firstNameFilter = firstNameField.getText().toLowerCase();
      String lastNameFilter = lastNameField.getText().toLowerCase();
      String phoneFilter = phoneNumberField.getText().toLowerCase();
      String emailFilter = emailField.getText().toLowerCase();

      boolean matchesFirstName = client.getFirstName().toLowerCase().contains(firstNameFilter) || firstNameFilter.isEmpty();
      boolean matchesLastName = client.getLastName().toLowerCase().contains(lastNameFilter) || lastNameFilter.isEmpty();
      boolean matchesPhone = client.getPhoneNumber().toLowerCase().contains(phoneFilter) || phoneFilter.isEmpty();
      if (!Objects.equals(client.getEmail(), null) && !emailFilter.isEmpty()) {
        boolean matchesEmail = client.getEmail().toLowerCase().contains(emailFilter) || emailFilter.isEmpty();
        return matchesFirstName && matchesLastName && matchesPhone && matchesEmail;
      } else if (Objects.equals(client.getEmail(), null) && !emailFilter.isEmpty()) {
        return false;
      } else {
        return matchesFirstName && matchesLastName && matchesPhone;
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
    loadClients();
  }
}
