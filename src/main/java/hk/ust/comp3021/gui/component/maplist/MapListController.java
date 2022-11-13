package hk.ust.comp3021.gui.component.maplist;

import hk.ust.comp3021.gui.utils.Message;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller logic for {@link  MapList}.
 */
public class MapListController implements Initializable {
    @FXML
    private ListView<MapModel> list;

    public void addNewMap(URL gameMapURL) {
        try {
            MapModel mapModel = MapModel.load(gameMapURL);
            for (MapModel originalMapModel : list.getItems()) {
                if (originalMapModel.file().toUri().toURL().equals(mapModel.file().toUri().toURL())) {
                    list.getItems().remove(originalMapModel);
                    break;
                }
            }
            list.getItems().add(0, mapModel);
        } catch (IOException e) {
            Message.error("Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            Message.error("Error", e.getMessage());
        }
    }

    public void deleteSelectedMap() {
        list.getItems().remove(list.getSelectionModel().selectedItemProperty().get());
    }

    public ReadOnlyObjectProperty<MapModel> getSelectedItemProperty() {
        return list.getSelectionModel().selectedItemProperty();
    }


    /**
     * Initialize the controller.
     * You should customize the items in the list view here.
     * Set the item in the {@link MapList} to be {@link MapListCell}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     * @see <a href="https://docs.oracle.com/javafx/2/ui_controls/list-view.htm">JavaFX ListView</a>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO

        list.setCellFactory(list -> new MapListCell());

    }
}
