package statics;

import deck.Deck;
import play.player.Player;

public class Round {
    public static int handRound = 0;
    public static boolean girlExposed;
    public static boolean hasHandPoints(){
        for(Player p: Players.players){
            if(p.getRoundScore() > 0){
                return true;
            }
        }
        return false;

    }

    public static void setDeck() {
        handRound = 0;
        girlExposed = false;
        Deck deck = new Deck();
        Players.reset();
        int player = 0;
        while (deck.hasNext()) {
            Players.getPlayer(player++ % 4).addCard(deck.pop());
        }
        Players.humanPlayer.setCardOrder();
        for(Player p : Players.players){
            p.analyzeCards();
        }
    }
}
