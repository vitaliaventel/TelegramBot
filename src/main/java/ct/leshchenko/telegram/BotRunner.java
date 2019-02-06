package ct.leshchenko.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * Created by Vitalii Leshchenko on 09.01.2018.
 */
public class BotRunner {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new TryBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
