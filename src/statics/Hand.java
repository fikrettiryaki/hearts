package statics;

import deck.Card;
import deck.Suit;
import display.Animation;
import play.game.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Hand {

    public static final List<Card> cards = new ArrayList<>();
    public static int starter;

    public Hand(int start) {
        starter = start;
        cards.clear();
    }


    public void collectCards(int begins, Map<Suit, TreeSet<Card>> exposedCards) {
        cards.clear();
        for (int j = 0; j < 4; j++) {
            Card card = Players.getPlayer((j + begins) % 4).getNext(j, cards, exposedCards);

            Animation.setAnimationCard(card, (j + begins) % 4);

            while (Animation.animate) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            cards.add(card);
        }
    }



    public int getWinner() {
        Card starterCard = cards.get(0);

        int winner = 0;
        for(int i =1; i<4; i++){
            if (starterCard.getSuit() == cards.get(i).getSuit()) {
                if (starterCard.getOrderValue() < cards.get(i).getOrderValue()) {
                    winner = i;
                    starterCard = cards.get(i);
                }
            }
        }
        return (winner+starter)%4;
    }

    public int getScore() {
        int total = 0;
        for (Card card : cards) {
                total += card.getValue();
        }
        return total;
    }

    public List<Card> getCards(){
        return cards;
    }


}
