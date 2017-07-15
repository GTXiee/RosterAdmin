package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import datamodel.DialogUtilities;
import datamodel.Datasource;
import datamodel.Roster;
import datamodel.Team;

public class WelcomeTabController {

    @FXML
    private GridPane mainGridPane;

    @FXML
    private ComboBox<Roster> rosterComboBox;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Button submitButton;

    @FXML
    private TextField teamNameTextField;

    private MainWindowController mainWindowController;


    public void initialize() {
        rosterComboBox.setItems(Datasource.getInstance().getRosterData());

        // prevents naming spaces
        teamNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().isEmpty()) {
                    teamNameTextField.setText("");
                }
            }
        });

        // change max spinner value depending on roster selected
        NewTeamController.setMaxSpinner(spinner, rosterComboBox);

        submitButton.disableProperty().bind(
                teamNameTextField.textProperty().isEmpty()
                        .or(rosterComboBox.valueProperty().isNull())
        );
    }

    // shows dialog box for to create new roster
    @FXML
    public void showNewRosterDialog() {
        Roster newRoster = DialogUtilities.showRosterDialog(mainGridPane);
        if (newRoster != null) {
            Datasource.getInstance().insertRosterFull(newRoster);
            rosterComboBox.getSelectionModel().select(newRoster);
        }
    }

    // creates new team & tab
    @FXML
    public void handleSubmit() {
        String teamName = teamNameTextField.getText();
        Roster roster = rosterComboBox.getValue();
        int startWeek = spinner.getValue();
        Team newTeam = new Team(teamName, startWeek, roster);
        Datasource.getInstance().insertTeam(newTeam);
        mainWindowController.createTeamTab(newTeam);
    }

    public void setMainWindowController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

}
