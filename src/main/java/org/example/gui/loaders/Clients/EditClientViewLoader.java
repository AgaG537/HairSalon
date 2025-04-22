package org.example.gui.loaders.Clients;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Clients.EditClientController;
import org.example.model.Client;

import java.io.IOException;

public class EditClientViewLoader {

  public static void loadEditClientView(Client client, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditClientViewLoader.class.getResource("/fxml/Clients/EditClientView.fxml"));
      Parent root = loader.load();

      EditClientController controller = loader.getController();
      controller.setClient(client);
      controller.setOnSuccessCallback(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku EditClientView.fxml");
    }
  }
}
