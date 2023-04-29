package Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesGetter {

    public static String getUrlProperty(String urlName ) {

        FileInputStream fis;
        Properties property = new Properties();


        String url = null;
        try {
            fis = new FileInputStream("src/main/resources/BaseUrl.properties");
            property.load(fis);

            url = property.getProperty(urlName);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return url;
    }
}
