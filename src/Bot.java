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
        sendMessage(channel, sender + ": Enter Covid 'Country_Name' 'parameter' to get the current details about" +
                " the havoc being caused by COVID-19. Example -> Covid India active  OR  Covid Germany Deaths");
        sendMessage(channel, sender + ": Parameter Types for Covid -> Deaths, Active, Recovered, Critical, Tests");
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
                if (WeatherBot.weatherZip(callZip) == 0.0) {
                    sendMessage(channel, sender + ": Not a valid United States ZIP Code OR API Cannot return Data");
                }
                else {
                    sendMessage(channel, sender + ": Temperature at ZIP Code " + callZip + " is " + WeatherBot.weatherZip(callZip) + " Fahrenheit");
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
                if (WeatherBot.weatherCity(callCity) == 0.0) {
                    sendMessage(channel, sender + ": Not a valid United States City Name OR API Cannot return Data");
                }
                else {
                    sendMessage(channel, sender + ": Temperature in " + callCity + " is " + WeatherBot.weatherCity(callCity) + " Fahrenheit");
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
                if (CovidBot.covid(covidCountry, covidType) == 0) {
                    sendMessage(channel, sender + ": Not a valid Country Name or Parameter for the number of Cases");
                }
                else {
                    sendMessage(channel, sender + ": The number of Coronavirus " + covidType +" in " + covidCountry + " is " + CovidBot.covid(covidCountry, covidType));
                }
            } catch (IOException | ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            sendMessage(channel, sender + ": Not a Valid ProjectBot Command.");
        }
    }
}