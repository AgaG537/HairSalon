package org.example.gui.loaders.UserInfo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.UserInfo.MainController;
import org.example.gui.controllers.UserInfo.MainRestrictedController;
import org.example.model.User;

import java.io.IOException;

public class MainRestrictedViewLoader {

  public static void loadMainView(User user, Stage currentStage) {
    try {
      FXMLLoader loader = new FXMLLoader(MainRestrictedViewLoader.class.getResource("/fxml/UserInfo/MainRestrictedView.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(loader.load());

      MainRestrictedController controller = loader.getController();
      controller.setUser(user);

      stage.setScene(scene);
      stage.setTitle("Hair Salon Management");
      stage.show();

      if (currentStage != null) {
        currentStage.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
