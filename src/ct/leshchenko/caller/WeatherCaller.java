package ct.leshchenko.caller;

/**
 * Created by Vitalii Leshchenko on 09.01.2018.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherCaller {

    private static final String APPID = "63f6349d53ea4bde06506e4d0f86b820";

    public String getHTTPWeather(String city) throws Exception {
        StringBuilder result = new StringBuilder();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + APPID;
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

}
