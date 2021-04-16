package play.game;

import deck.Card;
import deck.Deck;
import deck.Suit;
import play.player.Player;
import statics.Hand;
import statics.Players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Round {
    private int handRound = 0;
    private Map<Suit, TreeSet<Card>> exposedCards = new HashMap<>();

    public Round(){
        exposedCards.put(Suit.SPADES, new TreeSet<>());
        exposedCards.put(Suit.HEARTS, new TreeSet<>());
        exposedCards.put(Suit.DIAMONDS, new TreeSet<>());
        exposedCards.put(Suit.CLUBS, new TreeSet<>());
    }

    public  void playRound() {
        setDeck();
        int nextStarter = getWhoIsFirst();
        while (nextRound()) {
            nextStarter = playHand(nextStarter);
        }
        endRound();
    }

    private  void endRound() {
        Players.endRound();
    }


    private  int getWhoIsFirst() {
        for (int i = 0; i < 4; i++) {
            if (Players.getPlayer(i).hasClubs2()) {
                return i;
            }
        }
        return 0;
    }

    private int playHand( int lastWinner) {
        Hand hand = new Hand(lastWinner);
        hand.collectCards(lastWinner, exposedCards);
        Players.setHandScores(hand.getWinner(), hand.getScore());
        updateExposedCards(hand.getCards());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hand.getWinner();
    }

    private void updateExposedCards(List<Card> cards) {
        for(Card c : cards) {
            exposedCards.get(c.getSuit()).add(c);
        }

    }


    public  boolean nextRound(){
        handRound++;
        return handRound<14;
    }

    public  void setDeck() {
        handRound = 0;
        Deck deck = new Deck();
        Players.reset();
        int player = 0;
        while (deck.hasNext()) {
            Players.getPlayer(player++ % 4).addCard(deck.pop());
        }
        Players.humanPlayer.setCardOrder();

    }
}
