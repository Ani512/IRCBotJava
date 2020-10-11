// Importing the classes and methods of external library Json Simple to Parse Json returned by the API
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Uses classes of type java.net to access the web
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JokeBot {
    public static String[] randomJoke() throws IOException, ParseException {
        URL url = new URL("https://sv443.net/jokeapi/v2/joke/Any?format=json&blacklistFlags=nsfw,sexist,racist,religious,political&type=twopart&lang=en");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        int resCode = connection.getResponseCode();
        String[] output = new String[2];

        if (resCode != 200) {
            output[0] = "error";
            output[1] = "null";
        }

        else {
            Scanner sc = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }
            System.out.println("JSON Variable in String format");
            sc.close();

            // Parsing JSON using built in methods of JSON Simple library
            JSONParser parser = new JSONParser();   // For parsing json
            Object jsonObj = parser.parse(String.valueOf(inline));     // Object of type parser
            JSONObject mainObj = (JSONObject) jsonObj;          // Object of type JSONObject for parsing

            String joketype = (String) mainObj.get("type");

            if (joketype.equalsIgnoreCase("single")) {
                String joke = (String) mainObj.get("joke");
                output[0] = joke;
                output[1] = "null";
            }
            else if (joketype.equalsIgnoreCase("twopart")) {
                String setup = (String) mainObj.get("setup");
                output[0] = setup;
                String delivery = (String) mainObj.get("delivery");
                output[1] = delivery;
            }
        }
        return output;
    }
}
