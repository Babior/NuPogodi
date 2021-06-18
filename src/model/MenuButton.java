package model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;


public class MenuButton extends Button {

    private final String FONT_PATH = "/model/resources/kenvector-future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/blue-button-pressed.png');";
    private final String BUTTON_RELEASED_STYLE ="-fx-background-color: transparent; -fx-background-image: url('/model/resources/blue-button.png');";

    public MenuButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_RELEASED_STYLE);
        initialiseButtonListeners();
    }

    private void setButtonFont() {
        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_RELEASED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initialiseButtonListeners() {
        setOnMousePressed(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(mouseEvent -> setEffect(new DropShadow()));

        setOnMouseExited(mouseEvent -> setEffect(null));
    }
}
