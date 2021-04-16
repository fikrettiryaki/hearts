package statics;

import play.player.HumanPlayer;
import play.player.PcPlayer;
import play.player.Player;

public class Players {
    public static HumanPlayer humanPlayer = new HumanPlayer("You");
    public static Player[] players = new Player[4];

    static{
        players[0] = humanPlayer;
        players[1] = new PcPlayer("Osman");
        players[2] = new PcPlayer("Cem");
        players[3] = new PcPlayer("Aykut");
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

    public static void endRound() {
        boolean onePlayerMaxed=false;
        for(int i = 0; i < 4; i++) {
            if( getPlayer(i).getRoundScore() == 26){
                onePlayerMaxed=true;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(onePlayerMaxed){
                if(getPlayer(i).getRoundScore()==0){
                    getPlayer(i).addScore(26);
                }else{
                    getPlayer(i).addScore(-26);
                }
            }
          getPlayer(i).addRoundScore();
        }
    }
}
