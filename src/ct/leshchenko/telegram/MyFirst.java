package ct.leshchenko.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import ct.leshchenko.caller.PrivatBankCaller;
import ct.leshchenko.caller.WeatherCaller;
import ct.leshchenko.entity.CurrencyRate;
import ct.leshchenko.utils.WeatherUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitalii Leshchenko on 09.01.2018.
 */
public class MyFirst extends TelegramLongPollingBot {

    /**
     * CONSTANTS
     */
    private static final int TENNIS_OFFSET_HOURS = 2;
    private static final String WEATHER_COMMAND = "/weather";
    private static final String TENNIS_COMMAND = "/tennis";
    private static final String CURRENCY_COMMAND = "/currency";
    private static final String START_COMMAND = "/start";
    private static final String BOT_TOKEN = "498006089:AAHUtC2N5hZx3Fz29LN7E07WcJiL0ptuxP8";
    private static final String BOT_USERNAME = "vtlchbot";
    private static final Map<String, String> DESCRIPTION_EMOJI = new HashMap<String, String>() {{
        put("01", ":sunny:");
        put("02", ":white_sun_behind_cloud:");
        put("03", ":cloud:");
        put("04", ":cloud:");
        put("09", "cloud_with_rain: \n Please take an umbrella :umbrella:, I care about you :kissing_heart:");
        put("10", ":white_sun_behind_cloud_with_rain: \n Please take an umbrella :umbrella:, I care about you :kissing_heart:");
        put("11", ":thunder_cloud_and_rain: \n Please take an umbrella :umbrella:, I care about you :kissing_heart:");
        put("13", "cloud_with_snow");
        put("50", ":alien:");
    }};

    private WeatherCaller weatherCaller;
    private PrivatBankCaller privatBankCaller;
    private Map<Long, LocalDateTime> idWithDate = new HashMap<>();

    public MyFirst() {
        weatherCaller = new WeatherCaller();
        privatBankCaller = new PrivatBankCaller();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            if (messageText.equals(START_COMMAND)) {
                messageText = "Hi " + update.getMessage().getChat().getFirstName() + " ! Lets start: press the / to see the list of available functions";
            } else if (messageText.startsWith(WEATHER_COMMAND) && messageText.contains(" ")) {
                messageText = weatherMessage(messageText.substring(WEATHER_COMMAND.length() + 1));
            } else if (messageText.equals(TENNIS_COMMAND)) {
                messageText = tennisMessage(chatId);
            } else if (messageText.equals(CURRENCY_COMMAND)) {
                messageText = currencyMessage();
            }

            SendMessage message = new SendMessage().setChatId(chatId).setText(messageText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    /**
     * HELPER METHODS
     */

    private String tennisMessage(long chatId) {
        String message = "Hey! It is time to move your body, I'm waiting you in secret room 9!";
        if (idWithDate.get(chatId) == null) {
            idWithDate.put(chatId, LocalDateTime.now().plusHours(TENNIS_OFFSET_HOURS));
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime dateTime = idWithDate.get(chatId);
            if (currentDateTime.compareTo(dateTime) <= 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                message = "YOU CANNOT PLAY TENNIS YET! WORK! Last time you have played at :\n";
                message += dateTime.format(formatter);
                idWithDate.put(chatId, currentDateTime.plusHours(TENNIS_OFFSET_HOURS));
            }
        }
        return message;
    }

    private String weatherMessage(String city) {
        String message = "Current weather in " + city + " is ";
        try {
            String weatherJSON = weatherCaller.getHTTPWeather(city);
            message += String.valueOf(getCurrentTemperature(weatherJSON)) + "C \n";
            message += EmojiParser.parseToUnicode("Current state: " + getDescription(weatherJSON) + DESCRIPTION_EMOJI.get(getIcon(weatherJSON)) + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            message = "Error! Cannot find <" + city + "> city: try again..";
        }
        return message;
    }

    private double getCurrentTemperature(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        return WeatherUtils.round(WeatherUtils.KelvinToCelsius((Double) ((Map<String, Object>) map.get("main")).get("temp")), 2);
    }

    private String getDescription(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        return (String) (((Map<String, Object>) (((List<Object>) map.get("weather"))).get(0)).get("description"));
    }

    private String getIcon(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        String icon = (String) (((Map<String, Object>) (((List<Object>) map.get("weather"))).get(0)).get("icon"));
        return icon.substring(0, icon.length() - 1);
    }

    private String currencyMessage() {
        String message = "No information.";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<CurrencyRate> rates = mapper.readValue(privatBankCaller.getCurrencies(), mapper.getTypeFactory().constructCollectionType(List.class, CurrencyRate.class));
            message = "Privat Bank current rates: \n";
            for (CurrencyRate rate : rates) {
                message += rate.getCurrency() + " <> " + rate.getBaseCurrency() + " is next: buy = " + rate.getBuy() + " , sale = " + rate.getSale() + " \n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
