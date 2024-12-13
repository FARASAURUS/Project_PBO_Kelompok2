package carcruiser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * Controller class for Menu.fxml
 */
public class MenuController implements Initializable {

    @FXML
    private ImageView playbutton;

    @FXML
    private ImageView exitbutton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add actions for Play and Exit buttons
        playbutton.setOnMouseClicked(event -> startGame(event));
        exitbutton.setOnMouseClicked(event -> exitGame());
    }

    /**
     * Starts the game by switching to FXMLDocument.fxml.
     */
    private void startGame(javafx.scene.input.MouseEvent event) {
        try {
            // Load the new FXML file (FXMLDocument.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = (Stage) playbutton.getScene().getWindow();
        Scene newScene = new Scene(root);

        stage.setScene(newScene);
        stage.show();
        root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application.
     */
    private void exitGame() {
        System.exit(0); // Terminate the application
    }
}
