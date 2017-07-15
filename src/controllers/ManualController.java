package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ManualController {
    @FXML
    private TextArea textArea;

    public static final String MANUAL_TEXT =
            "1. Using the Welcome tab, set up your Team and corresponding Roster. The start week defines which week of the roster that your team begins with. These can later be edited in the Rosters and Teams tabs.\n\n" +
                    "2. A new 'Team Tab' will open, and using the button in the bottom left you can proceed to create a set of targets for each member of the team. These can be changed later if you are unsure.\n\n" +
                    "3. Now you can select the the team member from the list, and click the save button to save the file with the targets at the desired location.\n\n" +
                    "4. Email this file to the team member it belongs to and they will be able load it into the time management program at their end to ensure they are reaching their targets.\n\n";

    public void initialize() {
        textArea.setText(MANUAL_TEXT);
    }
}
