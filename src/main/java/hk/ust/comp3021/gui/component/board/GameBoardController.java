package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import hk.ust.comp3021.gui.utils.Resource;
import hk.ust.comp3021.utils.StringResources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {
        // TODO
        Platform.runLater(() -> {
            try {

                var height = state.getMapMaxHeight();
                var width = state.getMapMaxWidth();
                for (int j = 0; j < height; ++j) {
                    for (int i = 0; i < width; ++i) {
                        Position pos = new Position(i, j);
                        var entity = state.getEntity(pos);
                        Cell c = new Cell();
                        if (entity instanceof Wall) {
                            c.getController().setImage(Resource.getWallImageURL());
                        } else if (entity instanceof Player) {
                            Player player = (Player) entity;
                            c.getController().setImage(Resource.getPlayerImageURL(player.getId()));
                        } else if (entity instanceof Empty) {
                            c.getController().setImage(Resource.getEmptyImageURL());
                        } else if (entity instanceof Box) {
                            Box box = (Box) entity;
                            c.getController().setImage(Resource.getBoxImageURL(box.getPlayerId()));
                        }
                        map.add(c, i, j);
                    }
                }

                for (Position pos : state.getDestinations()) {
                    Cell c = new Cell();
                    c.getController().setImage(Resource.getDestinationImageURL());
                    map.add(c, pos.x(), pos.y());
                    if (state.getEntity(pos) instanceof Box) {
                        c.getController().markAtDestination();
                    }
                }
                if (state.getUndoQuota().isPresent()) {
                    undoQuota.setText(String.format(StringResources.UNDO_QUOTA_TEMPLATE, state.getUndoQuota().get()));
                } else {
                    undoQuota.setText(String.format(StringResources.UNDO_QUOTA_TEMPLATE, StringResources.UNDO_QUOTA_UNLIMITED));
                }


            } catch (IOException e) {
                Message.error("Error", e.getMessage());
            }
        });
    }

    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }
}
