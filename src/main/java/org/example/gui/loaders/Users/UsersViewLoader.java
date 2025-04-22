package org.example.gui.loaders.Users;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Users.UsersViewController;

import java.io.IOException;

public class UsersViewLoader {

  public static void loadUsersView() {
    try {
      FXMLLoader loader = new FXMLLoader(org.example.gui.loaders.Users.UsersViewLoader.class.getResource("/fxml/Users/UsersView.fxml"));
      VBox root = loader.load();

      UsersViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
