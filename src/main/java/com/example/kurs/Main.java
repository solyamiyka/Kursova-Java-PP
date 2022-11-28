package com.example.kurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    public static Logger logger = Logger.getGlobal();

    @Override
    public void start(Stage stage) {
        try {

            FileHandler handler = new FileHandler("logs.txt");
            handler.setFormatter(new SimpleFormatter());

            logger.addHandler(handler);
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("design.fxml")));
            Scene scene = new Scene(root);
            Image icon = new Image("D:\\Study\\ПП\\kurs\\src\\main\\resources\\com\\example\\kurs\\piggy-bank.png");

            stage.getIcons().add(icon);
            stage.setResizable(false);
            String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Program for taxes!");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        launch();
    }
}