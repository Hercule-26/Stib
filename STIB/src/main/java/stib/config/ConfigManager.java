package stib.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * This singleton gives access to all properties from config.properties.
 */
public class ConfigManager {

    private ConfigManager() {
        prop = new Properties();
        URL URL = Objects.requireNonNull(getClass().getClassLoader().getResource(FILE));
        String urlString = URL.toString();
        URI uri = URI.create(urlString);
        url = uri.getPath();
    }

    private static final String FILE = "./config/config.properties";

    private final Properties prop;

    private final String url;

    /**
     * Loads the properties from this url.
     *
     * @throws IOException if no file is found.
     */
    public void load() throws IOException {
        try (InputStream input = new FileInputStream(url)) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IOException("Chargement configuration impossible ",ex);
        }
    }

    /**
     * Returns the value from the key name.
     *
     * @param name key to found.
     * @return the value from the key-value pair.
     */
    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    /**
     * Returns the instance of the singleton.
     * @return the instance of the singleton.
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
