package statics;

import play.player.HumanPlayer;
import play.player.PcPlayer;
import play.player.Player;

public class Players {
    public static HumanPlayer humanPlayer = new HumanPlayer();
    public static Player[] players = new Player[4];

    static{
        players[0] = humanPlayer;
        players[1] = new PcPlayer();
        players[2] = new PcPlayer();
        players[3] = new PcPlayer();
    }

    public static Player getPlayer(int player){
        return players[player];
    }


    public static void setHandScores(int handWinner, int handScore) {

            Players.getPlayer(handWinner).addScore(handScore);

    }

    public static void reset() {
        for(Player p : players){
            p.reset();
        }
    }
}
