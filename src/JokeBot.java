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

public class JokeBot {
    public static String[] randomJoke() throws IOException, ParseException, InterruptedException {
        var url= ("https://sv443.net/jokeapi/v2/joke/Any?format=json&blacklistFlags=nsfw,sexist,racist,religious,political&type=twopart&lang=en");

        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();      // Defining the Web request
        var client = HttpClient.newBuilder().build();       // Building the Web request and defining the client

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());  // Getting a response
        int resCode = response.statusCode();        // Response Code - 200 Ok

        String[] output = new String[2];    // Creating array to store API Data

        // If invalid response by API
        if (resCode != 200) {
            output[0] = "error";
            output[1] = "null";
        }

        else {
            // Parsing JSON using built in methods of JSON Simple library
            JSONParser parser = new JSONParser();   // For parsing json
            Object jsonObj = parser.parse(String.valueOf(response.body()));     // Object of type parser
            JSONObject mainObj = (JSONObject) jsonObj;          // Object of type JSONObject for parsing

            // Storing JSON data as String in the array and returning it
            String setup = (String) mainObj.get("setup");
            output[0] = setup;
            String delivery = (String) mainObj.get("delivery");
            output[1] = delivery;
        }
        return output;
    }
}
