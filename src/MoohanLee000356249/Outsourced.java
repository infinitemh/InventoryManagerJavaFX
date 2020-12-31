package MoohanLee000356249;

/**
 * Outsourced class extends the Part class and includes the companyName attribute.
 */
public class Outsourced extends Part
{
    private String companyName;

    /**
     * Constructor
     *
     * @param id          the id
     * @param name        the part name
     * @param price       the part price
     * @param stock       number of parts in inventory
     * @param min         minimum number of parts in inventory
     * @param max         maximum number of parts in inventory
     * @param companyName Outsourced part's company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.setCompanyName(companyName);
    }

    /**
     * sets the company name
     *
     * @param companyName the company's name
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * returns the company name
     *
     * @return the company name
     */
    public String getCompanyName()
    {
        return this.companyName;
    }
}
