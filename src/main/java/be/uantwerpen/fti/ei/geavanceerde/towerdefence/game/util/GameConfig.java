package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Charge et expose les paramètres du fichier config.properties.
 * Singleton — une seule instance dans tout le programme.
 */
public class GameConfig {

    private static GameConfig instance;
    private final Properties props = new Properties();

    private GameConfig() {
        try (InputStream in = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("Impossible de charger config.properties : "
                    + e.getMessage());
        }
    }

    public static GameConfig getInstance() {
        if (instance == null) instance = new GameConfig();
        return instance;
    }

    public String getString(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(props.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
