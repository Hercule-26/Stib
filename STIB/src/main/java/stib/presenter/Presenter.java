package stib.presenter;

import stib.dto.StationDto;
import stib.model.Stib;
import stib.util.Observer;
import stib.view.FxmlController;

public class Presenter implements Observer {
    FxmlController fxmlController;
    Stib stib;

    public Presenter(FxmlController fxmlController, Stib stib) {
        this.fxmlController = fxmlController;
        this.stib = stib;
    }
    public void search(Integer idBeginStation, Integer idEndStation) {
        stib.searchWay(idBeginStation, idEndStation);
    }

    public void delete(Integer key) {
        stib.deleteFavorite(key);
    }
    public void addFavorite(StationDto begin, StationDto end) {
        stib.addFavorite(begin, end);
    }
    @Override
    public void update() {
        fxmlController.setWay(stib.getPath());
        fxmlController.setFavorite(stib.getFavorites());
    }
}
