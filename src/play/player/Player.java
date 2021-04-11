package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;
import util.Strategy;

import java.util.*;

public abstract class Player {
    Set<Card> cards = new TreeSet<>();
    Map<Suit, TreeSet<Card>> cardMap = new HashMap();
    String name;
    Strategy strategy;
    List<Card> safeOrderedCards = new ArrayList<>();


    public int getRoundScore() {
        return roundScore;
    }

    public int getGameScore() {
        return gameScore;
    }

    int roundScore;
    int gameScore;

    public Player(String name){
        this.name = name;
    }

    public  void analyzeCards(){
strategy = Strategy.Safe;
        safeOrderedCards = new ArrayList<>(cards);
        safeOrderedCards.sort(Comparator.comparingInt(Card::getSafeValue));

    }

    public abstract Card getNext(int begins);

    public boolean hasClubs2() {
        return cards.contains(new Card(Suit.CLUBS, Face.TWO));
    }

    public void reset() {
        cards = new TreeSet<>();
        cardMap.put(Suit.CLUBS, new TreeSet<>());
        cardMap.put(Suit.DIAMONDS, new TreeSet<>());
        cardMap.put(Suit.SPADES, new TreeSet<>());
        cardMap.put(Suit.HEARTS, new TreeSet<>());

        roundScore = 0;
    }

    public void addScore(int score) {
        roundScore += score;
    }


    public void addCard(Card card) {
        cards.add(card);
        cardMap.get(card.getSuit()).add(card);
    }

    public Set<Card> getHand() {
        return cards;
    }


    public void addRoundScore() {
        gameScore += roundScore;
    }

    ;

}
