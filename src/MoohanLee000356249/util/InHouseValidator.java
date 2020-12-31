package MoohanLee000356249.util;

import MoohanLee000356249.InHouse;

/**
 * Class to validate the InHouse Part Form
 */
public class InHouseValidator extends BaseValidator
{
    private final String machineId;

    /**
     * Constructor method
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     * @param mId   the machine ID
     */
    public InHouseValidator(String id, String name, String price, String stock, String min, String max, String mId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = mId;
    }

    /**
     * Validates user entered field values
     * <p>
     * If not valid, displays a dialog message containing the errors.
     *
     * @return boolean indicating valid object
     */
    @Override
    public boolean isValid()
    {
        boolean valid = (validateId() && validateName() && validateInventory() && validatePrice() && validateMachineId());
        if (valid) {
            return true;
        } else {
            new SimpleDialog("Error", "Invalid Input", errorString).displayDialog();
            return false;
        }
    }

    /**
     * Generates and return an InHouse object if inputs are valid
     * <p>
     * otherwise, returns null
     *
     * @return input-validated InHouse object.
     */
    public InHouse generate()
    {
        if (this.isValid()) {
            return new InHouse(Integer.parseInt(this.id),
                               name,
                               Double.parseDouble(this.price),
                               Integer.parseInt(this.stock),
                               Integer.parseInt(this.min),
                               Integer.parseInt(this.max),
                               Integer.parseInt(this.machineId));

        } else {
            return null;
        }
    }

    /**
     * Validates the machine ID
     *
     * @return boolean representing valid machine ID
     */
    public boolean validateMachineId()
    {
        if (isPositiveIntString(this.machineId)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the machine ID.\n";
            return false;
        }
    }

}
