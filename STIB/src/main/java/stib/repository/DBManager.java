package stib.repository;

import stib.config.ConfigManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private DBManager() {
        try {
            String url = ConfigManager.getInstance().getProperties("db.url");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException e) {
            System.out.println("Erreur accès à la base de données");
        }
    }
    private Connection connection;
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Returns the instance of the singleton.
     * @return the instance of the singleton.
     */
    public static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {
        private static final DBManager INSTANCE = new DBManager();
    }
}
