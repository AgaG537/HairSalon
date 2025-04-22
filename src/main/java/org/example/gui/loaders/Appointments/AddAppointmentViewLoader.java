package org.example.gui.loaders.Appointments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.gui.controllers.Appointments.AddAppointmentController;
import org.example.gui.controllers.Hairdressers.AddHairdresserController;

import java.io.IOException;

public class AddAppointmentViewLoader {

  public static void loadAddAppointmentView(Runnable onSuccess) {
    try {
      FXMLLoader loader = new FXMLLoader(AddAppointmentViewLoader.class.getResource("/fxml/Appointments/AddAppointmentView.fxml"));
      VBox root = loader.load();

      AddAppointmentController controller = loader.getController();
      controller.addHairdresserChoiceBoxItems();
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
