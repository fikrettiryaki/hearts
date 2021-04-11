package statics;

import deck.Card;
import deck.Suit;
import display.Animation;

public class Hand {

    public static int starter;

    public static Card[] cards = new Card[4];


    public static int cardsCollected = 0;

    public static void collectCards(int begins) {
        Hand.initialize(begins);
        for (int j = 0; j < 4; j++) {
           Card card =  Players.getPlayer((j + begins) % 4).getNext(begins);

            Animation.setAnimationCard(card, (j + begins) % 4);

            while(Animation.animate == true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Hand.setCard((j + begins) % 4 , card);
            cardsCollected++;
        }
    }

    public static Card getStartersCard(){
        return cards[starter];
    }

    public static Suit getStartersSuit(){
        return cards[starter].getSuit();
    }

    public static void setCard(int i, Card card) {
        cards[i] = card;
    }

    public static int getWinner( ) {
        int orderVal = cards[starter].getOrderValue();
        Suit selectedSuit = cards[starter].getSuit();
        int winner = starter;

        for(int i =0; i<4;i++) {
            if ( selectedSuit == cards[i].getSuit()) {
                if (orderVal < cards[i].getOrderValue()) {
                    winner = i;
                    orderVal = cards[i].getOrderValue();
                }
            }
        }

        return winner;
    }

    public static int getScore() {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            if(cards[i] != null) {
                total += cards[i].getValue();
            }
        }
        if(total>10){
            Round.girlExposed = true;
        }
        return total;
    }

    public static void initialize(int begins) {
        cardsCollected = 0;
        starter = begins;
        cards = new Card[4];
    }

    public static Card getCurrentMax() {
        Card max = getStartersCard();
        for (int i = 0; i < 4; i++) {
            if(cards[i] != null && cards[i].getSuit() == getStartersSuit()){
                if(cards[i].getFace().getValue() > max.getFace().getValue())
                max = cards[i];
            }
        }
        return max;

    }
}
