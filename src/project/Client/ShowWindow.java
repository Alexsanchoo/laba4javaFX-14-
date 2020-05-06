package project.Client;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Clothes;

public class ShowWindow {

    public ShowWindow(Stage primaryStage, ObservableList<Clothes> items) {
        Stage stage = new Stage();
        stage.setTitle("Show information");

        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);

        TableClothes table = new TableClothes(items);

        pane.getChildren().add(table.getTable());
        Scene scene = new Scene(pane, 620,500);
        stage.setScene(scene);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.setX(primaryStage.getX());
        stage.setY(primaryStage.getY());

        stage.showAndWait();
    }
}
