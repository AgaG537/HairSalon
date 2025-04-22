package org.example.gui.loaders.Users;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Users.EditUserController;
import org.example.model.User;

import java.io.IOException;

public class EditUserViewLoader {

  public static void loadEditUserView(User user, Runnable onSuccess) {
    try {
      FXMLLoader loader = new FXMLLoader(EditUserViewLoader.class.getResource("/fxml/Users/EditUserView.fxml"));
      Stage stage = new Stage();
      stage.setScene(new Scene(loader.load()));
      stage.initModality(Modality.APPLICATION_MODAL);

      EditUserController controller = loader.getController();
      controller.setUser(user);
      controller.setOnSuccessCallback(onSuccess);

      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
