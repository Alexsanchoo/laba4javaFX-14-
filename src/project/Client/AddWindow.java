package project.Client;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Clothes;


public class AddWindow {

    private Clothes clothhes = null;


    Clothes getClothhes() {
        return clothhes;
    }

    public AddWindow(Stage primaryStage) {
        Stage stage = new Stage();
        stage.setTitle("Add new information");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(15, 20, 10, 10));

        Label categoryLbl = new Label("Enter category: ");
        ComboBox<String> categoryCb = new ComboBox<>(FXCollections.observableArrayList("женская", "мужская", "детская"));
        categoryCb.setValue("женская");
        Label nameLbl = new Label("Enter name: ");
        TextField nameTf = new TextField();
        Label materialLbl = new Label("Enter material: ");
        TextField materailTf = new TextField();
        Label sizeLbl = new Label("Enter size: ");
        TextField sizeTf = new TextField();
        Label priceLbl = new Label("Enter price: ");
        TextField priceTf = new TextField();

        Button addBtn = new Button("add");

        addBtn.setOnAction(event -> {
            try{
                clothhes = new Clothes(0, categoryCb.getValue(), nameTf.getText(), materailTf.getText(),
                        Integer.parseInt(sizeTf.getText()), Double.parseDouble(priceTf.getText()));
                stage.close();
            } catch(NumberFormatException e) {
                e.printStackTrace();
                new WarningWindow(stage, "Invalid input!");
            }
        });

        pane.getChildren().addAll(categoryLbl, categoryCb, nameLbl, nameTf, materialLbl, materailTf, sizeLbl, sizeTf,
                priceLbl, priceTf, addBtn);
        Scene scene = new Scene(pane, 480, 500);
        stage.setScene(scene);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.showAndWait();
    }
}
