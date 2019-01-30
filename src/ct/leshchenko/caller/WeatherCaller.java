package ct.leshchenko.caller;

/**
 * Created by Vitalii Leshchenko on 09.01.2018.
 */

import ct.leshchenko.config.ConfigManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class  WeatherCaller {

    private Caller caller;

    public WeatherCaller(){

    }

    public WeatherCaller(Caller caller){
        this.caller = caller;
    }

    public String getWeatherByCity(String city)  {
        return caller.getApiCall(String.format(ConfigManager.getInstance().properties.getProperty("GET_WEATHER_BY_CITY_URL"), city));
    }

}
