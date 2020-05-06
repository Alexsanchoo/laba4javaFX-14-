package project.Client;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.Clothes;

public class TableClothes {

    private TableView<Clothes> table = null;

    public TableClothes(ObservableList<Clothes> items) {
        table = new TableView<>(items);
        TableColumn<Clothes, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Clothes, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Clothes, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Clothes, String> materialColumn = new TableColumn<>("Material");
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));

        TableColumn<Clothes, Integer> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Clothes, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(idColumn, categoryColumn, nameColumn, materialColumn, sizeColumn, priceColumn);
        table.setPrefSize(480, 500);
    }

    public TableView<Clothes> getTable() {
        return table;
    }
}
