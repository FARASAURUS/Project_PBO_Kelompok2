package carcruiser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to start the application.
 */
public class CarCruiser extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Menu.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(loader.load());

            // Set up the stage
            primaryStage.setTitle("Car Cruiser Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
