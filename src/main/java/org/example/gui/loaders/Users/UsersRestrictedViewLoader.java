package org.example.gui.loaders.Users;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Users.UsersRestrictedViewController;
import org.example.gui.controllers.Users.UsersViewController;

import java.io.IOException;

public class UsersRestrictedViewLoader {

  public static void loadHairdresserView() {
    try {
      FXMLLoader loader = new FXMLLoader(UsersRestrictedViewLoader.class.getResource("/fxml/Users/UsersRestrictedView.fxml"));
      VBox root = loader.load();

      UsersRestrictedViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
