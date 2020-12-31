package MoohanLee000356249;

import MoohanLee000356249.util.IDGenerator;
import MoohanLee000356249.util.SimpleConfirmation;
import MoohanLee000356249.util.SimpleDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FUTURE ENHANCEMENT more sample data
 * <p>
 * Controller class for Main.fxml
 */
public class MainController
{
    @FXML
    private TextField partSearchBar;
    @FXML
    private TextField productSearchBar;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableView<Product> productTableView;
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

    private static Part selectedPart;
    private static Product selectedProduct;
    private static boolean sampleDataCreated = false;

    /**
     * optional method which runs after the FXML file has been loaded. maps tables to data, and creates sample data if
     * not created.
     */
    public void initialize()
    {
        setupTable();
        if (!sampleDataCreated) {
            addSampleData();
            sampleDataCreated = true;
        }
    }

    /**
     * Exits the application
     */
    public void exitButtonListener()
    {
        System.exit(0);
    }

    /**
     * Event listener for the 'Add' button in the Parts table.
     *
     * Calls the setMode('a') method in the PartFormController,
     * pre-renders the form, and opens it in a new window.
     *
     * @throws IOException getResource takes a filename String argument
     */
    public void partAddListener() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PartForm.fxml"));
        Parent parent = loader.load();
        PartFormController partFormController = loader.getController();
        partFormController.setMode('a');
        partFormController.prerenderForm();
        Scene mainScene = new Scene(parent);
        Stage partStage = new Stage();
        partStage.setTitle("Software 1 Performance Assessment");
        partStage.setScene(mainScene);
        partStage.show();
    }

    /**
     * Event listener for the 'Modify' button in the Parts table.
     *
     * Calls the setMode('m') method in the
     * PartFormController, pre-renders the form, and opens it in a new window.
     *
     * @throws IOException getResource takes a filename String argument
     */
    public void partModifyListener() throws IOException
    {
        partSelectListener();
        if (selectedPart == null) {
            new SimpleDialog("No Part Selected", "No Part Selected", "Please select a part to modify.").displayDialog();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartForm.fxml"));
            Parent parent = loader.load();
            PartFormController partFormController = loader.getController();
            partFormController.setMode('m');
            partFormController.prerenderForm();
            Scene mainScene = new Scene(parent);
            Stage partStage = new Stage();
            partStage.setTitle("Software 1 Performance Assessment");
            partStage.setScene(mainScene);
            partStage.show();
        }
    }

    /**
     * Event listener for the 'Delete' button in the Parts table. Checks a part is selected
     *
     * @throws IOException potentially thrown from partSelectListener
     */
    public void partDeleteListener() throws IOException
    {
        partSelectListener();
        if (selectedPart == null) {
            new SimpleDialog("Delete failed", "Failed to delete.", "No part was selected.").displayDialog();
        } else {
            if (new SimpleConfirmation().confirm()) {
                boolean deleted = Inventory.deletePart(selectedPart);
                if (deleted) {
                    partTableView.getItems().remove(selectedPart);
                }
            } else {
                new SimpleDialog("Delete failed", "Failed to delete.", "Delete was unsuccessful.").displayDialog();
            }
        }
    }

    /**
     * RUNTIME ERROR - Calling FXMLLoader.getController() without calling FXMLLoader.load() threw a
     * NullPointerException. Fixed by calling FXMLLoader.load()
     * <p>
     * RUNTIME ERROR - Clicking on an empty row caused an IndexOutOfBoundsException thrown by getSelectedItem().
     * Implemented a check for an empty selection.
     *
     * <p>
     * Detects mouseclicks in the Parts table. If the click was on a valid row, calls setSelectedPart from
     * PartFormController
     *
     * @throws IOException String filename arguement for getResource()
     */
    public void partSelectListener() throws IOException
    {
        boolean validSelection = !(partTableView.getSelectionModel().isEmpty());
        if (validSelection) {
            selectedPart = partTableView.getSelectionModel().getSelectedItem();
        } else {
            selectedPart = null;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PartForm.fxml"));
        loader.load();
        PartFormController partFormController = loader.getController();
        partFormController.setSelectedPart(selectedPart);
    }

    /**
     * Listens for the enter key in the parts searchbar. queryString could potentially be a String of number characters.
     * parts are searched by String first. if the string can be parsed into an int, search for partId as well, and
     * append to the initial results
     */
    public void partSearchBarListener() throws IOException
    {
        ObservableList<Part> results;
        String queryString = partSearchBar.getText();
        if (queryString.equals("")) {
            partTableView.setItems(Inventory.getAllParts());
        } else {
            results = Inventory.lookupPart(queryString);
            if (isPositiveIntString(queryString)) {
                int queryInt = Integer.parseInt(queryString);
                Part intQueryResult = Inventory.lookupPart(queryInt);
                if (intQueryResult != null) {
                    results.add(Inventory.lookupPart(queryInt));
                }
            }
            partTableView.setItems(results);
            if (results.size() == 1) {
                partTableView.getSelectionModel().clearAndSelect(0);
                partTableView.getFocusModel().focus(0);
                partSelectListener();
            }
        }
    }

    /**
     * Event listener for the 'ADD' button in the products table. calls the setMode('a') method from the
     * ProductFormController, pre-renders the form, and opens it in a new window.
     *
     * @throws IOException getResource() takes a String filename
     */
    public void productAddListener() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductForm.fxml"));
        Parent parent = loader.load();
        ProductFormController productFormController = loader.getController();
        productFormController.setMode('a');
        productFormController.prerenderForm();
        Scene mainScene = new Scene(parent);
        Stage partStage = new Stage();
        partStage.setTitle("Software 1 Performance Assessment");
        partStage.setScene(mainScene);
        partStage.show();
    }

    /**
     * RUNTIME ERROR - nullPointerException thrown by not checking selectedProduct for null value. Created a conditional
     * statement to gracefully display a dialog box.
     * <p>
     * Event listener for the 'Modify' button in the products table. calls the setMode('m') method from the
     * ProductFormController, pre-renders the form, and opens it in a new window.
     *
     * @throws IOException getResource() takes a String filename
     */
    public void productModifyListener() throws IOException
    {
        productSelectListener();
        if (selectedProduct == null) {
            new SimpleDialog("No Product Selected",
                             "No Product Selected.",
                             "Please select a product to modify.").displayDialog();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductForm.fxml"));
            Parent parent = loader.load();
            ProductFormController productFormController = loader.getController();
            productFormController.setMode('m');
            productFormController.prerenderForm();
            Scene mainScene = new Scene(parent);
            Stage partStage = new Stage();
            partStage.setTitle("Software 1 Performance Assessment");
            partStage.setScene(mainScene);
            partStage.show();
        }
    }

    /**
     * Event listener for the 'Delete' button in the Products table. Checks if a product is selected. If so, the
     * selected product is removed from Inventory, as well as the table
     *
     * @throws IOException potentially thrown by productSelectListener()
     */
    public void productDeleteListener() throws IOException
    {
        productSelectListener();
        if (selectedProduct == null) {
            new SimpleDialog("Delete failed", "Failed to delete.", "No product was selected.").displayDialog();
        } else {
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                new SimpleDialog("Delete failed",
                                 "Failed to delete",
                                 "The product has associated parts. Please remove the associated parts prior to deleting the product.")
                        .displayDialog();
            } else {
                if (new SimpleConfirmation().confirm()) {
                    boolean deleted = Inventory.deleteProduct(selectedProduct);
                    if (deleted) {
                        productTableView.getItems().remove(selectedProduct);
                    } else {
                        new SimpleDialog("Delete failed",
                                         "Failed to delete.",
                                         "Delete was unsuccessful.").displayDialog();
                    }
                }
            }
        }
    }

    /**
     * RUNTIME ERROR - Clicking on an empty row caused an IndexOutOfBoundsException thrown by getSelectedItem().
     * Implemented a check for an empty selection.
     * <p>
     * Event listener that detects a valid row select when a mouse click is detected in the products table.
     *
     * @throws IOException getResource() takes a String filename argument
     */
    public void productSelectListener() throws IOException
    {
        boolean validSelection = !(productTableView.getSelectionModel().isEmpty());
        if (validSelection) {
            selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        } else {
            selectedProduct = null;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductForm.fxml"));
        loader.load();
        ProductFormController productFormController = loader.getController();
        productFormController.setSelectedProduct(selectedProduct);
    }

    /**
     * Listens for the enter key in the products searchbar.
     *
     * queryString could potentially be a String of number
     * characters. products are searched by String first. if the string can be parsed into an int, search for productId
     * as well, and append to the initial results
     */
    public void productSearchBarListener() throws IOException
    {
        ObservableList<Product> results;
        String queryString = productSearchBar.getText();
        if (queryString.equals("")) {
            productTableView.setItems(Inventory.getAllProducts());
        } else {
            results = Inventory.lookupProduct(queryString);
            if (isPositiveIntString(queryString)) {
                int queryInt = Integer.parseInt(queryString);
                Product intQueryResult = Inventory.lookupProduct(queryInt);
                if (intQueryResult != null) {
                    results.add(Inventory.lookupProduct(queryInt));
                }
            }
            productTableView.setItems(results);
            if (results.size() == 1) {
                productTableView.getSelectionModel().clearAndSelect(0);
                productTableView.getFocusModel().focus(0);
                productSelectListener();
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
    public boolean isPositiveIntString(String str)
    {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * initializes table by mapping columns to the respective data types. TableView.setItems() allows the TableView to
     * detect changes in data and update dynamically.
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
        productTableView.setItems(Inventory.getAllProducts());
    }

    /**
     * creates several sample data to be initialized on first load
     */
    private void addSampleData()
    {
        Part brakes = new InHouse(IDGenerator.generateId(), "Brakes", 15.22, 10, 1, 30, 1);
        Part wheel = new Outsourced(IDGenerator.generateId(), "Wheel", 11.22, 16, 1, 30, "Test Company");
        Part seat = new InHouse(IDGenerator.generateId(), "Seat", 15.22, 10, 1, 30, 3);
        Product giantBike = new Product(IDGenerator.generateId(), "Giant Bike", 299.99, 5, 1, 30);
        Product tricycle = new Product(IDGenerator.generateId(), "Tricycle", 99.99, 3, 1, 30);
        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);
        Inventory.addProduct(giantBike);
        Inventory.addProduct(tricycle);
    }
}