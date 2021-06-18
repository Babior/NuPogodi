package model;

import controller.ListController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class MenuSubScene extends SubScene {

    private static final String BACKGROUND_IMAGE = "/model/resources/panel.png";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private boolean isHidden;
    private DataModel model;
    private ListController listController;

    public MenuSubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(WIDTH);
        prefHeight(HEIGHT);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 400, 300, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        AnchorPane root3 = new AnchorPane();
        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("../controller/list.fxml"));
        try {
            root3.getChildren().add(listLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        listController = listLoader.getController();
        root3.setLayoutX(70);
        root3.setLayoutY(5);
        root2.getChildren().add(root3);

        model = new DataModel();
        model.loadData();
        listController.initModel(model);

        isHidden = true;
        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }
        transition.play();
    }
}
