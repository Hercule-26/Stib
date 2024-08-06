package stib.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import stib.dto.PairStationDto;
import stib.exception.RepositoryException;
import stib.model.Stib;
import stib.presenter.Presenter;
import stib.repository.FavoriteRepository;
import stib.repository.StationRepository;

import java.io.IOException;
import java.util.List;

public class MainView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));

            Parent root = loader.load();  // Charger la vue avant de faire le Controller
            FxmlController fxmlController = loader.getController();

            Stib stib = new Stib();
            stib.setFavorite();
            Presenter presenter = new Presenter(fxmlController, stib);

            stib.register(presenter);
            fxmlController.setPresenter(presenter);

            StationRepository repository = new StationRepository();
            fxmlController.initSearchableComboBox(repository.getAll());


            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (RepositoryException e) {
            System.out.println("Error in Select all for init Searchable Combo Box ");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
}
