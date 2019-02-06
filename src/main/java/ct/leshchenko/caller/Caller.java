package ct.leshchenko.caller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @AUTHOR : Vitalii Leshchenko
 * @DESCRIPTION :
 * @HISTORY : 30.01.2019 - Created.(VLeshchenko)
 **/
public class Caller {

    public String getApiCall(String url){
        StringBuilder result = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
