package MoohanLee000356249.util;

import MoohanLee000356249.Product;

/**
 * Class to validate the Product Part Form
 */
public class ProductValidator extends BaseValidator
{
    /**
     * Constructor method
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */
    public ProductValidator(String id, String name, String price, String stock, String min, String max)
    {
        super(id, name, price, stock, min, max);
    }

    /**
     * Generates and return Product object if inputs are valid
     * <p>
     * otherwise, returns null
     *
     * @return input-validated Product object.
     */
    public Product generate()
    {
        if (this.isValid()) {
            return new Product(Integer.parseInt(this.id),
                               name,
                               Double.parseDouble(this.price),
                               Integer.parseInt(this.stock),
                               Integer.parseInt(this.min),
                               Integer.parseInt(this.max));
        } else {
            return null;
        }
    }

}
