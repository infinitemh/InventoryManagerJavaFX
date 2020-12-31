package MoohanLee000356249;

import MoohanLee000356249.util.IDGenerator;
import MoohanLee000356249.util.ProductValidator;
import MoohanLee000356249.util.SimpleConfirmation;
import MoohanLee000356249.util.SimpleDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FUTURE ENHANCEMENT real-time form input feedback validation
 * <p>
 * Controller class for ProductForm.fxml
 */
public class ProductFormController
{
    @FXML
    private Label productFormName;
    @FXML
    private TextField partSearchBar;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private Button productSaveButton;
    @FXML
    private Button productCancelButton;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    private static char mode;
    private static Product selectedProduct;
    private static Part selectedPart;
    private static Part selectedAssociatedPart;
    private static final ObservableList<Part> ephemeralParts = FXCollections.observableArrayList();

    /**
     * optional method that runs after the fxml file is finished loading.
     */
    public void initialize()
    {
        setupTable();
    }

    /**
     * event listener for the cancel button. clears the form and closes the window.
     */
    public void productCancelButtonListener()
    {
        clearForm();
        Stage productStage = (Stage) productCancelButton.getScene().getWindow();
        productStage.close();
    }

    /**
     * RUNTIME ERROR - saving without changes still caused an IndexOutOfBoundsException as the object address was
     * changed(caused by updating with an empty ObservableList). fixed by assigning newProduct to selectedProduct after
     * each invocation.
     * <p>
     * event listener for the save button. validates the user input, creates a product with the associated parts, and
     * updates Inventory.
     */
    public void productSaveButtonListener()
    {
        Product newProduct = new ProductValidator(idField.getText(),
                                                  nameField.getText(),
                                                  priceField.getText(),
                                                  inventoryField.getText(),
                                                  minField.getText(),
                                                  maxField.getText()).generate();
        if (newProduct != null) {
            for (Part p : ephemeralParts) {
                newProduct.addAssociatedPart(p);
            }
            if (mode == 'a') {
                Inventory.addProduct(newProduct);
            } else if (mode == 'm') {
                int inventoryIndex = Inventory.getAllProducts().indexOf(selectedProduct);
                Inventory.updateProduct(inventoryIndex, newProduct);
                selectedProduct = newProduct;
            }
            clearForm();
            Stage productStage = (Stage) productSaveButton.getScene().getWindow();
            productStage.close();
        }
    }

    /**
     * event listener for the 'Add' button in the part table. adds selectedPart to ephemeralParts to be eventually added
     * to the Product object.
     */
    public void partAddButtonListener()
    {
        if (selectedPart == null) {
            new SimpleDialog("No Part Selected",
                             "No Part Selected",
                             "Please select a part to associate with the product").displayDialog();
        } else {
            ephemeralParts.addAll(selectedPart);
        }
    }

    /**
     * event listener for the 'Remove associated part' button. removes selectedAssociatedPart from ephemeralParts before
     * ephemeralParts is added to the Product object.
     */
    public void partRemoveButtonListener()
    {
        associatedPartSelectListener();
        if (selectedAssociatedPart == null) {
            new SimpleDialog("No Part Selected", "No Part Selected", "Please select a part to remove.").displayDialog();
        } else {
            if (new SimpleConfirmation().confirm()) {
                boolean deleted = false;
                deleted = ephemeralParts.removeAll(selectedAssociatedPart);
                if (deleted) {
                    associatedPartTableView.getItems().remove(selectedAssociatedPart);
                } else {
                    new SimpleDialog("Remove failed", "Failed to remove.", "Remove was unsuccessful.").displayDialog();
                }
            }
        }
    }

    /**
     * RUNTIME ERROR - Clicking on an empty row caused an IndexOutOfBoundsException thrown by getSelectedItem().
     * Implemented a check for an empty selection.
     * <p>
     * event listener to detect selection of a part from the part table
     */
    public void partSelectListener()
    {
        boolean validSelection = !(partTableView.getSelectionModel().isEmpty());
        if (validSelection) {
            selectedPart = partTableView.getSelectionModel().getSelectedItem();
        } else {
            selectedPart = null;
        }
    }

