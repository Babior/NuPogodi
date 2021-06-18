package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.MenuButton;
import model.MenuSubScene;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int MENU_BUTTONS_START_X = 50;
    private static final int MENU_BUTTONS_START_Y = 200;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private MenuSubScene highScoresListSubScene;

    List<MenuButton> menuButtons;

    public MenuView() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createBackground();
        //createLogo();
        createButtons();
        createSubScene();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createBackground() {
        Image backgroundImage = new Image("/view/resources/background.png", 800, 700, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("/view/resources/logo.png");
        logo.setLayoutX(380);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(event -> logo.setEffect(new DropShadow()));
        logo.setOnMouseExited(event -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }

    private void addMenuButtons(MenuButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createNewGameButton();
        createHighScoresButton();
        createExitButton();
    }

    private void createNewGameButton() {
        MenuButton newGame = new MenuButton("New Game");
        addMenuButtons(newGame);

        newGame.setOnAction(actionEvent -> {
            GameView gameManager = new GameView();
            gameManager.createNewGame(mainStage);
        });
    }

    private void createHighScoresButton() {
        MenuButton highScores = new MenuButton("High Scores");
        addMenuButtons(highScores);

        highScores.setOnAction(actionEvent -> {
            showSubScene(highScoresListSubScene);
        });
    }

    private void createExitButton() {
        MenuButton exitButton = new MenuButton("Exit");
        addMenuButtons(exitButton);

        exitButton.setOnAction(actionEvent -> {
            mainStage.close();
            System.exit(0);
        });
    }

    public void createSubScene() {
        highScoresListSubScene = new MenuSubScene();
        mainPane.getChildren().add(highScoresListSubScene);
    }

    public void showSubScene(MenuSubScene subScene) {
        subScene.moveSubScene();
    }
}
