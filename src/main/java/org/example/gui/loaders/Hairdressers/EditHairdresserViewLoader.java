package org.example.gui.loaders.Hairdressers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Hairdressers.EditHairdresserController;
import org.example.model.Hairdresser;

import java.io.IOException;

public class EditHairdresserViewLoader {

  public static void loadEditHairdresserView(Hairdresser hairdresser, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditHairdresserViewLoader.class.getResource("/fxml/Hairdressers/EditHairdresserView.fxml"));
      Parent root = loader.load();

      EditHairdresserController controller = loader.getController();
      controller.setHairdresser(hairdresser);
      controller.setOnSuccessCallback(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku EditHairdresserView.fxml");
    }
  }
}
