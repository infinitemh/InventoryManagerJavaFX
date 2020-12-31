package MoohanLee000356249.util;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Convenience class to display an ad-hoc dialog box
 */
public class SimpleDialog extends Dialog<String>
{
    private final String title, header, content;

    /**
     * Constructor
     *
     * @param title   title string
     * @param header  header string
     * @param content content string
     */
    public SimpleDialog(String title, String header, String content)
    {
        super();
        this.title = title;
        this.header = header;
        this.content = content;
    }

    /**
     * displays a dialog with 1 OK button to close. the strings passed to the constructor are used to populate the
     * dialog box.
     */
    public void displayDialog()
    {
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(content);
        ButtonType okDoneButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(okDoneButtonType);
        this.showAndWait();
    }
}
