package play.game;

import statics.Players;

public class Game {

    int maxPoints;

    public Game(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public void playGame() {
        initializeGame();
        while (playNextRound()) {
            Round round = new Round();
            round.playRound();
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





}
