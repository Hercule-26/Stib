package stib;

import stib.config.ConfigManager;
import stib.model.DijkstraAlgorithm;
import stib.model.Graph;
import stib.model.Node;
import stib.view.MainView;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Launch {
    public static void main(String[] args) throws IOException {
        ConfigManager.getInstance().load();
        MainView.main(args);
    }
}
