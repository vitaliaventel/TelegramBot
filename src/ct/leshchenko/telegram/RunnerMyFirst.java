package ct.leshchenko.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Vitalii Leshchenko on 09.01.2018.
 */
public class RunnerMyFirst {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new MyFirst());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

}
