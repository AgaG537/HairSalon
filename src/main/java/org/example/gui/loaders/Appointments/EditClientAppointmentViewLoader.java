package org.example.gui.loaders.Appointments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Appointments.EditAppointmentController;
import org.example.gui.controllers.Appointments.EditClientAppointmentController;
import org.example.model.Appointment;

import java.io.IOException;

public class EditClientAppointmentViewLoader {

  public static void loadEditClientAppointmentView(Appointment appointment, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditClientAppointmentViewLoader.class.getResource("/fxml/Appointments/EditClientAppointmentView.fxml"));
      Parent root = loader.load();

      EditClientAppointmentController controller = loader.getController();
      controller.setAppointment(appointment);
      controller.addClientChoiceBoxItems();
      controller.addServiceChoiceBoxItems();
      controller.setOnSuccessCallback(onSuccessCallback);

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Nie udało się załadować widoku EditAppointmentView.fxml");
    }
  }
}
