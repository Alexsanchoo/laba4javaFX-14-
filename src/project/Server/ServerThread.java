package project.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class ServerThread implements Runnable {

    private int number = 0;
    private Socket client = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String username = "root";
    private String password = "root";
    private String URL = "jdbc:mysql://localhost:3306/clothes?serverTimezone=Europe/Moscow";



    public ServerThread(Socket client, int number) throws IOException {
        this.number = number;
        this.client = client;
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new IOException(e);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS clothes");
            statement.executeUpdate("USE clothes");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS clothes(\n" +
                    "id MEDIUMINT NOT NULL AUTO_INCREMENT,\n" +
                    "category ENUM('женская', 'мужская', 'детская'),\n" +
                    "name CHAR(30) NOT NULL,\n" +
                    "material CHAR(50) NOT NULL,\n" +
                    "size INT,\n" +
                    "price DOUBLE,\n" +
                    "PRIMARY KEY(id))");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int choice = 0;
        while(choice != 5) {
            try {
                String choiceStr = in.readUTF();
                choice = Integer.parseInt(choiceStr);

                switch(choice) {
                    case 1:
                    {
                        String result = "";
                        resultSet = statement.executeQuery("SELECT * FROM clothes");
                        while(resultSet.next()) {
                            result += resultSet.getString(1) + "/";
                            result += resultSet.getString(2) + "/";
                            result += resultSet.getString(3) + "/";
                            result += resultSet.getString(4) + "/";
                            result += resultSet.getString(5) + "/";
                            result += resultSet.getString(6) + "/";
                        }
                        out.writeUTF(result);
                    }
                    break;

                    case 2:
                    {
                        try {
                            String entry = in.readUTF();
                            if(entry.equals("error")) {
                                throw new RuntimeException("close window");
                            }
                            String[] elements = entry.split("/");
                            statement.executeUpdate("INSERT INTO clothes(category, name, material, size, price) " +
                                    "VALUES " +
                                    "('" + elements[1] + "', '" + elements[2] + "', '" + elements[3] + "', " + elements[4] + ", " + elements[5] + ")");
                        } catch(RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                    case 3:
                    {
                        String result = "";
                        resultSet = statement.executeQuery("SELECT * FROM clothes");
                        while(resultSet.next()) {
                            result += resultSet.getString(1) + "/";
                            result += resultSet.getString(2) + "/";
                            result += resultSet.getString(3) + "/";
                            result += resultSet.getString(4) + "/";
                            result += resultSet.getString(5) + "/";
                            result += resultSet.getString(6) + "/";
                        }
                        out.writeUTF(result);

                        try {
                            String recToRemove = in.readUTF();
                            if(recToRemove.equals("error")) {
                                throw new RuntimeException("close window");
                            }
                            statement.executeUpdate("DELETE FROM clothes WHERE id = " + recToRemove);
                        } catch(RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                        break;

                    case 4:
                    {
                        String result = "";
                        resultSet = statement.executeQuery("SELECT * FROM clothes");
                        while(resultSet.next()) {
                            result += resultSet.getString(1) + "/";
                            result += resultSet.getString(2) + "/";
                            result += resultSet.getString(3) + "/";
                            result += resultSet.getString(4) + "/";
                            result += resultSet.getString(5) + "/";
                            result += resultSet.getString(6) + "/";
                        }
                        out.writeUTF(result);

                        try {
                            String entry = in.readUTF();
                            if(entry.equals("error")) {
                                throw new RuntimeException("close window");
                            }
                            String[] elements = entry.split("/");
                            statement.executeUpdate("UPDATE clothes SET category='" + elements[1] + "', name='" + elements[2] +
                                    "', material='" + elements[3] + "', size=" + elements[4] + ", price=" + elements[5] + " WHERE id=" + elements[0] + ";");
                        } catch(RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                        break;

                    case 5:
                        System.out.println("Client " + number + " disconnected from the server.");
                        break;
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
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
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
