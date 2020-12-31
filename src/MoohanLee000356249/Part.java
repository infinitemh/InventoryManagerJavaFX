/**
 * Supplied class Part.java
 */

/**
 *
 * @author Moohan Lee Studen ID# 000356249
 * */

package MoohanLee000356249;

/**
 * Abstract class extended by InHouse and Outsourced Classes
 */
public abstract class Part
{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor function
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part's current inventory amount
     * @param min the part's minimum inventory amount
     * @param max the part's maximum inventory amount
     */
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max)
    {
        this.max = max;
    }

}