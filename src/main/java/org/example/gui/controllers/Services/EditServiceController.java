package org.example.gui.controllers.Services;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.database.ServiceDAO;
import org.example.model.Service;

import java.util.Objects;

public class EditServiceController {


  @FXML private TextField serviceTypeField;
  @FXML private TextField priceField;

  private Service service;
  private Runnable onSuccessCallback;

  public void setService(Service service) {
    this.service = service;
    serviceTypeField.setText(service.getType());
    priceField.setText(String.valueOf(service.getPrice()));
  }

  public void setOnSuccessCallback(Runnable onSuccessCallback) {
    this.onSuccessCallback = onSuccessCallback;
  }

  @FXML
  public void handleSave() {
    String serviceType = serviceTypeField.getText();
    String price = priceField.getText();

    if (serviceType.isEmpty() || price.isEmpty()) {
      showAlert("Error", "All fields are required.");
      return;
    }

    try {
      service.setType(serviceType);
      service.setPrice(Double.parseDouble(price));
    } catch (NumberFormatException e) {
      showAlert("Error", "Price must be a number.");
      return;
    }

    String flag = ServiceDAO.updateService(service);

    if (!Objects.equals(flag, "")) {
      showAlert("Error", flag);
      return;
    }

    onSuccessCallback.run();
    ((Stage) serviceTypeField.getScene().getWindow()).close();
  }

  @FXML
  public void handleCancel() {
    ((Stage) serviceTypeField.getScene().getWindow()).close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
