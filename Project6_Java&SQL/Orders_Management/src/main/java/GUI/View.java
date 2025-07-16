package GUI;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrdersBLL;
import BusinessLogic.OrdersDetailsBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Orders;
import Model.OrdersDetails;
import Model.Product;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.Map;

public class View extends Application {
    private Stage primaryStage;
    private VBox optionsBox;
    private BorderPane mainLayout;

    /**
     * Start the program.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        optionsBox = new VBox(5);
        optionsBox.setVisible(false);
        optionsBox.getStyleClass().add("popup-box");
        optionsBox.setPrefWidth(120);
        optionsBox.setMaxWidth(120);
        optionsBox.setMaxHeight(100);
        optionsBox.setPadding(new Insets(5));
        optionsBox.setAlignment(Pos.TOP_LEFT);

        Button ClientBtn = new Button("Client");
        Button ProductBtn = new Button("Product");
        Button OrdersBtn = new Button("Orders");
        Button OrdersDetailsBtn = new Button("OrdersDetails");

        ClientBtn.setOnAction(e -> showClientScene());
        ProductBtn.setOnAction(e -> showProductScene());
        OrdersBtn.setOnAction(e -> showOrdersScene());
        OrdersDetailsBtn.setOnAction(e -> showOrdersDetailsScene());

        ClientBtn.setMaxWidth(Double.MAX_VALUE);
        ProductBtn.setMaxWidth(Double.MAX_VALUE);
        OrdersBtn.setMaxWidth(Double.MAX_VALUE);
        OrdersDetailsBtn.setMaxWidth(Double.MAX_VALUE);

        optionsBox.getChildren().addAll(ClientBtn, ProductBtn, OrdersBtn, OrdersDetailsBtn);

        StackPane root = new StackPane();
        StackPane.setAlignment(optionsBox, Pos.TOP_RIGHT);
        StackPane.setMargin(optionsBox, new Insets(70, 20, 0, 0));

        BorderPane headerLayout = Controller.createHeader(optionsBox);
        mainLayout = new BorderPane();
        mainLayout.setTop(headerLayout);
        mainLayout.setRight(optionsBox);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DataBaseProject");
        showClientScene();
        primaryStage.show();
    }

    /**
     * Build the client scene.
     */
    private void showClientScene() {
        TextField ClientID = new TextField();
        ClientID.setPromptText("ClientID");
        ClientID.setPrefWidth(100);

        TextField LastName = new TextField();
        LastName.setPromptText("LastName");
        LastName.setPrefWidth(100);

        TextField FirstName = new TextField();
        FirstName.setPromptText("FirstName");
        FirstName.setPrefWidth(100);

        TextField Email = new TextField();
        Email.setPromptText("Email");
        Email.setPrefWidth(100);

        TextField PhoneNumber = new TextField();
        PhoneNumber.setPromptText("PhoneNumber");
        PhoneNumber.setPrefWidth(100);

        TextField Street = new TextField();
        Street.setPromptText("Street");
        Street.setPrefWidth(100);

        TextField Number = new TextField();
        Number.setPromptText("Number");
        Number.setPrefWidth(100);

        TextField City = new TextField();
        City.setPromptText("City");
        City.setPrefWidth(100);

        TextField PostalCode = new TextField();
        PostalCode.setPromptText("PostalCode");
        PostalCode.setPrefWidth(100);

        TextField Country = new TextField();
        Country.setPromptText("Country");
        Country.setPrefWidth(100);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> {
            try {
                ClientBLL clientBLL = new ClientBLL();
                Client newClient = new Client(Integer.parseInt(ClientID.getText()),
                        LastName.getText(), FirstName.getText(), Email.getText(),
                        PhoneNumber.getText(), Street.getText(), Number.getText(),
                        City.getText(), PostalCode.getText(), Country.getText());
                clientBLL.insertClient(newClient);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ClientID.clear();
                LastName.clear();
                FirstName.clear();
                Email.clear();
                PhoneNumber.clear();
                Street.clear();
                Number.clear();
                City.clear();
                PostalCode.clear();
            }
        });

