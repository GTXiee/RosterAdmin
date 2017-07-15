package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import datamodel.Datasource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/mainWindow.fxml"));
        primaryStage.setTitle("Roster Admin");
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(700);

        primaryStage.show();
    }

    @Override
    public void init() {
        if (!Datasource.getInstance().open()) {
            System.out.println("ERROR: Couldn't connect to database");
        }
    }

    @Override
    public void stop() {
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

