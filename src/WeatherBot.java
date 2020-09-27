import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherBot {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    }

    public static double weatherZip(String zip) throws IOException, InterruptedException, ParseException {
        var url = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&appid=a976894825514685daa64bd031ec6d99";

        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return(temperatureFahrenheit(response.body()));
    }

    public static double weatherCity(String city) throws ParseException, IOException, InterruptedException {
        var url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=a976894825514685daa64bd031ec6d99";

        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return temperatureFahrenheit(response.body());
    }

    public static double temperatureFahrenheit(String weather) throws ParseException {
        JSONParser parser = new JSONParser();
        Object jsonObj = parser.parse(weather);

        JSONObject mainObj = (JSONObject) jsonObj;
        JSONObject main = (JSONObject) mainObj.get("main");
        double temperature = 0, fahrenheit = 0;
        temperature = (double) main.get("temp");
        fahrenheit = (float)Math.round((((temperature*9)/5) - 459.67)*100.0) / 100.0;
        return fahrenheit;
    }
}
