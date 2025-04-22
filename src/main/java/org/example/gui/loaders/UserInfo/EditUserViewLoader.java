package org.example.gui.loaders.UserInfo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.UserInfo.EditUserController;
import org.example.model.User;

import java.io.IOException;

public class EditUserViewLoader {

  public static void loadEditUserView(User user, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditUserViewLoader.class.getResource("/fxml/UserInfo/EditUserView.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(loader.load());

      EditUserController controller = loader.getController();
      controller.setUser(user);
      controller.setOnSuccessCallback(onSuccessCallback);

      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
