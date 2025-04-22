package org.example.gui.loaders.Hairdressers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Hairdressers.HairdressersViewController;

import java.io.IOException;

public class HairdressersViewLoader {

  public static void loadHairdresserView() {
    try {
      FXMLLoader loader = new FXMLLoader(HairdressersViewLoader.class.getResource("/fxml/Hairdressers/HairdressersView.fxml"));
      VBox root = loader.load();

      HairdressersViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
