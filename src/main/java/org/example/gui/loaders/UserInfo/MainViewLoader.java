package org.example.gui.loaders.UserInfo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.UserInfo.MainController;
import org.example.model.User;

import java.io.IOException;
import java.util.Objects;

public class MainViewLoader {

  public static void loadMainView(User user, Stage currentStage) {
    try {
      FXMLLoader loader = new FXMLLoader(MainViewLoader.class.getResource("/fxml/UserInfo/MainView.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(loader.load());

      MainController controller = loader.getController();
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
