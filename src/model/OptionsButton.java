package model;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

public class OptionsButton extends Button {
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/dark-button-pressed-op.png');";
    private final String BUTTON_RELEASED_STYLE ="-fx-background-color: transparent; -fx-background-image: url('/model/resources/dark-button-op.png');";

    private boolean isPressed = false;

    public OptionsButton() {
        setPrefWidth(100);
        setPrefHeight(31);
        setStyle(BUTTON_RELEASED_STYLE);
        initialiseButtonListeners();
    }

    public boolean getPressed() {
        return isPressed;
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(31);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_RELEASED_STYLE);
        setPrefHeight(31);
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

        setOnAction(actionEvent -> {
            setButtonPressedStyle();
            PauseTransition pt = new PauseTransition(Duration.millis(100));
            pt.setOnFinished(event -> {
                setButtonReleasedStyle();
                if (getPressed()) {
                    isPressed = false;
                } else {
                    isPressed = true;
                }
            });
            pt.play();
        });
    }
}
