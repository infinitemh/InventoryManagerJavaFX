package MoohanLee000356249;

/**
 * InHouse class extends the Part class and includes the machineId attribute.
 */
public class InHouse extends Part
{

    private int machineId;

    /**
     * Constructor method
     *
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param stock     current inventory amount
     * @param min       minimum inventory amount
     * @param max       maximum inventory amount
     * @param machineId number representing the machine
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.setMachineId(machineId);
    }

    /**
     * sets the machine Id
     *
     * @param machineId the machine Id
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * returns the machine Id
     *
     * @return the machine Id
     */
    public int getMachineId()
    {
        return this.machineId;

    }
}
