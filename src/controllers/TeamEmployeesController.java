package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import datamodel.Datasource;
import datamodel.Employee;
import datamodel.Team;

public class TeamEmployeesController {
    private Team team;

    private Employee employeeToEdit;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private RadioButton fullTimeRadioButton;

    @FXML
    private RadioButton partTimeRadioButton;

    @FXML
    private TextField employeeNameTextField;

    @FXML
    private TextField totalHoursTextField;

    @FXML
    private TextField saturdayHoursTextField;

    @FXML
    private TextField sundayHoursTextField;

    @FXML
    private TextField publicHoursTextField;

    @FXML
    private TextField publicHolidayCreditTextField;

    @FXML
    private TextField shiftChangesTextField;

    @FXML
    private Button submitButton;

    private ToggleGroup toggleGroup = new ToggleGroup();

    public void setTeam(Team team) {
        this.team = team;
    }

    public void initialize() {
        fullTimeRadioButton.setToggleGroup(toggleGroup);
        partTimeRadioButton.setToggleGroup(toggleGroup);

        // disables button if fields are empty
        submitButton.disableProperty().bind(employeeNameTextField.textProperty().isEmpty()
                .or(totalHoursTextField.textProperty().isEmpty()
                        .or(saturdayHoursTextField.textProperty().isEmpty())
                        .or(sundayHoursTextField.textProperty().isEmpty()
                                .or(publicHoursTextField.textProperty().isEmpty()
                                        .or(toggleGroup.selectedToggleProperty().isNull())
                                        .or(publicHolidayCreditTextField.textProperty().isEmpty())
                                        .or(shiftChangesTextField.textProperty().isEmpty())))));


    }

    // handles submit button depending on editing or new
    @FXML
    public void handleSubmit() {
        try {
            String employeeName = employeeNameTextField.getText();
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            Boolean isFullTime = selectedRadioButton.getText().equals(fullTimeRadioButton.getText());
            int totalHours = Integer.parseInt(totalHoursTextField.getText());
            int saturdayHours = Integer.parseInt(saturdayHoursTextField.getText());
            int sundayHours = Integer.parseInt(sundayHoursTextField.getText());
            int publicHolidayHours = Integer.parseInt(publicHoursTextField.getText());
            int publicHolidayCredit = Integer.parseInt(publicHolidayCreditTextField.getText());
            int shiftChanges = Integer.parseInt(shiftChangesTextField.getText());

            if (employeeToEdit == null) {
                Employee newEmployee = new Employee(employeeName, this.team, isFullTime, totalHours,
                        saturdayHours, sundayHours, publicHolidayHours, publicHolidayCredit, shiftChanges);

                Datasource.getInstance().insertEmployee(newEmployee);
            } else {
                Datasource.getInstance().updateEmployee(employeeToEdit, employeeName, isFullTime, totalHours,
                        saturdayHours, sundayHours, publicHolidayHours, publicHolidayCredit, shiftChanges);
            }

            Stage stage = (Stage) mainGridPane.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number Format Error");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure numbers are used to denote hours");
            alert.showAndWait();
        }


    }

    // sets up fields ready to be edited
    public void editEmployee(Employee employee) {
        this.employeeToEdit = employee;
        employeeNameTextField.setText(employee.getName());
        if (employee.isIsFullTime()) {
            fullTimeRadioButton.fire();
        } else {
            partTimeRadioButton.fire();
        }
        totalHoursTextField.setText(Integer.toString(employee.getTotalHours()));
        saturdayHoursTextField.setText(Integer.toString(employee.getSaturdayHours()));
        sundayHoursTextField.setText(Integer.toString(employee.getSundayHours()));
        publicHoursTextField.setText(Integer.toString(employee.getPhHours()));
        publicHolidayCreditTextField.setText(Integer.toString(employee.getPhCredit()));
        shiftChangesTextField.setText(Integer.toString(employee.getShiftChanges()));
    }


}
