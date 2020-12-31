package MoohanLee000356249;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * Inventory class utilizes its static attributes to maintain information about parts and products
 */
public class Inventory
{
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * append a part to allParts
     *
     * @param newPart new part to add
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * append a product to allProducts
     *
     * @param newProduct new product to add
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * method to return Part object by partId
     *
     * @param partId an identifying integer for a Part object
     *
     * @return Part object
     */
    public static Part lookupPart(int partId)
    {
        Part existing = null;
        for (Part p : allParts) {
            if (p.getId() == partId) {
                existing = p;
                break;
            }
        }
        return existing;
    }

    /**
     * Overloaded method to return Part by partName
     *
     * @param partName a String object used for query
     *
     * @return Part object
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> matches = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (p.getName().toLowerCase(Locale.ENGLISH).contains(partName.toLowerCase(Locale.ENGLISH))) {
                matches.addAll(p);
            }
        }
        return matches;
    }

    /**
     * method to return Product object by productId
     *
     * @param productId an identifying integer for a Product object
     *
     * @return Product object
     */
    public static Product lookupProduct(int productId)
    {
        Product existing = null;
        for (Product p : allProducts) {
            if (p.getId() == productId) {
                existing = p;
                break;
            }
        }
        return existing;
    }

    /**
     * overloaded method to return Part object by partName:String
     *
     * @param productName a String object used for query
     *
     * @return Part object
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {

        ObservableList<Product> matches = FXCollections.observableArrayList();
        for (Product p : allProducts) {
            if (p.getName().toLowerCase(Locale.ENGLISH).contains(productName.toLowerCase(Locale.ENGLISH))) {
                matches.addAll(p);
            }
        }
        return matches;
    }

    /**
     * Replace a part in allParts by index
     *
     * @param index        index of the part to replace
     * @param selectedPart new part to replace with
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * Replace a product in allProducts by index
     *
     * @param index      index of the product to replace
     * @param newProduct new product to replace with
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Delete a part from allParts. Returns true if allParts has been modified from the method call.
     *
     * @param selectedPart part to delete
     *
     * @return boolean representing successful deletion
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.removeAll(selectedPart);
    }

    /**
     * Delete a product from allProducts. Returns true if allProducts has been modified from the method call.
     *
     * @param selectedProduct product to delete
     *
     * @return boolean representing successful deletion
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.removeAll(selectedProduct);
    }

    /**
     * get an ObservableList of all Parts
     *
     * @return ObservableList of all Parts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * get an ObservableList of all Products
     *
     * @return ObservableList of all Products
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;

    }
}
