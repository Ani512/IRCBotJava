// Importing the classes and methods of external library Json Simple to Parse Json returned by the API
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Uses classes of type java.net to access the web
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Class to calculate the weather
public class WeatherBot {
    // Method to call the API with parameter as zip code which it will get from the user and Return an array of doubles
    public static double[] weatherZip(String zip) throws IOException, InterruptedException, ParseException {
        var url = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&appid=a976894825514685daa64bd031ec6d99"; // API
        return weatherOutput(url);  // Calling a function to build the response
    }

    // Method to call the API with parameter as City Name which it will get from the user and Return an array of doubles
    public static double[] weatherCity(String city) throws ParseException, IOException, InterruptedException {
        // Only the API Call varies everything else is the same
        var url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=a976894825514685daa64bd031ec6d99";
        return weatherOutput(url);  // Calling a function to build the response
    }

    // Private method to return the value of the array containing output for the given input
    private static double[] weatherOutput(String url) throws IOException, InterruptedException, ParseException {
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build(); // To request Data from API through GET Method
        var client = HttpClient.newBuilder().build();   // Defining the client of the API Call

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());  // Gets the response as a string
        int code = response.statusCode();   // Gets the response code (200 for OK)

        if (code == 200) {    // if response code OK
            return temperatureFahrenheit(response.body()); // Calls function to Parse JSON and return output
        } else {
            return null;   // return null value
        }
    }

    // Private Method to Parse JSON using the methods of the JsonSimple external Library
    // Stores the data to be returned in an Array
    private static double[] temperatureFahrenheit(String weather) throws ParseException {
        JSONParser parser = new JSONParser();   // Object of type JSONParser
        Object jsonObj = parser.parse(weather); // Parsing the Json string received from the function call

        JSONObject mainObj = (JSONObject) jsonObj;  // Object of type JSONObject to Parse data
        JSONObject main = (JSONObject) mainObj.get("main"); // Calling the "main" part in the response
        double temperature, feelsLike, tempMaximum, tempMinimum;
        double[] tempArray = new double[4];

        // Getting the different type of attributes from the "main" part
        temperature = (double) main.get("temp");
        feelsLike = (double) main.get("feels_like");
        tempMinimum = (double) main.get("temp_min");
        tempMaximum = (double) main.get("temp_max");

        // Adding them to the array after converting them to the type Fahrenheit
        tempArray[0] = convertFahrenheit(temperature);
        tempArray[1] = convertFahrenheit(feelsLike);
        tempArray[2] = convertFahrenheit(tempMinimum);
        tempArray[3] = convertFahrenheit(tempMaximum);

        return tempArray;
    }

    // Method which converts to Fahrenheit
    private static double convertFahrenheit(double temp) {
        double fahrenheit;
        fahrenheit = (float) Math.round((((temp * 9) / 5) - 459.67) * 100.0) / 100.0;   // Converting to Fahrenheit
        return fahrenheit;  // Returning to the function call
    }
}
