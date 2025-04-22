package org.example.gui.controllers.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database.ServiceDAO;
import org.example.gui.loaders.Services.AddServiceViewLoader;
import org.example.gui.loaders.Services.EditServiceViewLoader;
import org.example.model.Service;

import java.util.Objects;
import java.util.function.Predicate;

public class ServicesViewController {

  @FXML private TableView<Service> servicesTable;
  @FXML private TableColumn<Service, String> serviceTypeColumn;
  @FXML private TableColumn<Service, String> priceColumn;

  public TextField serviceTypeField;
  public TextField priceField;

  @FXML private TextField filterField;
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;

  private ObservableList<Service> serviceList = FXCollections.observableArrayList();
  private FilteredList<Service> filteredList;

  @FXML
  private void initialize() {
    serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    loadServices();

    filteredList = new FilteredList<>(serviceList, p -> true);
    servicesTable.setItems(filteredList);
  }

  private void loadServices() {
    serviceList.setAll(ServiceDAO.getAllServices());
  }

  @FXML
  private void handleAddService() {
    AddServiceViewLoader.loadAddServiceView(() -> {
      loadServices();
      showAlert("Success", "Service added.");
    });
  }

  @FXML
  private void handleDeleteService() {
    Service selectedService = servicesTable.getSelectionModel().getSelectedItem();
    if (selectedService != null) {
      String flag = ServiceDAO.deleteService(selectedService.getServiceId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadServices();
      showAlert("Success", "Service deleted.");
    } else {
      showAlert("Error", "Choose service to delete.");
    }
  }

  @FXML
  private void handleEditService() {
    Service selectedService = servicesTable.getSelectionModel().getSelectedItem();
    if (selectedService != null) {
      EditServiceViewLoader.loadEditServiceView(selectedService, () -> {
        loadServices();
        showAlert("Success", "Service info updated.");
      });
    } else {
      showAlert("Error", "Choose service to edit.");
    }
  }

  @FXML
  private void handleFilterServices() {
    Predicate<Service> filter = service -> {
      String serviceTypeFilter = serviceTypeField.getText().toLowerCase();
      String priceFilterText = priceField.getText();

      boolean matchesServiceType = service.getType().toLowerCase().contains(serviceTypeFilter) || serviceTypeFilter.isEmpty();

      boolean matchesPrice = true;
      if (!priceFilterText.isEmpty()) {
        try {
          double priceFilter = Double.parseDouble(priceFilterText);
          matchesPrice = service.getPrice() <= priceFilter;
        } catch (NumberFormatException e) {
          showAlert("Error", "Number format required.");
          return false;
        }
      }

      return matchesServiceType && matchesPrice;
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
    loadServices();
  }
}