    /**
     * event listener to detect selection of an associatedPart from the associatedParts table.
     */
    public void associatedPartSelectListener()
    {
        boolean validSelection = !(associatedPartTableView.getSelectionModel().isEmpty());
        if (validSelection) {
            selectedAssociatedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        } else {
            selectedAssociatedPart = null;
        }
    }

    /**
     * Listens for the enter key in the parts searchbar. partName could potentially be a String of number characters.
     * parts are searched by String first. if the string can be parsed into an int, search for partId as well, and
     * append to the initial results
     */
    public void partSearchBarListener()
    {
        ObservableList<Part> results;
        String queryString = partSearchBar.getText();
        if (queryString.equals("")) {
            partTableView.setItems(Inventory.getAllParts());
        } else {
            results = Inventory.lookupPart(queryString);
            if (isPositiveNumberString(queryString)) {
                int queryInt = Integer.parseInt(queryString);
                Part intQueryResult = Inventory.lookupPart(queryInt);
                if (intQueryResult != null) {
                    results.add(Inventory.lookupPart(queryInt));
                }
            }
            partTableView.setItems(results);
            if (results.size() == 0) {
                new SimpleDialog("No Result", "Not Found", "No such part.").displayDialog();
                partSearchBar.setText("");
                partTableView.setItems(Inventory.getAllParts());
            } else if (results.size() == 1) {
                partTableView.getSelectionModel().clearAndSelect(0);
                partTableView.getFocusModel().focus(0);
                partSelectListener();
            }
        }
    }

    /**
     * check if string consists of number characters
     *
     * @param str string to check
     *
     * @return boolean indicating number string
     */
    public boolean isPositiveNumberString(String str)
    {
        try {
            int testInt = Integer.parseInt(str);
            return testInt >= 0;
        }
        catch (NumberFormatException exception) {
            try {
                double testDouble = Double.parseDouble(str);
                return testDouble >= 0;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
    }

    /**
     * sets the product id field via IDGenerator class
     */
    public void setIdField()
    {
        this.idField.setText(String.valueOf(IDGenerator.generateId()));
    }

    /**
     * overloaded method to set the product id via passed in value
     *
     * @param id product id to set
     */
    public void setIdField(int id)
    {
        this.idField.setText(String.valueOf(id));
    }

    /**
     * sets the form submission mode
     *
     * @param m 'a' for Add, 'm' for Modify
     */
    public void setMode(char m)
    {
        mode = m;
    }

    /**
     * keeps track of the user selected product via static attribute selectedProduct
     *
     * @param p selected product
     */
    public void setSelectedProduct(Product p)
    {
        selectedProduct = p;
    }

    /**
     * keeps track of the user selected part via static attribute selectedPart
     *
     * @param p selected part
     */
    public void setSelectedPart(Part p)
    {
        selectedPart = p;
    }

    /**
     * keeps track of the user selected associatedPart via static attribute selectedAssociatedPart
     *
     * @param p selected associatedPart
     */
    public void setSelectedAssociatedPart(Part p)
    {
        selectedAssociatedPart = p;
    }

    /**
     * clear all fields in the form, as well as the associatedParts table.
     */
    public void clearForm()
    {
        idField.clear();
        nameField.clear();
        inventoryField.clear();
        priceField.clear();
        maxField.clear();
        minField.clear();
        ephemeralParts.clear();
    }

    /**
     * pre-renders the form prior to user input. Adjusts form labels and field values depending on the current mode.
     */
    public void prerenderForm()
    {
        if (mode == 'a') {
            productFormName.setText("Add Product");
            clearForm();
            this.setIdField();
            selectedProduct = null;
        } else if (mode == 'm') {
            productFormName.setText("Modify Product");
            setIdField(selectedProduct.getId());
            nameField.setText(selectedProduct.getName());
            inventoryField.setText(String.valueOf(selectedProduct.getStock()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
            maxField.setText(String.valueOf(selectedProduct.getMax()));
            minField.setText(String.valueOf(selectedProduct.getMin()));
            ephemeralParts.addAll(selectedProduct.getAllAssociatedParts());
        }
    }

    /**
     * set up the parts and associatedParts table. configure columns to the correct data type properties.
     * TableView.setItems() allows the TableView to bind to its data and update changes dynamically.
     */
    public void setupTable()
    {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        partTableView.setItems(Inventory.getAllParts());
        associatedPartTableView.setItems(ephemeralParts);
    }
}
