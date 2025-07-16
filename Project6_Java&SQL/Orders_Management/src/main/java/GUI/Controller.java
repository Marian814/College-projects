package GUI;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrdersBLL;
import BusinessLogic.OrdersDetailsBLL;
import BusinessLogic.ProductBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;

public class Controller {
    /**
     * Get a TableView with data from a database.
     * @param entityName
     * @return
     */
    public static TableView<Map<String, Object>> getTable(String entityName) {
        List<?> dataList;
        switch (entityName.toLowerCase()) {
            case "client":
                dataList = new ClientBLL().findAllClients();
                break;
            case "product":
                dataList = new ProductBLL().findAllProducts();
                break;
            case "orders":
                dataList = new OrdersBLL().findAllOrders();
                break;
            case "ordersdetails":
                dataList = new OrdersDetailsBLL().findAllOrdersDetails();
                break;
            default:
                return new TableView<>();
        }
        if (dataList == null || dataList.isEmpty()) {
            return new TableView<>();
        }
        TableView<Map<String, Object>> tableView = new TableView<>();
        Map<String, Object> firstProps = ReflectionClass.retrieveProperties(dataList.get(0));
        ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList();
        dataList.forEach(obj -> tableData.add(ReflectionClass.retrieveProperties(obj)));
        for (String columnName : firstProps.keySet()) {
            TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
            column.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().get(columnName))
            );
            column.setPrefWidth(100);
            tableView.getColumns().add(column);
        }
        tableView.setItems(tableData);
        return tableView;
    }

    /**
     * Create a header layout with an options button.
     * @param optionsBox
     * @return
     */
    public static BorderPane createHeader(VBox optionsBox) {
        BorderPane headerLayout = new BorderPane();
        HBox rightBox = new HBox(20);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPadding(new Insets(0, 20, 0, 0));
        ImageView optionsIcon = new ImageView(new Image("./optionsLogo.png", 40, 40, true, true));
        Button optionsBtn = new Button("", optionsIcon);
        rightBox.getChildren().addAll(optionsBtn);
        headerLayout.setRight(rightBox);
        optionsBtn.setOnAction(e -> {
            optionsBox.setVisible(!optionsBox.isVisible());
        });
        return headerLayout;
    }
}
