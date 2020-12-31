package MoohanLee000356249;

import MoohanLee000356249.util.IDGenerator;
import MoohanLee000356249.util.InHouseValidator;
import MoohanLee000356249.util.OutsourcedValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FUTURE ENHANCEMENT real-time form input feedback validation
 * <p>
 * Controller class for PartForm.fxml
 */
public class PartFormController
{
    @FXML
    private Label partFormName;
    @FXML
    private Label machineOrCompanyLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField machineOrCompanyField;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private Button partSaveButton;
    @FXML
    private Button partCancelButton;

    private static char mode;
    private static Part selectedPart;

    /**
     * optional method that runs after the FXML file has loaded.
     */
    public void initialize()
    {
    }

    /**
     * Event listener for the cancel button. Cancel button clears the form and closes the current window
     */
    public void partCancelButtonListener()
    {
        clearForm();
        Stage partStage = (Stage) partCancelButton.getScene().getWindow();
        partStage.close();
    }

    /**
     * Event listener for radio buttons. Adjusts the form title and field labels depending on the radio button.
     */
    public void partRadioButtonListener()
    {
        inHouseOrOutsourced();
    }

    /**
     * RUNTIME ERROR IndexOutOfBounds thrown by Inventory.updatePart(). Fixed by ensuring selectedPart exists when in
     * 'm' (Modify) mode.
     * <p>
     * Event listener for the save button. Determines if the part is in-house or outsourced. Validates user input for
     * valid data types and correct inventory numbers. depending on the mode, adds or replaces the part in the Inventory
     * Class and closes the window.
     */
    public void partSaveButtonListener()
    {
        boolean isInHouse = inHouseRadio.isSelected();
        Part newPart;
        if (isInHouse) {
            newPart = new InHouseValidator(idField.getText(),
                                           nameField.getText(),
                                           priceField.getText(),
                                           inventoryField.getText(),
                                           minField.getText(),
                                           maxField.getText(),
                                           machineOrCompanyField.getText()).generate();
        } else {
            newPart = new OutsourcedValidator(idField.getText(),
                                              nameField.getText(),
                                              priceField.getText(),
                                              inventoryField.getText(),
                                              minField.getText(),
                                              maxField.getText(),
                                              machineOrCompanyField.getText()).generate();
        }
        if (newPart != null) {
            if (mode == 'a') {
                Inventory.addPart(newPart);
            } else if (mode == 'm') {
                int inventoryIndex = Inventory.getAllParts().indexOf(selectedPart);
                Inventory.updatePart(inventoryIndex, newPart);
                selectedPart = newPart;
            }
            Stage partStage = (Stage) partSaveButton.getScene().getWindow();
            partStage.close();
        }
    }

    /**
     * Checks the currently selected radio button to determine the part type, and adjusts the form's labels
     */
    public void inHouseOrOutsourced()
    {
        if (inHouseRadio.isSelected()) {
            machineOrCompanyLabel.setText("Machine ID");
        } else if (outsourcedRadio.isSelected()) {
            machineOrCompanyLabel.setText("Company Name");
        }
    }

    /**
     * sets the id field of the form using the IDGenerator class
     */
    public void setIdField()
    {
        this.idField.setText(String.valueOf(IDGenerator.generateId()));
    }

    /**
     * overloaded method to set the id field of the form via given int
     *
     * @param id id number to set
     */
    public void setIdField(int id)
    {
        this.idField.setText(String.valueOf(id));
    }

    /**
     * set the form mode to add/modify part
     *
     * @param m 'a' for add, 'm' for modify
     */
    public void setMode(char m)
    {
        mode = m;
    }

    /**
     * static attribute to keep track of the selected part.
     *
     * @param p the selected part
     */
    public void setSelectedPart(Part p)
    {
        selectedPart = p;
    }

    /**
     * clears all fields in the form
     */
    public void clearForm()
    {
        idField.clear();
        nameField.clear();
        inventoryField.clear();
        priceField.clear();
        maxField.clear();
        minField.clear();
        machineOrCompanyField.clear();

    }

    /**
     * renders the form prior to showing the user. auto populates the Id field for 'Add' mode auto populates the part
     * information for 'Modify' mode. Adjusts radio button depending on the instance type of the part.
     */
    public void prerenderForm()
    {
        if (mode == 'a') {
            partFormName.setText("Add Part");
            clearForm();
            this.setIdField();

        } else if (mode == 'm') {
            partFormName.setText("Modify Part");
            try {
                setIdField(selectedPart.getId());
                nameField.setText(selectedPart.getName());
                inventoryField.setText(String.valueOf(selectedPart.getStock()));
                priceField.setText(String.valueOf(selectedPart.getPrice()));
                maxField.setText(String.valueOf(selectedPart.getMax()));
                minField.setText(String.valueOf(selectedPart.getMin()));
                if (selectedPart instanceof InHouse) {
                    inHouseRadio.setSelected(true);
                    InHouse selectedInHouse = (InHouse) selectedPart;
                    machineOrCompanyField.setText(String.valueOf(selectedInHouse.getMachineId()));
                } else if (selectedPart instanceof Outsourced) {
                    outsourcedRadio.setSelected(true);
                    Outsourced selectedOutsourced = (Outsourced) selectedPart;
                    machineOrCompanyField.setText(selectedOutsourced.getCompanyName());
                }
            }
            catch (NullPointerException exception) {
                System.out.println("No Part selected");
            }
        }
        inHouseOrOutsourced();
    }


}