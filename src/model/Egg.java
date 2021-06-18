package model;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Egg extends ImageView {

    private static final String EGG_IMAGE = "/view/resources/egg.png";
    private boolean isLeft;

    public Egg(boolean isLeft) {
        this.isLeft = isLeft;
        setImage(new Image(EGG_IMAGE, 30, 30, true, true));
        setAnimation();
    }

    public boolean isLeft() {
        return isLeft;
    }

    private void setAnimation() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), this);
        rotateTransition.setFromAngle(0);
        if (isLeft) {
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(360);
        } else {
            rotateTransition.setFromAngle(360);
            rotateTransition.setToAngle(0);
        }
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }

    public void freeFall() {
        PathTransition pathTransition = new PathTransition();
        Path path = new Path();
        path.getElements().add(new MoveTo(285, 180));
        path.getElements().add(new LineTo(385, 300));

        pathTransition.setNode(this);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setDuration(new Duration(3000));
        pathTransition.setAutoReverse(false);
        pathTransition.play();

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), this);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, pathTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.setAutoReverse(true);
        parallelTransition.play();
    }
}
