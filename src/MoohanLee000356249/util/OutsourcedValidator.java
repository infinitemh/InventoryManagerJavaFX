package MoohanLee000356249.util;

import MoohanLee000356249.Outsourced;

/**
 * Class to validate the InHouse Part Form
 */
public class OutsourcedValidator extends BaseValidator
{
    private final String companyName;

    /**
     * Constructor method
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     * @param cName the company name
     */
    public OutsourcedValidator(String id, String name, String price, String stock, String min, String max, String cName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = cName;
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
        boolean valid = (validateId() && validateName() && validateInventory() && validatePrice() && validateCompanyName());
        if (valid) {
            return true;
        } else {
            new SimpleDialog("Error", "Invalid Input", errorString).displayDialog();
            return false;
        }
    }

    /**
     * Generates and return an Outsourced object if inputs are valid
     * <p>
     * otherwise, returns null
     *
     * @return input-validated Outsourced object.
     */
    public Outsourced generate()
    {
        if (this.isValid()) {
            return new Outsourced(Integer.parseInt(this.id),
                                  name,
                                  Double.parseDouble(this.price),
                                  Integer.parseInt(this.stock),
                                  Integer.parseInt(this.min),
                                  Integer.parseInt(this.max),
                                  this.companyName);

        } else {
            return null;
        }
    }

    /**
     * Validates the company name
     *
     * @return boolean representing valid company name
     */
    public boolean validateCompanyName()
    {
        if (notEmptyOrNullString(this.companyName)) {
            return true;
        } else {
            errorString += "Please enter the outsourced company name.\n";
            return false;
        }
    }

}
