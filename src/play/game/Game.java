package play.game;

import statics.Hand;
import statics.Players;
import statics.Round;

public class Game {

    int maxPoints;


    public Game(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public void playGame() {
        initializeGame();
        while (playNextRound()) {
            playRound();
        }
        printScores();
    }

    private void printScores() {
    }

    private void initializeGame() {
        Players.reset();

    }

    private boolean playNextRound() {
        for (int i = 0; i < 4; i++) {
            if (Players.getPlayer(0).getGameScore() > maxPoints) {
                return false;
            }
        }
        return true;
    }

    public void playRound() {
        Round.setDeck();
        int nextStarter = getWhoIsFirst();
        for (int i = 0; i < 13; i++) {
            Round.handRound=i;
            nextStarter = playHand(nextStarter);
        }
        saveScores();
    }

    private void saveScores() {
        for(int i = 0; i < 4; i++) {
           Players.getPlayer(i).addRoundScore();
        }
    }

    private int playHand( int lastWinner) {
        Hand.collectCards(lastWinner);
        int handWinner = Hand.getWinner();
        Players.setHandScores(handWinner, Hand.getScore());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return handWinner;
    }


    private int getWhoIsFirst() {
        for (int i = 0; i < 4; i++) {
            if (Players.getPlayer(i).hasClubs2()) {
                return i;
            }
        }
        return 0;
    }


}
