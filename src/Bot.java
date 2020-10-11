// Importing classes of Pircbot
import org.jibble.pircbot.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

// Public class to implement Pircbot
public class Bot extends PircBot {
    public Bot() {
        this.setName("AniProjectBot"); // BotName
    }

    // When a new user Joins or the bot joins
    public void onJoin(String channel, String sender, String message, String hostname) {
        sendMessage(channel, sender + ": Welcome to Channel " + channel + " !");
        sendMessage(channel, sender + ": Enter a Valid United States ZIP Code or the City Name to" +
                " find the temperature at that Location. You may enter it with or without using the word 'Weather' as the predecessor");
        sendMessage(channel, sender + ": Enter Covid 'Valid_Country_Name' 'Parameter' to get the current details about" +
                " the havoc being caused by COVID-19. Example : Covid India active  (OR)  Covid Germany Deaths");
        sendMessage(channel, sender + ": Parameter Types for Covid : Deaths, Active, Recovered, Critical, Tests");
        sendMessage(channel, sender + ": Type 'Disconnect Bot' to end the connection with the Remote Host (The Bot Will Exit)");
    }

    // As soon as the user receives a message on the IRC Server
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }

        // Checking if the user input matches the Regular Expression for Weather Zipcode or Zipcode
        else if (message.matches("^[Ww][Ee][Aa][tT][Hh][Ee][rR] [0-9]{5}(?:-[0-9]{4})?$") || message.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
            String[] words = message.split(" ");   // Splitting user input into parts
            String callZip = "";

            // Accessing the zipCode according to the type of user Input
            if (words.length ==1) {
                callZip = words[0];
            } else if (words.length ==2) {
                callZip = words[1];
            }

            // Try and catch expressions used to catch any undocumented errors
            try {
                if (WeatherBot.weatherZip(callZip) == null) {
                    // Checking for invalid data by calling the function
                    sendMessage(channel, sender + ": Not a valid United States ZIP Code (OR) The API Cannot return Data");
                }
                else {
                    // Output using function call
                    double[] tempWeather = WeatherBot.weatherZip(callZip);
                    sendMessage(channel, sender + ": Temperature at ZIP Code " + callZip + " is " + tempWeather[0] + " Fahrenheit. Feels like "+ tempWeather[1] + " Fahrenheit");
                    sendMessage(channel, sender + ": Minimum Temperature " + tempWeather[2] + " Fahrenheit. Maximum Temperature " + tempWeather[3] + " Fahrenheit");
                }
            } catch (IOException | InterruptedException | ParseException e) {
                sendMessage(channel, sender + ": Command Error");
                e.printStackTrace();
            }
        }

        // Matching Regex for getting a valid City Name or Weather City Name
        // Same implementation as ZipCode API
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
                    sendMessage(channel, sender + ": Minimum Temperature " + tempWeather[2] + " Fahrenheit. Maximum Temperature " + tempWeather[3] + " Fahrenheit");
                }
            } catch (IOException | InterruptedException | ParseException e) {
                sendMessage(channel, sender + ": Command Error");
                e.printStackTrace();
            }
        }

        // Matching Regex for input type COVID for implementing the Coronavirus API
        else if (message.matches("^[Cc][Oo][Vv][Ii][Dd] ([A-Za-z]+ )[A-Za-z]+$")) {
            String[] words = message.split(" "); // Splitting user Input
            String covidCountry = words[1]; // Assigning values for function call
            String covidType = words[2];

            // Try and catch expression to get any undocumented errors
            try {
                if (CovidBot.covid(covidCountry, covidType) == -1L || CovidBot.covid(covidCountry, covidType) == -1) {
                    // Checking for invalid user command to see empty JSON Value
                    sendMessage(channel, sender + ": Invalid Valid Country Name (OR) Invalid Parameter for the number of Cases");
                }
                else {
                    // Calling the function to get the output
                    sendMessage(channel, sender + ": The number of Coronavirus " + covidType +" in " + covidCountry + " is " + CovidBot.covid(covidCountry, covidType));
                }
            } catch (IOException | ParseException | InterruptedException e) {
                sendMessage(channel, sender + ": Command Error");
                e.printStackTrace();
            }
        }

        else if (message.toLowerCase().contains("random joke")) {
            try {
                String[] temp = JokeBot.randomJoke();

                if (temp[0].equalsIgnoreCase("error")) {
                    sendMessage(channel, sender + ": JokeAPI is Currently down for Development. Please try again later");
                }
                else if (temp[1].equalsIgnoreCase("null")) {
                        sendMessage(channel, sender + ": " + temp[0]);
                    }
                else {
                    sendMessage(channel, sender + ": " + temp[0]);
                    sendMessage(channel, sender + ": " + temp[1]);
                }
            } catch (IOException | ParseException e) {
                System.out.println("The Biggest Joke is this Joke API");
            }
        }

        // Message to Disconnect Bot
        else if (message.toLowerCase().matches("^disconnect bot")) {
            dispose();      // Ends the connection between the IRC Server and the Bot
        }

        // If user wants a list of commands available
        else if (message.toLowerCase().matches("^get commands")) {
            sendMessage(channel, sender + ": Enter a Valid United States ZIP Code or the City Name to" +
                    " find the temperature at that Location. You may enter it with or without using the word 'Weather' as the predecessor");
            sendMessage(channel, sender + ": Enter Covid 'Valid_Country_Name' 'Parameter' to get the current details about" +
                    " the havoc being caused by COVID-19. Example : Covid India active  (OR)  Covid Germany Deaths");
            sendMessage(channel, sender + ": Parameter Types for Covid : Deaths, Active, Recovered, Critical, Tests");
            sendMessage(channel, sender + ": Type 'Disconnect Bot' to end the connection with the Remote Host (The Bot Will Exit)");
        }

        // Default Command
        else {
            sendMessage(channel, sender + ": Not a Valid ProjectBot Command! Type 'Get Commands' to get a list " +
                    "of valid commands or continue using Bot");
        }
    }
}