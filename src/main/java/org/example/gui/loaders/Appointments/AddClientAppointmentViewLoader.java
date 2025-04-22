package org.example.gui.loaders.Appointments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.gui.controllers.Appointments.AddAppointmentController;
import org.example.gui.controllers.Appointments.AddClientAppointmentController;
import org.example.model.Appointment;

import java.io.IOException;

public class AddClientAppointmentViewLoader {

  public static void loadAddClientAppointmentView(Appointment appointment, Runnable onSuccess) {
    try {
      FXMLLoader loader = new FXMLLoader(AddClientAppointmentViewLoader.class.getResource("/fxml/Appointments/AddClientAppointmentView.fxml"));
      VBox root = loader.load();

      AddClientAppointmentController controller = loader.getController();
      controller.setAppointment(appointment);
      controller.addClientChoiceBoxItems();
      controller.addServiceChoiceBoxItems();
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
