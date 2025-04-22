package org.example.gui.loaders.Clients;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Clients.ClientsViewController;

import java.io.IOException;

public class ClientsViewLoader {

  public static void loadClientView() {
    try {
      FXMLLoader loader = new FXMLLoader(ClientsViewLoader.class.getResource("/fxml/Clients/ClientsView.fxml"));
      Parent root = loader.load();

      ClientsViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku ClientsView.fxml");
    }
  }
}
