package MoohanLee000356249.util;

/**
 * Base validator class. superclass to in-house, outsourced, and product validators
 */
public abstract class BaseValidator
{
    protected final String id, name, price, stock, min, max;
    protected String errorString = "";

    /**
     * Constructor function
     *
     * @param id    the part id
     * @param name  the part name
     * @param price the part price
     * @param stock the part's current inventory amount
     * @param min   the part's minimum inventory amount
     * @param max   the part's maximum inventory amount
     */
    public BaseValidator(String id, String name, String price, String stock, String min, String max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Validates user entered field values
     * <p>
     * If not valid, displays a dialog message containing the errors.
     *
     * @return boolean indicating valid object
     */
    public boolean isValid()
    {
        boolean valid = (validateId() && validateName() && validateInventory() && validatePrice());
        if (valid) {
            return true;
        } else {
            new SimpleDialog("Error", "Invalid Input", errorString).displayDialog();
            return false;
        }
    }

    /**
     * checks if given string is not null or empty
     *
     * @param str the string to validate
     *
     * @return boolean indicating valid string.
     */
    public boolean notEmptyOrNullString(String str)
    {
        return (str != null && !(str.equals("")));
    }

    /**
     * check if string consists of positive integer characters
     *
     * @param str string to check
     *
     * @return boolean indicating string can be cast as an integer
     */
    public boolean isPositiveIntString(String str)
    {
        try {
            int testInt = Integer.parseInt(str);
            return (testInt >= 0);
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * check if string consists of positive decimal number characters
     *
     * @param str string to check
     *
     * @return boolean indicating double string
     */
    public boolean isPositiveDoubleString(String str)
    {
        try {
            double testDouble = Double.parseDouble(str);
            return testDouble >= 0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * validate the id
     *
     * @return boolean representing valid id
     */
    public boolean validateId()
    {
        if (isPositiveIntString(this.id)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the ID.\n";
            return false;
        }
    }

    /**
     * validate the name
     *
     * @return boolean representing valid name
     */
    public boolean validateName()
    {
        if (notEmptyOrNullString(this.name)) {
            return true;
        } else {
            errorString += "Please enter the name.\n";
            return false;
        }
    }

    /**
     * validate the price
     *
     * @return boolean representing valid price
     */
    public boolean validatePrice()
    {
        if (isPositiveDoubleString(this.price)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the price.\n";
            return false;
        }
    }

    /**
     * validate the stock
     *
     * @return boolean representing valid stock
     */
    public boolean validateStock()
    {
        if (isPositiveIntString(this.stock)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the inventory.\n";
            return false;
        }
    }

    /**
     * validate the min
     *
     * @return boolean representing valid min
     */
    public boolean validateMin()
    {
        if (isPositiveIntString(this.min)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the minimum\n";
            return false;
        }
    }

    /**
     * validate the max
     *
     * @return boolean representing valid max
     */
    public boolean validateMax()
    {
        if (isPositiveIntString(this.max)) {
            return true;
        } else {
            errorString += "Please enter a non-negative number as the maximum.\n";
            return false;
        }
    }

    /**
     * Checks if stock doesn't violate min or max values, and that the min value is less than the max value
     *
     * @return true for valid inventory numbers
     */
    public boolean validateInventory()
    {
        boolean validInputs = (validateStock() && validateMax() && validateMin());

        if (validInputs) {
            int intStock = Integer.parseInt(this.stock);
            int intMin = Integer.parseInt(this.min);
            int intMax = Integer.parseInt(this.max);

            if (intMin >= intMax) {
                errorString += "Max value should be greater than Min value.\n";
                return false;
            } else if (intStock > intMax) {
                errorString += "Inventory should not exceed the Max value.\n";
                return false;
            } else if (intStock < intMin) {
                errorString += "Inventory should not be lower than the Min value.\n";
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
