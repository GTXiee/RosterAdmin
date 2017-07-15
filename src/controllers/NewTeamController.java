package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import datamodel.DialogUtilities;
import datamodel.Datasource;
import datamodel.Roster;
import datamodel.Team;

public class NewTeamController {
    private Team teamToEdit;

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

    private TeamListTabController teamListTabController;

    public void initialize() {
        rosterComboBox.setItems(Datasource.getInstance().getRosterData());

        // prevents user from setting spaces as the team name
        teamNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().isEmpty()) {
                    teamNameTextField.setText("");
                }
            }
        });

        // change max spinner value depending on roster selected
        setMaxSpinner(spinner, rosterComboBox);

        // disable button unless fields are there
        submitButton.disableProperty().bind(
                rosterComboBox.valueProperty().isNull()
                        .or(teamNameTextField.textProperty().isEmpty())
        );
    }

    // for the 'new roster' button, shows the 'new roster' dialog
    @FXML
    public void showNewRosterDialog() {
        Roster newRoster = DialogUtilities.showRosterDialog(mainGridPane);
        if (newRoster != null) {
            Datasource.getInstance().insertRosterFull(newRoster);
            rosterComboBox.getSelectionModel().select(newRoster);
        }
    }

    // creates team from fields, inserts or updates depending on editing or not
    @FXML
    public void handleSubmit() {
        String teamName = teamNameTextField.getText();
        Roster roster = rosterComboBox.getValue();
        int startWeek = spinner.getValue();
        if (teamToEdit == null) {
            Team newTeam = new Team(teamName, startWeek, roster);
            Datasource.getInstance().insertTeam(newTeam);
            teamListTabController.selectTeam(newTeam);
        } else {
            Datasource.getInstance().updateTeam(teamToEdit, teamName, startWeek, roster);
        }
        Stage stage = (Stage) mainGridPane.getScene().getWindow();
        stage.close();


    }

    public void setTeamListTabController(TeamListTabController controller) {
        this.teamListTabController = controller;
    }

    // sets up the fields for the team to be edited
    public void editTeam(Team team) {
        this.teamToEdit = team;
        rosterComboBox.getSelectionModel().select(team.getRoster());
        spinner.getValueFactory().setValue(team.getStartWeek());
        teamNameTextField.setText(team.getTeamName());
    }

    // function to change max spinner value depending on roster selected
    public static void setMaxSpinner(Spinner spinner, ComboBox<Roster> rosterComboBox) {
        rosterComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Roster>() {
            @Override
            public void changed(ObservableValue<? extends Roster> observable, Roster oldValue, Roster newValue) {
                SpinnerValueFactory.IntegerSpinnerValueFactory svf = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();
                int max = 1;
                if (newValue != null) {
                    max = rosterComboBox.getSelectionModel().getSelectedItem().getWeeks().size();
                }
                svf.setMax(max);
            }
        });
    }
}
