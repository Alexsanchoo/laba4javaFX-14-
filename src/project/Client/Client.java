package project.Client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Clothes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    private Socket clientSocket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private boolean isConnected = false;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button connectionBtn = new Button("connect");
        Button showBtn = new Button("Show data information");
        showBtn.setPrefSize(200.0, 2.0);
        Button addBtn = new Button("Add record");
        addBtn.setPrefSize(200.0, 2.0);
        Button removeBtn = new Button("Remove record");
        removeBtn.setPrefSize(200.0, 2.0);
        Button editBtn = new Button("Edit record");
        editBtn.setPrefSize(200.0, 2.0);
        Button disconnectBtn = new Button("disconnect");
        disconnectBtn.setPrefSize(200.0, 2.0);

        TextField ipAdressTf = new TextField("127.0.0.1");
        ipAdressTf.setPrefColumnCount(7);
        TextField portTf = new TextField("1024");
        portTf.setPrefColumnCount(5);


        //подключение
        connectionBtn.setOnAction(event -> {
            try {
                if (clientSocket != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isConnected = false;
                }
                clientSocket = new Socket(ipAdressTf.getText(), Integer.parseInt(portTf.getText()));
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                isConnected = true;
                new WarningWindow(primaryStage, "The connection is established!");
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                new WarningWindow(primaryStage, "Connection error!");
            }
        });

        //просмотр информации
        showBtn.setOnAction(event -> {
            if (isConnected) {
                try {
                    out.writeUTF("1");
                    String entry = in.readUTF();
                    ObservableList<Clothes> list = getClothes(entry);
                    new ShowWindow(primaryStage, list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                new WarningWindow(primaryStage, "The connection is not established!");
            }
        });

        //добавление информации
        addBtn.setOnAction(event -> {
            if(isConnected) {
                try {
                    out.writeUTF("2");
                    AddWindow window = new AddWindow(primaryStage);
                    if(window.getClothhes() == null) {
                        out.writeUTF("error");
                        throw new RuntimeException("Close window");
                    }
                    String entry = getFormatToSend(window.getClothhes());
                    out.writeUTF(entry);
                    new WarningWindow(primaryStage, "New entry added successfully!");

                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                new WarningWindow(primaryStage, "The connection is not established!");
            }

        });

        //удаление информации
        removeBtn.setOnAction(event -> {
            if(isConnected) {
                try {
                    out.writeUTF("3");
                    String entry = in.readUTF();
                    ObservableList<Clothes> list = getClothes(entry);
                    RemoveWindow window = new RemoveWindow(primaryStage, list);
                    if(window.getRecToRemove() == -1) {
                        out.writeUTF("error");
                        throw new RuntimeException("close window");
                    }
                    out.writeUTF(String.valueOf(window.getRecToRemove()));
                    new WarningWindow(primaryStage, "New entry removed successfully!");

                } catch(IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                new WarningWindow(primaryStage, "The connection is not established!");
            }
        });

        //редактирование данных
        editBtn.setOnAction(event -> {
            if(isConnected) {
                try {
                    out.writeUTF("4");
                    String entry = in.readUTF();
                    ObservableList<Clothes> list = getClothes(entry);
                    EditWindow window = new EditWindow(primaryStage, list);
                    if(window.getClothes() == null) {
                        out.writeUTF("error");
                        throw new RuntimeException("close window");
                    }
                    entry = getFormatToSend(window.getClothes());
                    out.writeUTF(entry);
                    new WarningWindow(primaryStage, "Entry is edited successfully!");
                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                new WarningWindow(primaryStage, "The connection is not established!");
            }
        });


        //отключение
        disconnectBtn.setOnAction(event -> {
            if(isConnected) {
                try {
                    out.writeUTF("5");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                isConnected = false;
                new WarningWindow(primaryStage, "You have disconnected from the server!");

            }  else {
                new WarningWindow(primaryStage, "The connection is not established!");
            }
        });

        Group root = new Group();

        connectionBtn.setLayoutX(20.0);
        connectionBtn.setLayoutY(20.0);

        Label ipAdressLbl = new Label("IP adress");
        ipAdressLbl.setLayoutX(120.0);
        ipAdressLbl.setLayoutY(25.0);

        ipAdressTf.setLayoutX(190.0);
        ipAdressTf.setLayoutY(20.0);

        Label portLbl = new Label("Port");
        portLbl.setLayoutX(330.0);
        portLbl.setLayoutY(25.0);

        portTf.setLayoutX(365.0);
        portTf.setLayoutY(20.0);

        showBtn.setLayoutX(150.0);
        showBtn.setLayoutY(80.0);

        addBtn.setLayoutX(150.0);
        addBtn.setLayoutY(120.0);

        removeBtn.setLayoutX(150.0);
        removeBtn.setLayoutY(160.0);

        editBtn.setLayoutX(150.0);
        editBtn.setLayoutY(200.0);

        disconnectBtn.setLayoutX(150.0);
        disconnectBtn.setLayoutY(240.0);

        root.getChildren().addAll(connectionBtn, ipAdressLbl, ipAdressTf, portLbl, portTf, showBtn, addBtn, removeBtn, editBtn, disconnectBtn);

        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client");
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (isConnected) {
            try {
                out.writeUTF("5");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private ObservableList<Clothes> getClothes(String entry) {
        String[] elements = entry.split("/");
        Clothes[] clothes = new Clothes[elements.length / 6];

        int i = 0, j = 0;
        while (i < clothes.length) {
            clothes[i] = new Clothes();
            clothes[i].setId(Integer.parseInt(elements[j++]));
            clothes[i].setCategory(elements[j++]);
            clothes[i].setName(elements[j++]);
            clothes[i].setMaterial(elements[j++]);
            clothes[i].setSize(Integer.parseInt(elements[j++]));
            clothes[i++].setPrice(Double.parseDouble(elements[j++]));
        }

        ObservableList<Clothes> items = FXCollections.observableArrayList(clothes);
        return items;
    }


    private String getFormatToSend(Clothes clothes) {
        String result = "";
        result += clothes.getId() + "/";
        result += clothes.getCategory() + "/";
        result += clothes.getName() + "/";
        result += clothes.getMaterial() + "/";
        result += clothes.getSize() + "/";
        result += clothes.getPrice() + "/";

        return result;
    }
}
