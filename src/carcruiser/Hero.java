/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carcruiser;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ACER
 */
public class Hero extends GameObj implements IHero{
    private AnchorPane latar;
    private ImageView character;
    private FXMLDocumentController controller;
    
    private double step = 10; // Movement step for the character

    public Hero(AnchorPane latar, ImageView character, FXMLDocumentController controller) {
        super(latar, character, controller);
    }
    
    @Override
    public void moveCharacterUp() {
        double currentY = getCharacter().getLayoutY();
        if (currentY - step >= 0) { // Prevent moving outside the top boundary
            getCharacter().setLayoutY(currentY - step);
        }
    }

    @Override
    public void moveCharacterDown() {
        double currentY = getCharacter().getLayoutY();
        double latarHeight = getLatar().getBoundsInParent().getHeight();
        double characterHeight = getCharacter().getBoundsInParent().getHeight();

        // Check if the character can move down
        if (currentY + step <= latarHeight - characterHeight) {
            getCharacter().setLayoutY(currentY + step);
        }
    }
}
