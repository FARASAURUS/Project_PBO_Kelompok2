/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carcruiser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author ACER
 */
public class Enemy extends GameObj{
    private AnchorPane latar;
    private ImageView character;
    private FXMLDocumentController controller;
    public Timeline enemySpawner; // Timeline for spawning enemies
    public List<ImageView> enemies = new ArrayList<>(); // List for enemies
    public Random random = new Random();
    
    private double step = 10; // Movement step for the character

    public Enemy(AnchorPane latar, ImageView character, FXMLDocumentController controller) {
        super(latar, character, controller);
    }
    
    
    public void startEnemySpawner() {
        enemySpawner = new Timeline(new KeyFrame(Duration.seconds(1), event -> spawnEnemy()));
        enemySpawner.setCycleCount(Timeline.INDEFINITE);
        enemySpawner.play();
    }

    private void spawnEnemy() {
        if (getController().isGameOver) return; // Stop spawning enemies if the game is over

        String[] enemyImages = {
            "/carcruiser/Image/enemy1.png",
            "/carcruiser/Image/enemy2.png",
            "/carcruiser/Image/enemy3.png"
        };
        String selectedImage = enemyImages[random.nextInt(enemyImages.length)];
        ImageView enemy = new ImageView(new Image(getClass().getResource(selectedImage).toExternalForm()));

        enemy.setFitWidth(100);
        enemy.setFitHeight(100);
        double initialY = random.nextDouble() * (getLatar().getHeight() - 100);
        enemy.setLayoutX(getLatar().getWidth() + 50); // Make sure enemy starts outside the screen
        enemy.setLayoutY(initialY);

        getLatar().getChildren().add(enemy);
        enemies.add(enemy);

        // Add a flag to check if the enemy has been hit or missed
        enemy.setUserData(false); // This flag indicates if the enemy is hit or processed for score deduction

        Timeline enemyAnimation = new Timeline(new KeyFrame(Duration.millis(25), e -> moveEnemy(enemy)));
        enemyAnimation.setCycleCount(Timeline.INDEFINITE);
        enemyAnimation.play();
    }

    private void moveEnemy(ImageView enemy) {
        if (getController().isGameOver) return; // Stop moving enemies if the game is over

        if ((boolean) enemy.getUserData()) {
            return; // Do nothing if enemy is hit or processed
        }

        double currentX = enemy.getLayoutX();

        // Check if enemy collides with the character
        if (enemy.getBoundsInParent().intersects(getCharacter().getBoundsInParent())) {
            // Character collided with enemy, trigger game over
            getController().handleGameOver();
            getLatar().getChildren().remove(enemy);
            enemies.remove(enemy);
        }

        // Remove enemy if it moves off the screen
        if (currentX < -50) {
            if (!(boolean) enemy.getUserData()) {
                // Deduct 20 points if enemy passes the hero
                getController().score -= 20;
                getController().updateScoreDisplay();
                if (getController().score < 0) {
                    getController().handleGameOver();
                    return;
                }
                enemy.setUserData(true); // Mark the enemy as processed
            }
            getLatar().getChildren().remove(enemy);
            enemies.remove(enemy);
        } else {
            enemy.setLayoutX(currentX - 5); // Move enemy to the left
        }
    }
}
