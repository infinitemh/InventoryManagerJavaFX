package MoohanLee000356249.util;

/**
 * Convenience class to generate sequential numbers for assigning ID numbers
 */
public class IDGenerator
{
    private static int id = 0;

    /**
     * returns the next number to be used as an id
     *
     * @return id number
     */
    public static int generateId()
    {
        id += 1;
        return id;
    }
}
