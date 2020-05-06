package project.Client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WarningWindow {

    public WarningWindow( Stage primaryStage, String message) {
        Stage stage = new Stage();
        stage.setTitle("Warning");

        VBox pane = new VBox(30);
        pane.setAlignment(Pos.CENTER);

        Button okBtn = new Button("CLOSE");
        Label messageLbl = new Label(message);
        messageLbl.setFont(new Font("Times New Roman", 16));
        messageLbl.setStyle("-fx-font-weight: bold");

        okBtn.setOnAction(event ->  {
            stage.close();
        });

        pane.getChildren().addAll(messageLbl, okBtn);

        Scene scene = new Scene(pane, 400, 200);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.setX(primaryStage.getX() + 50);
        stage.setY(primaryStage.getY() + 50);

        stage.showAndWait();
    }
}
