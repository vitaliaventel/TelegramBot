package ct.leshchenko.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @AUTHOR : Vitalii Leshchenko
 * @DESCRIPTION :
 * @HISTORY : 30.01.2019 - Created.(VLeshchenko)
 **/
public class ConfigManager {

    private static ConfigManager instance;
    public static Properties properties;

    private ConfigManager() {
        this.initProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void initProperties(){
        properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/resources/app.properties");
            properties.load(inputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
