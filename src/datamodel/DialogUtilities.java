package datamodel;

import controllers.NewRosterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Optional;

public class DialogUtilities {

    public static Roster showRosterDialog(Pane parentPane) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parentPane.getScene().getWindow());
        dialog.setTitle("Create A New Roster");
        dialog.setHeaderText("Create a new roster using the fields below");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(DialogUtilities.class.getResource("/views/newRoster.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NewRosterController controller = fxmlLoader.getController();

        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(ActionEvent.ACTION, event -> {

            if (controller.isWeekGap()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Week Gap");
                alert.setHeaderText(null);
                alert.setContentText("Please ensure there are no gaps between weeks");
                alert.showAndWait();
                event.consume();
            }
            if (!controller.isRosterName()) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Missing Information");
                alert2.setHeaderText(null);
                alert2.setContentText("Please enter a name for the roster");
                alert2.showAndWait();
                event.consume();
            }
        });


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Roster newRoster = controller.newRoster();
            return newRoster;
        }

        return null;
    }

    public static Roster showEditRosterDialog(Pane parentPane, Roster roster) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parentPane.getScene().getWindow());
        dialog.setTitle("Edit Roster");
        dialog.setHeaderText("Edit the roster using the fields below");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(DialogUtilities.class.getResource("/views/newRoster.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NewRosterController controller = fxmlLoader.getController();
        controller.editRoster(roster);

        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(ActionEvent.ACTION, event -> {

            if (controller.isWeekGap()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Week Gap");
                alert.setHeaderText(null);
                alert.setContentText("Please ensure there are no gaps between weeks");
                alert.showAndWait();
                event.consume();
            }
            if (!controller.isRosterName()) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Missing Information");
                alert2.setHeaderText(null);
                alert2.setContentText("Please enter a name for the roster");
                alert2.showAndWait();
                event.consume();
            }
        });


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Roster newRoster = controller.newRoster();
            return newRoster;
        }

        return null;
    }


}

