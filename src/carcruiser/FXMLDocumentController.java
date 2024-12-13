package carcruiser;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane latar;
    @FXML
    private ImageView character;
    @FXML
    private ImageView background;

   
    private List<ImageView> bullets = new ArrayList<>(); // List for bullets
    public boolean isGameOver = false; // Status of the game, true if game is over
    private Text gameOverText; // Text to display game over message
    private Text scoreText; // Text to display the score
    public int score = 0; // Variable to track the score
    
    Hero hero;
    Enemy enemy;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hero = new Hero(latar, character, this);
        enemy = new Enemy(latar, character, this);
        
        latar.setOnKeyPressed(this::handleKeyPress);
        latar.requestFocus();
        latar.setOnMouseClicked(event -> latar.requestFocus());

        // Initialize game over text
        gameOverText = new Text(300, 200, "Game Over");
        gameOverText.setStyle("-fx-font-size: 30px; -fx-fill: red;");
        latar.getChildren().add(gameOverText);
        gameOverText.setVisible(false); // Hide game over message initially

        // Initialize score text
        scoreText = new Text(20, 30, "Score: 0");
        scoreText.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        latar.getChildren().add(scoreText);

        // Start spawning enemies
        enemy.startEnemySpawner();
    }
    
    public int getScore() {
        return score;
    }

    private void handleKeyPress(KeyEvent event) {
        if (isGameOver) return; // Don't move or shoot if the game is over

        switch (event.getCode()) {
            case W:
                hero.moveCharacterUp();
                break;
            case S:
                hero.moveCharacterDown();
                break;
            case SPACE:
                fireBullet(); // Fire bullet when SPACE is pressed
                break;
        }
    }





    private void fireBullet() {
        if (isGameOver) return; // Stop firing bullets if the game is over

        ImageView bullet = new ImageView(new Image(getClass().getResource("/carcruiser/Image/bullet.png").toExternalForm()));
        bullet.setFitWidth(30);
        bullet.setFitHeight(30);

        // Position the bullet in front of the character
        double characterCenterX = character.getBoundsInParent().getMinX() + character.getBoundsInParent().getWidth();
        double characterCenterY = character.getBoundsInParent().getMinY() + character.getBoundsInParent().getHeight() / 2;

        bullet.setLayoutX(characterCenterX); // Place the bullet in front of the character
        bullet.setLayoutY(characterCenterY - bullet.getFitHeight() / 2); // Center the bullet vertically

        latar.getChildren().add(bullet);
        bullets.add(bullet);

        animateBullet(bullet);
    }

    private void animateBullet(ImageView bullet) {
        double speed = 5.0;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameOver) {
                    stop(); // Stop bullet animation if the game is over
                    return;
                }

                bullet.setLayoutX(bullet.getLayoutX() + speed);
                checkBulletCollision(bullet, this); // Check for bullet collisions

                // Remove the bullet if it goes off-screen
                if (bullet.getLayoutX() > latar.getWidth()) {
                    latar.getChildren().remove(bullet);
                    bullets.remove(bullet);
                    stop();
                }
            }
        };

        timer.start();
    }

    private void checkBulletCollision(ImageView bullet, AnimationTimer bulletTimer) {
        Iterator<ImageView> enemyIterator = enemy.enemies.iterator();

        while (enemyIterator.hasNext()) {
            ImageView currentEnemy = enemyIterator.next();

            // Check if the bullet intersects with an enemy
            if (bullet.getBoundsInParent().intersects(currentEnemy.getBoundsInParent())) {
                System.out.println("Enemy hit!");

                // Immediately stop the bullet's animation timer
                bulletTimer.stop();

                // Remove the bullet from the scene and the list
                latar.getChildren().remove(bullet);
                bullets.remove(bullet);

                // Mark the enemy as hit and remove it from the scene
                currentEnemy.setUserData(true); // Mark the enemy as hit
                latar.getChildren().remove(currentEnemy);  // Remove enemy from the scene
                enemyIterator.remove(); // Remove the enemy from the list

                // Increase the score and update the score display
                score += 10;
                updateScoreDisplay();

                return; // No need to check further, stop after the first collision
            }
        }
    }

    public void updateScoreDisplay() {
        scoreText.setText("Score: " + score);
        if (score < 0) {
            handleGameOver();
        }
    }

    public void handleGameOver() {
    if (isGameOver) return;

    isGameOver = true;

    // Display Game Over message
    gameOverText.setVisible(true);

    // Stop spawning enemies after game over
    enemy.enemySpawner.stop();

    // Stop all remaining enemy animations
    for (ImageView enemy : enemy.enemies) {
        enemy.setUserData(true); // Mark all enemies as processed
    }

    // Clear all bullets
    for (ImageView bullet : bullets) {
        latar.getChildren().remove(bullet);
    }
    bullets.clear();

    // Switch to the Game Over screen (gameover.fxml)
    Platform.runLater(this::switchToGameOverScreen); // Ensure this runs on the UI thread
}

    private void switchToGameOverScreen() {
        try {
            // Load the gameover.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            AnchorPane gameOverRoot = loader.load();

            // Get the controller of the gameover.fxml
            GameOverController gameOverController = loader.getController();
            
            // Pass the current game controller to the GameOverController
            gameOverController.setGameController(this);

            // Get the current stage
            Stage stage = (Stage) latar.getScene().getWindow();

            // Create a new scene for the game over screen
            Scene gameOverScene = new Scene(gameOverRoot);

            // Set the new scene
            stage.setScene(gameOverScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Restart the game when Yes button is clicked
    public void restartGame() {
        isGameOver = true;
        score = 0;
        scoreText.setText("Score: 0");
        gameOverText.setVisible(true);

        // Clear the screen and restart the game
        latar.getChildren().clear();
        latar.getChildren().add(character);
        latar.getChildren().add(scoreText);

        // Restart the enemy spawner
        enemy.startEnemySpawner();
    }
}
