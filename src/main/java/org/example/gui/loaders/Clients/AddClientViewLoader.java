package org.example.gui.loaders.Clients;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Clients.AddClientController;

import java.io.IOException;

public class AddClientViewLoader {

  public static void loadAddClientView(Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(AddClientViewLoader.class.getResource("/fxml/Clients/AddClientView.fxml"));
      Parent root = loader.load();

      AddClientController controller = loader.getController();
      controller.setOnSuccessCallback(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku AddClientView.fxml");
    }
  }
}
