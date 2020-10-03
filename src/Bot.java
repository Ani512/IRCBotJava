import org.jibble.pircbot.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Bot extends PircBot {
    public Bot() {
        this.setName("ProjectBot");
    }

    public void onJoin(String channel, String sender, String message, String hostname) {
        sendMessage(channel, sender + ": Welcome to Channel " + channel + " !");
        sendMessage(channel, sender + ": Enter a Valid United States ZIP Code or the City Name in any Country to" +
                " find the temperature at that Location. You may enter it with or without using the word 'Weather' as the predecessor");
        sendMessage(channel, sender + ": Enter Covid 'Valid_Country_Name' 'Parameter' to get the current details about" +
                " the havoc being caused by COVID-19. Example -> Covid India active  (OR)  Covid Germany Deaths");
        sendMessage(channel, sender + ": Parameter Types for Covid -> Deaths, Active, Recovered, Critical, Tests");
        sendMessage(channel, sender + ": Type 'Disconnect Bot' to end the connection with the Remote Host (The Bot Will Exit)");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }

        else if (message.matches("^[Ww][Ee][Aa][tT][Hh][Ee][rR] [0-9]{5}(?:-[0-9]{4})?$") || message.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
            String[] words = message.split(" ");
            String callZip = "";

            if (words.length ==1) {
                callZip = words[0];
            } else if (words.length ==2) {
                callZip = words[1];
            }

            try {
                if (WeatherBot.weatherZip(callZip) == null) {
                    sendMessage(channel, sender + ": Not a valid United States ZIP Code (OR) The API Cannot return Data");
                }
                else {
                    double[] tempWeather = WeatherBot.weatherZip(callZip);
                    sendMessage(channel, sender + ": Temperature at ZIP Code " + callZip + " is " + tempWeather[0] + " Fahrenheit. Feels like "+ tempWeather[1] + " Fahrenheit");
                }
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        }

        else if (message.matches("^[Ww][Ee][Aa][tT][Hh][Ee][rR] [A-Za-z]+$") || message.matches("^[A-Za-z]+$")) {
            String[] words = message.split(" ");
            String callCity = "";

            if (words.length ==1) {
                callCity = words[0];
            } else if (words.length ==2) {
                callCity = words[1];
            }

            try {
                if (WeatherBot.weatherCity(callCity) == null) {
                    sendMessage(channel, sender + ": Not a valid City Name (OR) The API Cannot return Data");
                }
                else {
                    double[] tempWeather = WeatherBot.weatherCity(callCity);
                    sendMessage(channel, sender + ": Temperature in " + callCity + " is " + tempWeather[0] + " Fahrenheit. Feels like " + tempWeather[1] + " Fahrenheit");
                }
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        }

        else if (message.matches("^[Cc][Oo][Vv][Ii][Dd] ([A-Za-z]+ )[A-Za-z]+$")) {
            String[] words = message.split(" ");
            String covidCountry = words[1];
            String covidType = words[2];

            try {
                if (CovidBot.covid(covidCountry, covidType) == -1L || CovidBot.covid(covidCountry, covidType) == -1) {
                    sendMessage(channel, sender + ": Invalid Valid Country Name (OR) Invalid Parameter for the number of Cases");
                }
                else {
                    sendMessage(channel, sender + ": The number of Coronavirus " + covidType +" in " + covidCountry + " is " + CovidBot.covid(covidCountry, covidType));
                }
            } catch (IOException | ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        else if (message.toLowerCase().matches("^disconnect bot")) {
            dispose();
        }

        else {
            sendMessage(channel, sender + ": Not a Valid ProjectBot Command.");
        }
    }
}