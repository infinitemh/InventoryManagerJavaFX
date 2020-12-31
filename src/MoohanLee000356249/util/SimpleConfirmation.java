package MoohanLee000356249.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * Convenience class to display a confirmation message and return the response in boolean format.
 */
public class SimpleConfirmation
{
    /**
     * Display an alert dialog message and prompts the user to confirm their intentions
     *
     * @return boolean representing user's confirmation
     */
    public boolean confirm()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure you'd like the remove the selected item?",
                                ButtonType.CANCEL,
                                ButtonType.YES);
        Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        yesButton.setDefaultButton(false);
        Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        noButton.setDefaultButton(true);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }
}
