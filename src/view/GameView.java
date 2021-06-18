package view;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.GlobalKeyListener;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class GameView {
    private static final int GAME_WIDTH = 900;
    private static final int GAME_HEIGHT = 600;
    private static final String BACKGROUND_IMAGE = "/view/resources/back.png";
    private static final String BROKEN_EGG_IMAGE = "/view/resources/broken-egg-count.png";

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private ImageView wolf;
    private Basket basket;
    private AnimationTimer gameTimer;
    private ArrayList<ControlButton> controlBtns;
    private OptionsButton timeBtn;
    private int time;
    private ArrayList<Egg> eggs;
    private Random positionGenerator;
    private InfoLabel infoLabel;
    private int points;
    private ImageView[] brokenEggs;
    private int brokenEgg;
    private boolean isRunning = true;


    public GameView() {
        initialiseStage();
        createKeyListeners();
        startPassOfTime();
        positionGenerator = new Random();
    }

    public ImageView getWolf() {
        return wolf;
    }

    public Basket getBasket() {
        return basket;
    }

    public ArrayList<ControlButton> getControlBtns() {
        return controlBtns;
    }

    public OptionsButton getTimeBtn() {
        return timeBtn;
    }

    private void initialiseStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createKeyListeners() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(this));

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createWolf();
        createBasket();
        createButtons();
        createEggs();
        createGameLoop();
        gameStage.show();
    }

    private void createBackground() {
        GridPane gridPane = new GridPane();
        ImageView backgroundImage1 = new ImageView(new Image(BACKGROUND_IMAGE, GAME_WIDTH, GAME_HEIGHT, false, true));
        gridPane.getChildren().add(backgroundImage1);
        gamePane.getChildren().add(gridPane);
    }

    private void createWolf() {
        wolf = new ImageView(WOLF.DOWN_LEFT.getImgWolf());
        wolf.setLayoutX(285);
        wolf.setLayoutY(GAME_HEIGHT - 390);
        gamePane.getChildren().add(wolf);
    }

    private void createBasket() {
        basket = new Basket();
        basket.setFill(Color.TRANSPARENT);
        gamePane.getChildren().add(basket);
    }

    private void createButtons() {
        brokenEgg = 3;
        infoLabel = new InfoLabel("POINTS: 00");
        infoLabel.setLayoutX(250);
        infoLabel.setLayoutY(90);
        gamePane.getChildren().add(infoLabel);

        controlBtns = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            controlBtns.add(new ControlButton());
            gamePane.getChildren().add(controlBtns.get(i));
        }

        controlBtns.get(0).setLayoutX(20);
        controlBtns.get(0).setLayoutY(300);
        controlBtns.get(1).setLayoutX(20);
        controlBtns.get(1).setLayoutY(450);
        controlBtns.get(2).setLayoutX(GAME_WIDTH - 120);
        controlBtns.get(2).setLayoutY(300);
        controlBtns.get(3).setLayoutX(GAME_WIDTH - 120);
        controlBtns.get(3).setLayoutY(450);

        timeBtn = new OptionsButton();
        gamePane.getChildren().add(timeBtn);
        timeBtn.setLayoutX(GAME_WIDTH - 120);
        timeBtn.setLayoutY(100);
        Label lbl = new Label("Time");
        gamePane.getChildren().add(lbl);
        lbl.setFont(Font.loadFont(getClass().getResourceAsStream("/model/resources/kenvector-future.ttf"), 23));
        lbl.setLayoutX(GAME_WIDTH - 105);
        lbl.setLayoutY(140);
    }

    private void createEggs() {
        eggs = new ArrayList<>();
        eggs.add(new Egg(true));
        eggs.add(new Egg(false));
        for (Egg egg : eggs) {
            setNewEggPosition(egg);
            gamePane.getChildren().add(egg);
        }

        brokenEggs = new ImageView[4];
        for (int i = 0; i < brokenEggs.length; i++) {
            brokenEggs[i] = new ImageView(new Image(BROKEN_EGG_IMAGE, 50, 50, true, true));
            brokenEggs[i].setLayoutX(500 + (i * 50));
            brokenEggs[i].setLayoutY(90);
            gamePane.getChildren().add(brokenEggs[i]);
        }
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveEggs();
                checkIfEggCached();
            }
        };
        gameTimer.start();
    }

    private void setNewEggPosition(Egg image) {
        Map<Integer, Integer[]> positions = new HashMap<>();
        positions.put(0, new Integer[]{140, 150});
        positions.put(1, new Integer[]{140, 270});
        positions.put(2, new Integer[]{GAME_WIDTH - 170, 150});
        positions.put(3, new Integer[]{GAME_WIDTH - 170, 270});

        int randomIndex;
        if (image.isLeft()) {
            randomIndex = positionGenerator.nextInt(2);
        } else {
            randomIndex = positionGenerator.nextInt(2) + 2;
        }
        int randomX = positions.get(randomIndex)[0];
        int randomY = positions.get(randomIndex)[1];

        image.setLayoutX(randomX);
        image.setLayoutY(randomY);
    }

    private void moveEggs() {
        eggs.get(0).setLayoutX(eggs.get(0).getLayoutX() + 2);
        eggs.get(0).setLayoutY(eggs.get(0).getLayoutY() + 0.9);

        eggs.get(1).setLayoutX(eggs.get(1).getLayoutX() - 2);
        eggs.get(1).setLayoutY(eggs.get(1).getLayoutY() + 0.9);
    }

    private void removeLife() {
        gamePane.getChildren().remove(brokenEggs[brokenEgg]);
        brokenEgg--;
        if (brokenEgg < 0) {
            inputPlayer();
            endGame();
        }
    }

    public void endGame() {
        isRunning = false;
        gameStage.close();
        gameTimer.stop();
        menuStage.show();
    }

    private void checkIfEggCached() {
        String textToSet = "";
        for (Egg egg : eggs) {
            if (basket.intersects(egg.getBoundsInParent())) {
                setNewEggPosition(egg);
                points++;
            }
            if (timeBtn.getPressed()) {
                textToSet = "TIME : ";
                infoLabel.setText(textToSet + time);
            } else {
                textToSet = "POINTS : ";
                infoLabel.setText(textToSet + points);
            }

            if (egg.getLayoutX() == GAME_WIDTH / 2) {
                //egg.freeFall();
                setNewEggPosition(egg);
                removeLife();
            }
        }
    }

    public void startPassOfTime() {
        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                    time += 1;
                    System.out.println(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void inputPlayer() {
        Platform.runLater(() -> {
            PlayerInput dialog = new PlayerInput();
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                new Player(name, points);
            });
        });
    }
}
