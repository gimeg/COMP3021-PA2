package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.gui.utils.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;

    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {
        Image value = new Image(url.toString());
        playerImage.setImage(value);
    }

    @FXML
    private void moveUp() {
        Action a = new Move.Up(player.getId());
        try {
            ControlPanelController.actionBlockingQueue.put(a);
        } catch (InterruptedException e) {
            Platform.runLater(() -> Message.error("Error", e.getMessage()));
        }
    }

    @FXML
    private void moveDown() {
        Action a = new Move.Down(player.getId());
        try {
            ControlPanelController.actionBlockingQueue.put(a);
        } catch (InterruptedException e) {
            Platform.runLater(() -> Message.error("Error", e.getMessage()));
        }
    }

    @FXML
    private void moveLeft() {
        Action a = new Move.Left(player.getId());
        try {
            ControlPanelController.actionBlockingQueue.put(a);
        } catch (InterruptedException e) {
            Platform.runLater(() -> Message.error("Error", e.getMessage()));
        }
    }

    @FXML
    private void moveRight() {
        Action a = new Move.Right(player.getId());
        try {
            ControlPanelController.actionBlockingQueue.put(a);
        } catch (InterruptedException e) {
            Platform.runLater(() -> Message.error("Error", e.getMessage()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
