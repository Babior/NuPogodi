package model;


import javafx.scene.Node;
import javafx.scene.image.Image;

public enum WOLF {

    UP_LEFT(new Image("/model/resources/wolf-l-u.png", 320, 330, false, true)),
    DOWN_LEFT(new Image("/model/resources/wolf-l-d.png", 320, 330, false, true)),
    UP_RIGHT(new Image("/model/resources/wolf-r-u.png", 320, 330, false, true)),
    DOWN_RIGHT(new Image("/model/resources/wolf-r-d.png", 320, 330, false, true));

    Image imgWolf;

    private WOLF(Image imgWolf) {
        this.imgWolf = imgWolf;
    }

    public Image getImgWolf() {
        return imgWolf;
    }



}
