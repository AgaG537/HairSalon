package org.example.gui.loaders.Appointments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Appointments.AppointmentsViewController;
import org.example.gui.controllers.Hairdressers.HairdressersViewController;
import org.example.gui.loaders.UserInfo.MainViewLoader;

import java.io.IOException;
import java.util.Objects;

public class AppointmentsViewLoader {

  public static void loadHairdresserView() {
    try {
      FXMLLoader loader = new FXMLLoader(AppointmentsViewLoader.class.getResource("/fxml/Appointments/AppointmentsView.fxml"));
      VBox root = loader.load();

      AppointmentsViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
