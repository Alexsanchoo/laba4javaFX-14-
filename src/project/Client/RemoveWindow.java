package project.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Clothes;

import java.util.ArrayList;

public class RemoveWindow {

    private int recToRemove = -1;

    public int getRecToRemove() {
        return recToRemove;
    }

    public RemoveWindow(Stage primaryStage, ObservableList<Clothes> items) {
        Stage stage = new Stage();
        stage.setTitle("Remove record");

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        TableClothes table = new TableClothes(items);

        ArrayList<Integer> choice = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            choice.add(items.get(i).getId());
        }
        ComboBox<Integer> idCb = new ComboBox<>(FXCollections.observableArrayList(choice));

        Button removeBtn = new Button("remove");

        removeBtn.setOnAction(event -> {
            recToRemove = idCb.getValue();
            stage.close();
        });


        box.getChildren().addAll(table.getTable(), new Label("Make a choice: "), idCb, removeBtn);
        Scene scene = new Scene(box, 620,600);
        stage.setScene(scene);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.showAndWait();
    }
}
