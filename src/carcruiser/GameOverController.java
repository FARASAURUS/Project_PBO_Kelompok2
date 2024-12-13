package carcruiser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private ImageView YesButton;
    @FXML
    private ImageView NoButton;
    @FXML
    private Label ScoreLabel;

    private FXMLDocumentController gameController;

    // Setter method to pass the score from FXMLDocumentController
    public void setGameController(FXMLDocumentController gameController) {
        this.gameController = gameController;
        // Update the score label with the current score from the main game controller
        ScoreLabel.setText("Score: " + gameController.getScore());

        // Attach event handlers to buttons after the controller is set up
        YesButton.setOnMouseClicked(event -> handleYesButton());
        NoButton.setOnMouseClicked(event -> handleNoButton());
    }

    // Method to restart the game when Yes button is clicked
    private void handleYesButton() {
        try {
            // Load the new scene for restarting the game
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Stage stage = (Stage) YesButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            // Set the new scene and show it
            stage.setScene(newScene);
            stage.show();
            root.requestFocus();

            // Restart the game logic
            gameController.restartGame();
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions that may occur during loading the scene
        }
    }

    // Method to exit the game when No button is clicked
    private void handleNoButton() {
        System.exit(0); // Exit the game when No is clicked
    }
}