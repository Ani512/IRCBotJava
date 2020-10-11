// RUN THIS FILE TO CONNECT TO FREENODE AND RUN ALL THE FUNCTIONALITIES OF THE BOT
public class BotMain {
    public static void main(String[] args) throws Exception {
        Bot TempBot = new Bot();        // Creating object of class Bot
        TempBot.setVerbose(true);       // Bot will accept inputs
        TempBot.connect("irc.freenode.net");    // Will connect to the IRC Server specified
        TempBot.joinChannel("#PrjDev2336");             // Will join the Channel #PrjDev2336
    }
}

/*
    Implementation of the BOT Requires the pircBot.jar file
    Parsing JSON Done using the jsonSimple.jar file
    Run the BotMain.java file to connect to the IRC server
    Requires JDK 11 or above for implementation
*/
