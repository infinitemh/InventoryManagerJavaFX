package MoohanLee000356249;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class representing product, which keeps track of all associated parts.
 */
public class Product
{
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    /**
     * Constructor.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the current inventory amount
     * @param min   the minimum inventory amount
     * @param max   the maxmimum inventory amount
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * sets the id of the product
     *
     * @param id the id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * sets the name of the product
     *
     * @param name the product name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * sets the price of the product
     *
     * @param price the product price
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * sets the current amount the products in stock
     *
     * @param stock the product amount
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * sets the minimum amount of products in stock
     *
     * @param min the minimum amount
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * sets the maximum amount of products in stock
     *
     * @param max the maximum amount
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * returns the product's id
     *
     * @return the product's id
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * returns the product's name
     *
     * @return the product's name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * return's the product's price
     *
     * @return the product's price
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * returns the current amount of products in stock
     *
     * @return amount of products
     */
    public int getStock()
    {
        return this.stock;
    }

    /**
     * returns the minimum amount of products in stock
     *
     * @return the minimum allowed amount
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * returns the maximum amount of products in stock
     *
     * @return the maximum amount
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * appends the product's associated part to associatedParts
     *
     * @param part the associated part
     */
    public void addAssociatedPart(Part part)
    {
        this.associatedParts.add(part);
    }

    /**
     * deletes the product's associated part from associatedParts
     *
     * @param selectedAssociatedPart the associated part to delete
     *
     * @return boolean value indicating successful deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        try {
            this.associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * returns all associated parts for the product
     *
     * @return all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return this.associatedParts;
    }
}
