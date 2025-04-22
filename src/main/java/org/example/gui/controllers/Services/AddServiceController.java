package org.example.gui.controllers.Services;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.database.ServiceDAO;
import org.example.model.Service;

import java.util.Objects;

public class AddServiceController {

  @FXML private TextField serviceTypeField;
  @FXML private TextField priceField;

  private Runnable onSuccess;

  public void setOnSuccess(Runnable onSuccess) {
    this.onSuccess = onSuccess;
  }

  @FXML
  private void handleSubmit() {
    String serviceType = serviceTypeField.getText().trim();
    String price = priceField.getText().trim();

    if (serviceType.isEmpty() || price.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    try {
      Service newService = new Service(0, serviceType, Double.parseDouble(price));
      String flag = ServiceDAO.addService(newService);

      if (!Objects.equals(flag, "")) {
        showAlert("Error", flag);
        return;
      }
    } catch (NumberFormatException e) {
      showAlert("Error", "Price must be a number.");
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
    Stage stage = (Stage) serviceTypeField.getScene().getWindow();
    stage.close();
  }
}
