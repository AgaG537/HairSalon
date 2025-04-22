package org.example.gui.loaders.Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Services.AddServiceController;

import java.io.IOException;

public class AddServiceViewLoader {

  public static void loadAddServiceView(Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(AddServiceViewLoader.class.getResource("/fxml/Services/AddServiceView.fxml"));
      Parent root = loader.load();

      AddServiceController controller = loader.getController();
      controller.setOnSuccess(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku AddServiceView.fxml");
    }
  }
}
