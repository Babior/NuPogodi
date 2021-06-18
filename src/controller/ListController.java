package controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.DataModel;
import model.Player;

public class ListController {

    @FXML
    private ListView<Player> listView;

    private DataModel model;

    public void initModel(DataModel model) {
        this.model = model;
        listView.setPrefHeight(250);
        listView.setItems(model.getPlayerList());
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
                model.setCurrentPlayer(newSelection));

        model.currentPlayerProperty().addListener((obs, oldPlayer, newPlayer) -> {
            if (newPlayer == null) {
                listView.getSelectionModel().clearSelection();
            } else {
                listView.getSelectionModel().select(newPlayer);
            }
        });

        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(player.getName() + " " + player.getScore());
                }
            }
        });
    }
}