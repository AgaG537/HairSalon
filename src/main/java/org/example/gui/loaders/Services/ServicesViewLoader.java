package org.example.gui.loaders.Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Services.ServicesViewController;

import java.io.IOException;

public class ServicesViewLoader {

  public static void loadServiceView() {
    try {
      FXMLLoader loader = new FXMLLoader(ServicesViewLoader.class.getResource("/fxml/Services/ServicesView.fxml"));
      Parent root = loader.load();

      ServicesViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku ServicessView.fxml");
    }
  }
}
