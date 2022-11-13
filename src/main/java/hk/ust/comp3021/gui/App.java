package hk.ust.comp3021.gui;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.game.GameMap;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.control.ControlPanelController;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import hk.ust.comp3021.gui.utils.Message;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

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
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
            StartScene startScene = new StartScene();
            this.startScene = startScene;
            primaryStage.setScene(startScene);
            primaryStage.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, this::onOpenMap);
            primaryStage.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
            primaryStage.show();
        } catch (Exception ignored) {
            // TODO: Throw Exception if something goes wrong.
        }
    }


    /**
     * Event handler for opening a map.
     * Swith to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) {
        GameMap gameMap = event.getModel().gameMap();
        GameState gameState = new GameState(gameMap);
        if (gameState.getAllPlayerPositions().size() > 4) {
            Message.error("Error", "4 players at most.");
        }
        try {
            ControlPanelController.actionBlockingQueue = new ArrayBlockingQueue<Action>(233);
            GameScene gameScene = new GameScene(gameState);
            primaryStage.setScene(gameScene);
        } catch (IOException e) {
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
        primaryStage.setScene(this.startScene);
    }
}
