package org.example.gui.loaders.Users;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Hairdressers.AddHairdresserController;
import org.example.gui.controllers.Users.AddUserController;
import org.example.gui.loaders.Hairdressers.AddHairdresserViewLoader;

import java.io.IOException;

public class AddUserViewLoader {

  public static void loadAddUserView(Runnable onSuccess) {
    try {
      FXMLLoader loader = new FXMLLoader(AddUserViewLoader.class.getResource("/fxml/Users/AddUserView.fxml"));
      VBox root = loader.load();

      AddUserController controller = loader.getController();
      controller.setOnSuccessCallback(onSuccess);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
