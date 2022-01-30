import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Weather_Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "WWizard_Bot";
    }

    @Override
    public String getBotToken() {
        return "5194737361:AAGHPg4gDMmXE2qUGOgKhhdXMaUvHVLDFlE";
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            SendMessage message = new SendMessage();
            message.setText(fetchData(update.getMessage().getText()));
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new
                TypeToken<HashMap<String, Object>>() {
                }.getType());
    }

    private String fetchData(String city) {

        String API_KEY = "8615284ef09af9278dd3a9645398259e";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection connect = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
            System.out.println(result);

            Map<String, Object> respMap = jsonToMap(result.toString());
            LinkedTreeMap weatherMap = (LinkedTreeMap) ((ArrayList) respMap.get("weather")).get(0);
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
            String weather = "Current weather: " + weatherMap.get("main") + "\n";
            String temperature = "Current temperature: " + mainMap.get("temp") + "°С \n";
            String feels = "Feels like: " + mainMap.get("feels_like") + "°С \n";
            String humidity = "Humidity: " + mainMap.get("humidity") + "% \n";
            String visibility = "Visibility: " + respMap.get("visibility") + " м \n";
            String wind = "Speed of the wind: " + windMap.get("speed") + " m/s \n";

            System.out.println(weather + temperature + feels + humidity + visibility + wind);
            return(weather + temperature + feels + humidity + visibility + wind);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
