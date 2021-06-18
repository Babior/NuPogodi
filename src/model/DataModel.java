package model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DataModel {

    private static ObservableList<Player> players = FXCollections.observableArrayList(player ->
            new Observable[]{player.nameProperty(), player.scoreProperty()});

    private ObjectProperty<Player> currentPlayer = new SimpleObjectProperty<>(null);

    public ObservableList<Player> getPlayerList() {
        return players;
    }

    public ObjectProperty<Player> currentPlayerProperty() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayerProperty().set(player);
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scores"))) {
            players = FXCollections.observableList((ArrayList<Player>) ois.readObject());
        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        Collections.sort(players);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("scores");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(new ArrayList<>(players));
            oos.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}