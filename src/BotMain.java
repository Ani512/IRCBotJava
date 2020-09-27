public class BotMain {
    public static void main(String[] args) throws Exception {
        Bot TempBot = new Bot();        // Creating object of class Bot
        TempBot.setVerbose(true);       // We can debug the bot and make changes to it
        TempBot.connect("irc.freenode.net");
        TempBot.joinChannel("#PrjDev2336");
    }
}
