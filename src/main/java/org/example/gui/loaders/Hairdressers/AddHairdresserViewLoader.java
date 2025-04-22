package org.example.gui.loaders.Hairdressers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Hairdressers.AddHairdresserController;

import java.io.IOException;

public class AddHairdresserViewLoader {

  public static void loadAddHairdresserView(Runnable onSuccess) {
    try {
      FXMLLoader loader = new FXMLLoader(AddHairdresserViewLoader.class.getResource("/fxml/Hairdressers/AddHairdresserView.fxml"));
      VBox root = loader.load();

      AddHairdresserController controller = loader.getController();
      controller.setOnSuccess(onSuccess);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
