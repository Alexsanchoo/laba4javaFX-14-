package project.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Clothes;

import java.util.ArrayList;

public class EditWindow {
    private Clothes clothes = null;

    public Clothes getClothes() {
        return clothes;
    }

    public EditWindow(Stage primaryStage, ObservableList<Clothes> items) {
        Stage stage = new Stage();
        stage.setTitle("Edit record");

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        TableClothes table = new TableClothes(items);

        ArrayList<Integer> choice = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            choice.add(items.get(i).getId());
        }
        ComboBox<Integer> idCb = new ComboBox<>(FXCollections.observableArrayList(choice));

        Button editBtn = new Button("edit");


        editBtn.setOnAction(event -> {

            Clothes clothesTemp = null;

            int recToEdit = idCb.getValue();
            for (int i = 0; i < items.size(); i++) {
                if(items.get(i).getId() == recToEdit) {
                    clothesTemp = items.get(i);
                    break;
                }
            }

            VBox boxEdit = new VBox(10);
            boxEdit.setAlignment(Pos.CENTER);

            Label categoryLbl = new Label("Category: ");
            ComboBox<String> categoryCb = new ComboBox<>(FXCollections.observableArrayList("женская", "мужская", "детская"));
            categoryCb.setValue(clothesTemp.getCategory());

            Label nameLbl = new Label("Name: ");
            TextField nameTf = new TextField(clothesTemp.getName());

            Label materialLbl = new Label("Material: ");
            TextField materialTf = new TextField(clothesTemp.getMaterial());

            Label sizeLbl = new Label("Size: ");
            TextField sizeTf = new TextField(String.valueOf(clothesTemp.getSize()));

            Label priceLbl = new Label("Price: ");
            TextField priceTf = new TextField(String.valueOf(clothesTemp.getPrice()));

            Button edit2Btn = new Button("edit");

            Clothes finalClothesTemp = clothesTemp;
            edit2Btn.setOnAction(event2 -> {
                try{
                    clothes = new Clothes(finalClothesTemp.getId(), categoryCb.getValue(), nameTf.getText(), materialTf.getText(),
                            Integer.parseInt(sizeTf.getText()), Double.parseDouble(priceTf.getText()));
                    stage.close();
                } catch(NumberFormatException e) {
                    e.printStackTrace();
                    new WarningWindow(stage, "Invalid input!");
                }
            });

            boxEdit.getChildren().addAll(categoryLbl, categoryCb, nameLbl, nameTf, materialLbl, materialTf, sizeLbl, sizeTf, priceLbl, priceTf,
                    edit2Btn);
            Scene sceneEdit = new Scene(boxEdit, 620, 600);
            stage.setScene(sceneEdit);
        });

        box.getChildren().addAll(table.getTable(), new Label("Make a choice: "), idCb, editBtn);
        Scene scene = new Scene(box, 620, 600);
        stage.setScene(scene);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.showAndWait();
    }
}
