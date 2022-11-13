package hk.ust.comp3021.gui;

import hk.ust.comp3021.game.GameMap;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.control.ControlPanelController;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import hk.ust.comp3021.gui.utils.Message;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private StartScene startScene;
    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        // TODO: throw Exception if something goes wrong.
        StartScene startScene = new StartScene();
        this.startScene = startScene;
        primaryStage.setScene(startScene);
        primaryStage.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, this::onOpenMap);
        primaryStage.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
        primaryStage.show();
    }


    /**
     * Event handler for opening a map.
     * Swith to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) {
        // TODO
        GameMap gameMap = event.getModel().gameMap();
        GameState gameState = new GameState(gameMap);
        if (gameState.getAllPlayerPositions().size() > 4) {
            Message.error("Error", "4 players at most.");
        }
        try {
            ControlPanelController.actionBlockingQueue.clear();
            GameScene gameScene = new GameScene(gameState);
            primaryStage.setScene(gameScene);
        }
        catch (IOException e) {
            Message.error("Error", e.getMessage());
        }


    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        // TODO
        primaryStage.setScene(this.startScene);
    }
}
