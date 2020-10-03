import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CovidBot {
    public static long covid(String covidCountry, String covidType) throws IOException, InterruptedException, ParseException {
        String[] type = {"recovered", "tests", "active", "deaths", "critical"};
        boolean flag=false;
        for (int pos=0 ; pos<type.length ; pos++) {
            if (type[pos].equalsIgnoreCase(covidType)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            var url = "https://corona.lmao.ninja/v2/countries/" + covidCountry + "?yesterday&strict&query%20";

            var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
            var client = HttpClient.newBuilder().build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int code = response.statusCode();

            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(response.body());

            JSONObject mainObj = (JSONObject) jsonObj;

            Long covidResponse = (Long) mainObj.get(covidType.toLowerCase());

            if(covidResponse == null) {
                covidResponse = -1L;
            }

            if (code == 200 ) {
                return covidResponse;
            }
            else {
                return -1;
            }
        }

        else {
            return -1;
        }
    }
}
