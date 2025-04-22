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

import java.util.function.Predicate;

public class UsersRestrictedViewController {

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
  private void handleFilterUsers() {
    Predicate<User> predicate = user -> {
      String username = usernameField.getText().toLowerCase();
      String firstName = firstNameField.getText().toLowerCase();
      String lastName = lastNameField.getText().toLowerCase();
      String role = roleChoiceBox.getValue();

      return user.getUsername().toLowerCase().contains(username)
          && user.getFirstName().toLowerCase().contains(firstName)
          && user.getLastName().toLowerCase().contains(lastName)
          && user.getRole().toLowerCase().contains(role);
    };

    filteredList.setPredicate(predicate);
  }

  @FXML
  private void handleRefresh() {
    loadUsers();
  }
}
