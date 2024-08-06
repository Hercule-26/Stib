package stib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import stib.dto.PairStationDto;
import stib.dto.StationDto;
import stib.model.Node;
import stib.presenter.Presenter;

import java.util.List;

public class FxmlController {
    @FXML
    public SearchableComboBox<StationDto> end;
    @FXML
    private SearchableComboBox<StationDto> begin;
    @FXML
    private TableView<Node> table;
    @FXML
    private TableColumn<stib.model.Node, String> stationsColumn;
    @FXML
    private TableColumn<Node, String> lineColumn;
    @FXML
    private SearchableComboBox<PairStationDto> favorite;
    @FXML
    private Button delete;
    @FXML
    private Button select;
    private Presenter presenter;

    public FxmlController() {
    }

    public void initialize() {
        ObservableList<Node> stations = FXCollections.observableArrayList();
        table.setItems(stations);
        stationsColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        lineColumn.setCellValueFactory(new PropertyValueFactory<>("LinesToString"));
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void initSearchableComboBox(List<StationDto> stations) {
        ObservableList<StationDto> list = FXCollections.observableArrayList(stations);
        begin.setItems(list);
        end.setItems(list);
        ObservableList<PairStationDto> favorites = FXCollections.observableArrayList();
        favorite.setItems(favorites);
    }
    public void setWay(List<Node> list) {
        ObservableList<Node> obsList = FXCollections.observableArrayList(list);
        table.setItems(obsList);
    }
    public void setFavorite(List<PairStationDto> list) {
        ObservableList<PairStationDto> obsList = FXCollections.observableArrayList(list);
        favorite.setItems(obsList);
    }
    @FXML
    private void handleSearchBtnAction(ActionEvent event) {
        StationDto beginStation = begin.getValue();
        StationDto endStation = end.getValue();
        if (beginStation != null || endStation != null) {
            presenter.search(beginStation.getKey(), endStation.getKey());
        }
    }
    @FXML
    private void handleSwapBtnAction(ActionEvent event) {
        StationDto beginStation = begin.getValue();
        StationDto endStation = end.getValue();
        if (beginStation != null && endStation != null) {
            begin.setValue(endStation);
            end.setValue(beginStation);
        }
    }
    @FXML
    private void handleDeleteFavorite(ActionEvent event) {
        PairStationDto pairStation = favorite.getValue();
        if (pairStation != null) {
            presenter.delete(pairStation.getKey());
        }
    }
    @FXML
    private void handleSelectFavorite(ActionEvent event) {
        PairStationDto pairStation = favorite.getValue();
        if (pairStation != null) {
            begin.setValue(pairStation.getStationBegin());
            end.setValue(pairStation.getStationEnd());
        }
    }
    @FXML
    private void handleAddFavorite() {
        StationDto beginStation = begin.getValue();
        StationDto endStation = end.getValue();
        if (beginStation != null && endStation != null) {
            presenter.addFavorite(beginStation, endStation);
        }
    }
}