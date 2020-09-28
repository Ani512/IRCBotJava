import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CovidBot {
    public static void main(String[] args) {
    }

    public static long covid(String covidCountry, String covidType) throws IOException, InterruptedException, ParseException {
        var url = "https://corona.lmao.ninja/v2/countries/" + covidCountry + "?yesterday&strict&query%20";

        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();

        JSONParser parser = new JSONParser();
        Object jsonObj = parser.parse(response.body());

        JSONObject mainObj = (JSONObject) jsonObj;
        long covidResponse = -1;
        covidResponse = (long) mainObj.get(covidType.toLowerCase());

        if (code==200 && covidResponse!=-1) {
            return covidResponse;
        }
        else {
            return 0;
        }
    }
}
