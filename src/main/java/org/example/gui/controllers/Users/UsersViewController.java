package org.example.gui.controllers.Users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.database.UserDAO;
import org.example.gui.loaders.Users.AddUserViewLoader;
import org.example.gui.loaders.Users.EditUserViewLoader;
import org.example.model.User;

import java.util.Objects;
import java.util.function.Predicate;

public class UsersViewController {

  @FXML private TableView<User> userTable;
  @FXML private TableColumn<User, String> usernameColumn;
  @FXML private TableColumn<User, String> roleColumn;
  @FXML private TableColumn<User, String> firstNameColumn;
  @FXML private TableColumn<User, String> lastNameColumn;

  @FXML private TextField usernameField;
  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private ChoiceBox<String> roleChoiceBox;

  private ObservableList<User> userList = FXCollections.observableArrayList();
  private FilteredList<User> filteredList;

  @FXML
  public void initialize() {
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    loadUsers();

    filteredList = new FilteredList<>(userList, p -> true);
    userTable.setItems(filteredList);
  }

  private void loadUsers() {
    userList.setAll(UserDAO.getAllUsers());
  }

  @FXML
  private void handleAddUser() {
    AddUserViewLoader.loadAddUserView(() -> {
      loadUsers();
      showAlert("Success", "User added.");
    });
  }

  @FXML
  private void handleEditUser() {
    User selectedUser = userTable.getSelectionModel().getSelectedItem();
    if (selectedUser != null) {
      EditUserViewLoader.loadEditUserView(selectedUser, () -> {
        loadUsers();
        showAlert("Success", "User data has been updated.");
      });
    } else {
      showAlert("Error", "Choose a user to edit.");
    }
  }

  @FXML
  private void handleDeleteUser() {
    User selectedUser = userTable.getSelectionModel().getSelectedItem();
    if (selectedUser != null) {
      String flag = UserDAO.deleteUser(selectedUser.getId());
      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
      loadUsers();
      showAlert("Success", "User deleted.");
    } else {
      showAlert("Error", "Choose a user to delete.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleFilterUsers() {
    Predicate<User> predicate = user -> {
      String usernameFilter = usernameField.getText().toLowerCase();
      String firstNameFilter = firstNameField.getText().toLowerCase();
      String lastNameFilter = lastNameField.getText().toLowerCase();
      String roleFilter = roleChoiceBox.getValue();

      boolean matchesUsername = user.getUsername().toLowerCase().contains(usernameFilter) || usernameFilter.isEmpty();
      boolean matchesFirstName = user.getFirstName().toLowerCase().contains(firstNameFilter) || firstNameFilter.isEmpty();
      boolean matchesLastName = user.getLastName().toLowerCase().contains(lastNameFilter) || lastNameFilter.isEmpty();

      if (roleFilter != null) {
        boolean matchesRole = user.getRole().toLowerCase().contains(roleFilter) || roleFilter.isEmpty();
        return matchesUsername && matchesFirstName && matchesLastName && matchesRole;
      } else {
        return matchesUsername && matchesFirstName && matchesLastName;
      }
    };

    filteredList.setPredicate(predicate);
  }

  @FXML
  private void handleRefresh() {
    loadUsers();
  }
}
