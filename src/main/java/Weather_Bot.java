import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private String fetchData(String city) {
        String API_KEY = "8615284ef09af9278dd3a9645398259e";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
        return "Город не найден";
    }
}
