package ct.leshchenko.caller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vitalii Leshchenko on 10.01.2018.
 */
public class PrivatBankCaller {

    public String getCurrencies() throws Exception {
        StringBuilder result = new StringBuilder();
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
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
