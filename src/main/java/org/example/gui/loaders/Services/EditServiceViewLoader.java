package org.example.gui.loaders.Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Services.EditServiceController;
import org.example.model.Service;

import java.io.IOException;

public class EditServiceViewLoader {

  public static void loadEditServiceView(Service service, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditServiceViewLoader.class.getResource("/fxml/Services/EditServiceView.fxml"));
      Parent root = loader.load();

      EditServiceController controller = loader.getController();
      controller.setService(service);
      controller.setOnSuccessCallback(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku EditServiceView.fxml");
    }
  }
}
