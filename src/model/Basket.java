package model;

import javafx.scene.shape.Circle;

public class Basket extends Circle {

    public Basket() {
        setCenterX(370);
        setCenterY(380);
        setRadius(30);
    }


    public void setUpLeft() {
        setCenterX(370);
        setCenterY(260);
    }

    public void setDownLeft() {
        setCenterX(370);
        setCenterY(380);
    }

    public void setUpRight() {
        setCenterX(520);
        setCenterY(260);
    }

    public void setDownRight() {
        setCenterX(520);
        setCenterY(380);
    }
}
