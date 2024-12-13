/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carcruiser;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ACER
 */
public class GameObj {
    private AnchorPane latar;
    private ImageView character;
    private FXMLDocumentController controller;

    public GameObj(AnchorPane latar, ImageView character, FXMLDocumentController controller) {
        this.latar = latar;
        this.character = character;
        this.controller = controller;
    }
    
    public AnchorPane getLatar() {
        return latar;
    }

    public void setLatar(AnchorPane latar) {
        this.latar = latar;
    }

    public ImageView getCharacter() {
        return character;
    }

    public void setCharacter(ImageView character) {
        this.character = character;
    }

    public FXMLDocumentController getController() {
        return controller;
    }

    public void setController(FXMLDocumentController controller) {
        this.controller = controller;
    }
    
    
}
