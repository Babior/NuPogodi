package model;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerInput extends TextInputDialog {

    private static final String IMAGE = "/model/resources/wolf.png";

    public PlayerInput() {
        setTitle("Name input");
        setHeaderText("GAME OVER");
        setContentText("Please enter your name:");
        Image icon = new Image(IMAGE, 70, 70, true, true);
        setGraphic(new ImageView(icon));
    }
    
}
