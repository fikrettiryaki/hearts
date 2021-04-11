package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public abstract class Player {
    Set<Card> cards = new TreeSet<>();
    Map<Suit,  Set<Card>> cardMap = new HashMap();


    public int getRoundScore() {
        return roundScore;
    }

    public int getGameScore() {
        return gameScore;
    }

    int roundScore;
    int gameScore;



public abstract void analyzeCards();
    public abstract Card getNext(int begins);

    public boolean hasClubs2(){
        return cards.contains(new Card(Suit.CLUBS, Face.TWO));
    }

    public void reset(){
        cards = new TreeSet<>();
        cardMap.put(Suit.CLUBS, new TreeSet<Card>());
        cardMap.put(Suit.DIAMONDS, new TreeSet<Card>());
        cardMap.put(Suit.SPADES, new TreeSet<Card>());
        cardMap.put(Suit.HEARTS, new TreeSet<Card>());

        roundScore = 0;
    }

    public void addScore(int score){
        roundScore+=score;
    }


    public void addCard(Card card){
        cards.add(card);
        cardMap.get(card.getSuit()).add(card);
    }

    public Set<Card> getHand(){
        return cards;
    }


    public void addRoundScore(){
        gameScore += roundScore;
    };

}