        updateButton.setOnAction(e -> {
            try {
                ClientBLL clientBLL = new ClientBLL();
                Client updatedClient = new Client(Integer.parseInt(ClientID.getText()),
                        LastName.getText(), FirstName.getText(), Email.getText(),
                        PhoneNumber.getText(), Street.getText(), Number.getText(),
                        City.getText(), PostalCode.getText(), Country.getText());
                clientBLL.updateClient(updatedClient);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ClientID.clear();
                LastName.clear();
                FirstName.clear();
                Email.clear();
                PhoneNumber.clear();
                Street.clear();
                Number.clear();
                City.clear();
                PostalCode.clear();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                ClientBLL clientBLL = new ClientBLL();
                Client deletedClient = clientBLL.findClientById(Integer.parseInt(ClientID.getText()));
                clientBLL.deleteClient(deletedClient);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ClientID.clear();
                LastName.clear();
                FirstName.clear();
                Email.clear();
                PhoneNumber.clear();
                Street.clear();
                Number.clear();
                City.clear();
                PostalCode.clear();
            }
        });

        HBox FieldBox = new HBox(10, ClientID, LastName, FirstName, Email, PhoneNumber, Street, Number, City, PostalCode, Country);
        FieldBox.setAlignment(Pos.CENTER_LEFT);

        HBox ButtonBox = new HBox(10, addButton, updateButton, deleteButton);
        ButtonBox.setAlignment(Pos.CENTER_LEFT);

        TableView<Map<String, Object>> clientTable = Controller.getTable("client");

        clientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ClientID.setText(String.valueOf(newSelection.get("ClientID")));
                LastName.setText((String) newSelection.get("LastName"));
                FirstName.setText((String) newSelection.get("FirstName"));
                Email.setText((String) newSelection.get("Email"));
                PhoneNumber.setText((String) newSelection.get("PhoneNumber"));
                Street.setText((String) newSelection.get("Street"));
                Number.setText((String) newSelection.get("Number"));
                City.setText((String) newSelection.get("City"));
                PostalCode.setText((String) newSelection.get("PostalCode"));
                Country.setText((String) newSelection.get("Country"));
            }
        });

        VBox contentBox = new VBox(20, FieldBox, ButtonBox, clientTable);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        mainLayout.setCenter(contentBox);
    }

    /**
     * Build the product scene.
     */
    private void showProductScene() {
        TextField ProductID = new TextField();
        ProductID.setPromptText("ProductID");
        ProductID.setPrefWidth(100);

        TextField ProductName = new TextField();
        ProductName.setPromptText("ProductName");
        ProductName.setPrefWidth(100);

        TextField Price = new TextField();
        Price.setPromptText("Price");
        Price.setPrefWidth(100);

        TextField StockQuantity = new TextField();
        StockQuantity.setPromptText("StockQuantity");
        StockQuantity.setPrefWidth(100);

        TextField Category = new TextField();
        Category.setPromptText("Category");
        Category.setPrefWidth(100);

        TextField Description = new TextField();
        Description.setPromptText("Description");
        Description.setPrefWidth(100);

        TextField CreatedAt = new TextField();
        CreatedAt.setPromptText("CreatedAt");
        CreatedAt.setPrefWidth(100);

        TextField ExpireAt = new TextField();
        ExpireAt.setPromptText("ExpireAt");
        ExpireAt.setPrefWidth(100);

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> {
            try {
                ProductBLL productBLL = new ProductBLL();
                Product newProduct = new Product(Integer.parseInt(ProductID.getText()),
                        ProductName.getText(), Double.parseDouble(Price.getText()),
                        Integer.parseInt(StockQuantity.getText()), Category.getText(),
                        Description.getText(), Date.valueOf(CreatedAt.getText()), Date.valueOf(ExpireAt.getText()));
                productBLL.insertProduct(newProduct);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ProductID.clear();
                ProductName.clear();
                Price.clear();
                StockQuantity.clear();
                Category.clear();
                Description.clear();
                CreatedAt.clear();
                ExpireAt.clear();
            }
        });

        updateButton.setOnAction(e -> {
            try {
                ProductBLL productBLL = new ProductBLL();
                Product updatedProduct = new Product(Integer.parseInt(ProductID.getText()),
                        ProductName.getText(), Double.parseDouble(Price.getText()),
                        Integer.parseInt(StockQuantity.getText()), Category.getText(),
                        Description.getText(), Date.valueOf(CreatedAt.getText()), Date.valueOf(ExpireAt.getText()));
                productBLL.updateProduct(updatedProduct);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ProductID.clear();
                ProductName.clear();
                Price.clear();
                StockQuantity.clear();
                Category.clear();
                Description.clear();
                CreatedAt.clear();
                ExpireAt.clear();
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                ProductBLL productBLL = new ProductBLL();
                Product deletedProduct = productBLL.findProductById(Integer.parseInt(ProductID.getText()));
                productBLL.deleteProduct(deletedProduct);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                ProductID.clear();
                ProductName.clear();
                Price.clear();
                StockQuantity.clear();
                Category.clear();
                Description.clear();
                CreatedAt.clear();
                ExpireAt.clear();
            }
        });

        HBox FieldBox = new HBox(10, ProductID, ProductName, Price, StockQuantity, Category, Description, CreatedAt, ExpireAt);
        FieldBox.setAlignment(Pos.CENTER_LEFT);

        HBox ButtonBox = new HBox(10, addButton, updateButton, deleteButton);
        ButtonBox.setAlignment(Pos.CENTER_LEFT);

        TableView<Map<String, Object>> productTable = Controller.getTable("product");

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ProductID.setText(String.valueOf(newSelection.get("ProductID")));
                ProductName.setText((String) newSelection.get("ProductName"));
                Price.setText(String.valueOf(newSelection.get("Price")));
                StockQuantity.setText(String.valueOf(newSelection.get("StockQuantity")));
                Category.setText((String) newSelection.get("Category"));
                Description.setText((String) newSelection.get("Description"));
                CreatedAt.setText(String.valueOf(newSelection.get("CreatedAt")));
                ExpireAt.setText(String.valueOf(newSelection.get("ExpireAt")));
            }
        });

        VBox contentBox = new VBox(20, FieldBox, ButtonBox, productTable);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        mainLayout.setCenter(contentBox);
    }

    /**
     * Build the order scene.
     */
    private void showOrdersScene() {
        TextField OrderID = new TextField();
        OrderID.setPromptText("OrderID");
        OrderID.setPrefWidth(100);

        TextField ClientID = new TextField();
        ClientID.setPromptText("ClientID");
        ClientID.setPrefWidth(100);

        TextField OrderDate = new TextField();
        OrderDate.setPromptText("OrderDate");
        OrderDate.setPrefWidth(100);

        HBox FieldBox = new HBox(10, OrderID, ClientID, OrderDate);
        FieldBox.setAlignment(Pos.CENTER_LEFT);

        TextField Product1 = new TextField();
        Product1.setPromptText("Product 1");
        Product1.setPrefWidth(100);

        TextField Product2 = new TextField();
        Product2.setPromptText("Product 2");
        Product2.setPrefWidth(100);

        TextField Product3 = new TextField();
        Product3.setPromptText("Product 3");
        Product3.setPrefWidth(100);

        TextField Product4 = new TextField();
        Product4.setPromptText("Product 4");
        Product4.setPrefWidth(100);

        HBox FieldBox2 = new HBox(10, Product1, Product2, Product3, Product4);
        FieldBox2.setAlignment(Pos.CENTER_LEFT);

        TextField Price1 = new TextField();
        Price1.setPromptText("Price 1");
        Price1.setPrefWidth(100);

        TextField Price2 = new TextField();
        Price2.setPromptText("Price 2");
        Price2.setPrefWidth(100);

        TextField Price3 = new TextField();
        Price3.setPromptText("Price 3");
        Price3.setPrefWidth(100);

        TextField Price4 = new TextField();
        Price4.setPromptText("Price 4");
        Price4.setPrefWidth(100);

        HBox FieldBox3 = new HBox(10, Price1, Price2, Price3, Price4);
        FieldBox3.setAlignment(Pos.CENTER_LEFT);

        TextField Quantity1 = new TextField();
        Quantity1.setPromptText("Quantity 1");
        Quantity1.setPrefWidth(100);

        TextField Quantity2 = new TextField();
        Quantity2.setPromptText("Quantity 2");
        Quantity2.setPrefWidth(100);

        TextField Quantity3 = new TextField();
        Quantity3.setPromptText("Quantity 3");
        Quantity3.setPrefWidth(100);

        TextField Quantity4 = new TextField();
        Quantity4.setPromptText("Quantity 4");
        Quantity4.setPrefWidth(100);

        HBox FieldBox4 = new HBox(10, Quantity1, Quantity2, Quantity3, Quantity4);
        FieldBox4.setAlignment(Pos.CENTER_LEFT);

        Button OrderButton = new Button("Order");

        OrderButton.setOnAction(e -> {
            try {
                OrdersBLL ordersBLL = new OrdersBLL();
                ProductBLL productBLL = new ProductBLL();
                OrdersDetailsBLL ordersDetailsBLL = new OrdersDetailsBLL();

                Product product1 = productBLL.findProductById(Integer.parseInt(Product1.getText()));
                if(product1.getStockQuantity() < Integer.parseInt(Quantity1.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Product 1 out of stock!");
                    alert.showAndWait();
                    return;
                }
                Product product2 = productBLL.findProductById(Integer.parseInt(Product2.getText()));
                if(product2.getStockQuantity() < Integer.parseInt(Quantity2.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Product 2 out of stock!");
                    alert.showAndWait();
                    return;
                }
                Product product3 = productBLL.findProductById(Integer.parseInt(Product3.getText()));
                if(product3.getStockQuantity() < Integer.parseInt(Quantity3.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Product 3 out of stock!");
                    alert.showAndWait();
                    return;
                }
                Product product4 = productBLL.findProductById(Integer.parseInt(Product4.getText()));
                if(product4.getStockQuantity() < Integer.parseInt(Quantity4.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Product 4 out of stock!");
                    alert.showAndWait();
                    return;
                }

                product1.setStockQuantity(product1.getStockQuantity() - Integer.parseInt(Quantity1.getText()));
                productBLL.updateProduct(product1);

                product2.setStockQuantity(product2.getStockQuantity() - Integer.parseInt(Quantity2.getText()));
                productBLL.updateProduct(product2);

                product3.setStockQuantity(product3.getStockQuantity() - Integer.parseInt(Quantity3.getText()));
                productBLL.updateProduct(product3);

                product4.setStockQuantity(product4.getStockQuantity() - Integer.parseInt(Quantity4.getText()));
                productBLL.updateProduct(product4);

                double totalAmount = Double.parseDouble(Price1.getText()) * Integer.parseInt(Quantity1.getText()) +
                        Double.parseDouble(Price2.getText()) * Integer.parseInt(Quantity2.getText()) +
                        Double.parseDouble(Price3.getText()) * Integer.parseInt(Quantity3.getText()) +
                        Double.parseDouble(Price4.getText()) * Integer.parseInt(Quantity4.getText());
                Orders newOrder = new Orders(Integer.parseInt(OrderID.getText()), Integer.parseInt(ClientID.getText()),
                        Date.valueOf(OrderDate.getText()), totalAmount, "Pending");
                ordersBLL.insertOrder(newOrder);

                OrdersDetails order1 = new OrdersDetails(Integer.parseInt(OrderID.getText()), Integer.parseInt(Product1.getText()),
                        Integer.parseInt(Quantity1.getText()), Double.parseDouble(Price1.getText()));
                ordersDetailsBLL.insertOrdersDetails(order1);

                OrdersDetails order2 = new OrdersDetails(Integer.parseInt(OrderID.getText()), Integer.parseInt(Product2.getText()),
                        Integer.parseInt(Quantity2.getText()), Double.parseDouble(Price2.getText()));
                ordersDetailsBLL.insertOrdersDetails(order2);

                OrdersDetails order3 = new OrdersDetails(Integer.parseInt(OrderID.getText()), Integer.parseInt(Product3.getText()),
                        Integer.parseInt(Quantity3.getText()), Double.parseDouble(Price3.getText()));
                ordersDetailsBLL.insertOrdersDetails(order3);

                OrdersDetails order4 = new OrdersDetails(Integer.parseInt(OrderID.getText()), Integer.parseInt(Product4.getText()),
                        Integer.parseInt(Quantity4.getText()), Double.parseDouble(Price4.getText()));
                ordersDetailsBLL.insertOrdersDetails(order4);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                OrderID.clear();
                ClientID.clear();
                OrderDate.clear();
                Product1.clear();
                Product2.clear();
                Product3.clear();
                Product4.clear();
                Price1.clear();
                Price2.clear();
                Price3.clear();
                Price4.clear();
                Quantity1.clear();
                Quantity2.clear();
                Quantity3.clear();
                Quantity4.clear();
            }
        });

        HBox ButtonBox = new HBox(10, OrderButton);
        ButtonBox.setAlignment(Pos.CENTER_LEFT);

        TableView<Map<String, Object>> clientTable = Controller.getTable("client");
        TableView<Map<String, Object>> productTable = Controller.getTable("product");
        TableView<Map<String, Object>> ordersTable = Controller.getTable("orders");

        clientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ClientID.setText(String.valueOf(newSelection.get("ClientID")));
            }
        });

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(Product1.getText().equals("")) {
                    Product1.setText(String.valueOf(newSelection.get("ProductID")));
                    Price1.setText(String.valueOf(newSelection.get("Price")));
                }
                else if(Product2.getText().equals("")) {
                    Product2.setText(String.valueOf(newSelection.get("ProductID")));
                    Price2.setText(String.valueOf(newSelection.get("Price")));
                }
                else if(Product3.getText().equals("")) {
                    Product3.setText(String.valueOf(newSelection.get("ProductID")));
                    Price3.setText(String.valueOf(newSelection.get("Price")));
                }
                else if(Product4.getText().equals("")) {
                    Product4.setText(String.valueOf(newSelection.get("ProductID")));
                    Price4.setText(String.valueOf(newSelection.get("Price")));
                }
            }
        });

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OrderID.setText(String.valueOf(newSelection.get("OrderID")));
                ClientID.setText(String.valueOf(newSelection.get("ClientID")));
                OrderDate.setText(String.valueOf(newSelection.get("OrderDate")));
            }
        });

        VBox contentBox = new VBox(20, FieldBox, FieldBox2, FieldBox3, FieldBox4, ButtonBox, clientTable, productTable, ordersTable);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        mainLayout.setCenter(contentBox);
    }

    /**
     * Build the orderDetails scene.
     */
    private void showOrdersDetailsScene() {
        TableView<Map<String, Object>> ordersDetailsTable = Controller.getTable("ordersdetails");

        VBox contentBox = new VBox(20, ordersDetailsTable);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        mainLayout.setCenter(contentBox);
    }

    public static void main(String[] args) {
        launch();
    }
}
