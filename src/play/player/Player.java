package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;

import java.util.*;

public abstract class Player {

    String name;

    Set<Card> cards = new TreeSet<>();


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



    public abstract Card getNext(int playerOrder, List<Card> addedCards, Map<Suit, TreeSet<Card>> exposedCards);

    public boolean hasClubs2() {
        return cards.contains(new Card(Suit.CLUBS, Face.TWO));
    }

    public void reset() {
        cards = new TreeSet<>();

        roundScore = 0;
    }

    public void addScore(int score) {
        roundScore += score;
    }


    public void addCard(Card card) {
        cards.add(card);
    }

    public Set<Card> getHand() {
        return cards;
    }


    public void addRoundScore() {
        gameScore += roundScore;
    }

    public String getName(){
return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        sb.append(": ");
        sb.append(roundScore);
        sb.append("/");
        sb.append(gameScore);
        Suit suit=null;
        for(Card card : cards){
            if(suit != card.getSuit()){
                suit = card.getSuit();
                sb.append("\n");
            }
            sb.append(card.toString());
        }
        return sb.toString();
    }



}
