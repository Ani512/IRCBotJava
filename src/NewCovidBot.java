//import org.jibble.pircbot.IrcException;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//
//public class NewCovidBot {
//    public static void main(String[] args) throws IOException, InterruptedException, IrcException {
//        Scanner input = new Scanner(System.in);
//        String zip, userUrl;
//
//        zip = input.next();
//        userUrl = "http://api.openweathermap.org/data/2.5/weather?zip="+zip+",us&appid=a976894825514685daa64bd031ec6d99";
//
//        URL url = new URL(userUrl);
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setRequestMethod("GET");
//        connection.connect();
//
//        int resCode = connection.getResponseCode();
//
//        if (resCode != 200) {
//            throw new RuntimeException("Http Response Code INVALID for GET Method");
//        }
//        else {
//            Scanner sc = new Scanner(url.openStream());
//            StringBuilder inline = new StringBuilder();
//            while(sc.hasNext())
//            {
//                inline.append(sc.nextLine());
//            }
//            System.out.println("JSON Variable in String format");
//            System.out.println(inline);
//            sc.close();
//
//            JSONParser parser = new JSONParser();
//            JSONObject jobj = parser.parse(String.valueOf(inline));
//
//        }
//
//    }
//}
