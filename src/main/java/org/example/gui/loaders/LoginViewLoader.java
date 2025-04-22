package org.example.gui.loaders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewLoader {

  public static void loadLoginView(Stage currentStage) {
    try {
      FXMLLoader loader = new FXMLLoader(LoginViewLoader.class.getResource("/fxml/LoginView.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(loader.load());

      stage.setScene(scene);
      stage.show();

      if (currentStage != null) {
        currentStage.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
