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

// Class for calculating Covid Cases
public class CovidBot {
    public static long covid(String covidCountry, String covidType) throws IOException, InterruptedException, ParseException {
        // User input - (Covid Type Parameter) - Validation
        String[] type = {"recovered", "tests", "active", "deaths", "critical"};       // Available commands array
        boolean flag=false;
        for (String s : type) {
            if (s.equalsIgnoreCase(covidType)) {
                flag = true;
                break;
            }
        }

        // if valid
        if (flag) {
            var url = "https://corona.lmao.ninja/v2/countries/" + covidCountry + "?yesterday&strict&query%20";  // Defining API url

            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();      // Defining the Web request
            var client = HttpClient.newBuilder().build();       // Building the Web request and defining the client

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());  // Getting a response
            int code = response.statusCode();

            // Parsing JSON using built in methods of JSON Simple library
            JSONParser parser = new JSONParser();   // For parsing json
            Object jsonObj = parser.parse(response.body());     // Object of type parser

            JSONObject mainObj = (JSONObject) jsonObj;          // Object of type JSONObject for parsing

            Long covidResponse = (Long) mainObj.get(covidType.toLowerCase());       // Getting output of specified type

            // If Invalid Input (Input Validation for country)
            if(covidResponse == null) {
                covidResponse = -1L;
            }

            // Validation for type of Web Response
            if (code == 200 ) {
                // Status Code 200 for OK
                return covidResponse;
            }
            else {
                return -1L;
            }
        }

        else {
            return -1L;
        }
    }
}
