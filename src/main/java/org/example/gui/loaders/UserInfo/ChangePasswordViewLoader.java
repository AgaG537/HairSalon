package org.example.gui.loaders.UserInfo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.UserInfo.ChangePasswordController;
import org.example.model.User;

import java.io.IOException;

public class ChangePasswordViewLoader {

  public static void loadChangePasswordView(User user, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(ChangePasswordViewLoader.class.getResource("/fxml/UserInfo/ChangePasswordView.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(loader.load());

      ChangePasswordController controller = loader.getController();
      controller.setUser(user);
      controller.setOnSuccessCallback(onSuccessCallback);

      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
