package org.example.gui.loaders.Appointments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.controllers.Appointments.EditAppointmentController;
import org.example.gui.controllers.Hairdressers.EditHairdresserController;
import org.example.model.Appointment;
import org.example.model.Hairdresser;

import java.io.IOException;

public class EditAppointmentViewLoader {

  public static void loadEditAppointmentView(Appointment appointment, Runnable onSuccessCallback) {
    try {
      FXMLLoader loader = new FXMLLoader(EditAppointmentViewLoader.class.getResource("/fxml/Appointments/EditAppointmentView.fxml"));
      Parent root = loader.load();

      EditAppointmentController controller = loader.getController();
      controller.setAppointment(appointment);
      controller.addHairdresserChoiceBoxItems();
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
