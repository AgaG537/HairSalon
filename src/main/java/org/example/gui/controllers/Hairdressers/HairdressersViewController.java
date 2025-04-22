package org.example.gui.controllers.Hairdressers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database.HairdresserDAO;
import org.example.gui.loaders.Hairdressers.AddHairdresserViewLoader;
import org.example.gui.loaders.Hairdressers.EditHairdresserViewLoader;
import org.example.model.Hairdresser;

import java.util.Objects;
import java.util.function.Predicate;

public class HairdressersViewController {

  @FXML private TableView<Hairdresser> hairdresserTable;
  @FXML private TableColumn<Hairdresser, String> firstNameColumn;
  @FXML private TableColumn<Hairdresser, String> lastNameColumn;
  @FXML private TableColumn<Hairdresser, String> phoneNumberColumn;
  @FXML private TableColumn<Hairdresser, String> specializationColumn;

  public TextField firstNameField;
  public TextField lastNameField;
  @FXML private TextField phoneNumberField;
  public TextField specializationField;

  @FXML private TextField filterField;
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;

  private ObservableList<Hairdresser> hairdresserList = FXCollections.observableArrayList();
  private FilteredList<Hairdresser> filteredList;

  @FXML
  private void initialize() {
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));

    loadHairdressers();

    filteredList = new FilteredList<>(hairdresserList, p -> true);
    hairdresserTable.setItems(filteredList);
  }

  private void loadHairdressers() {
    hairdresserList.setAll(HairdresserDAO.getAllHairdressers());
  }

  @FXML
  private void handleAddHairdresser() {
    AddHairdresserViewLoader.loadAddHairdresserView(() -> {
      loadHairdressers();
      showAlert("Success", "Hairdresser added.");
    });
  }

  @FXML
  private void handleDeleteHairdresser() {
    Hairdresser selectedHairdresser = hairdresserTable.getSelectionModel().getSelectedItem();
    if (selectedHairdresser != null) {
      String flag = HairdresserDAO.deleteHairdresser(selectedHairdresser.getId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadHairdressers();
      showAlert("Success", "Hairdresser deleted.");
    } else {
      showAlert("Error", "Choose a hairdresser to delete.");
    }
  }

  @FXML
  private void handleEditHairdresser() {
    Hairdresser selectedHairdresser = hairdresserTable.getSelectionModel().getSelectedItem();
    if (selectedHairdresser != null) {
      EditHairdresserViewLoader.loadEditHairdresserView(selectedHairdresser, () -> {
        loadHairdressers();
        showAlert("Success", "Hairdresser updated.");
      });
    } else {
      showAlert("Error", "Choose a hairdresser to update.");
    }
  }

  @FXML
  private void handleFilterHairdressers() {
    Predicate<Hairdresser> filter = hairdresser -> {
      String firstNameFilter = firstNameField.getText().toLowerCase();
      String lastNameFilter = lastNameField.getText().toLowerCase();
      String phoneFilter = phoneNumberField.getText().toLowerCase();
      String specializationFilter = specializationField.getText().toLowerCase();

      boolean matchesFirstName = hairdresser.getFirstName().toLowerCase().contains(firstNameFilter) || firstNameFilter.isEmpty();
      boolean matchesLastName = hairdresser.getLastName().toLowerCase().contains(lastNameFilter) || lastNameFilter.isEmpty();
      boolean matchesPhone = hairdresser.getPhoneNumber().toLowerCase().contains(phoneFilter) || phoneFilter.isEmpty();
      boolean matchesSpecialization = hairdresser.getSpecialization().toLowerCase().contains(specializationFilter) || specializationFilter.isEmpty();

      return matchesFirstName && matchesLastName && matchesPhone && matchesSpecialization;
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
    loadHairdressers();
  }
}
