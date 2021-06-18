package model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Player implements Serializable, Comparable<Player> {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty score = new SimpleIntegerProperty();
    private static ObservableList<Player> players = FXCollections.observableArrayList(player ->
            new Observable[]{player.nameProperty(), player.scoreProperty()});

    public Player(String name, int score) {
        setName(name);
        setScore(score);
        DataModel.addPlayer(this);
        DataModel.saveData();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public static ObservableList<Player> getPlayers() {
        return players;
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException {
        out.writeObject(getName());
        out.writeObject(getScore());
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        name = new SimpleStringProperty((String) in.readObject());
        score = new SimpleIntegerProperty((Integer) in.readObject());
    }

    @Override
    public String toString() {
        return "Player{" + name + score + '}';
    }

    @Override
    public int compareTo(Player o) {
        return o.getScore() - this.getScore();
    }
}
