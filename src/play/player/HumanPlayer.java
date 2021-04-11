package play.player;

import deck.Card;
import deck.Deck;
import deck.Face;
import deck.Suit;
import statics.Hand;
import statics.Round;

import java.util.HashMap;
import java.util.Map;

public class HumanPlayer extends Player{

    public Map<Card, Integer> cardOrder = new HashMap<>();
    private Card selected;

    @Override
    public void analyzeCards() {

    }

    @Override
    public Card getNext(int begins) {
        selected = null;
        while (selected == null) {

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cards.remove(selected);
        cardMap.get(selected.getSuit()).remove(selected);
        return selected;
    }


    //first round no point cards
    //
    public void cardClicked(Card card){
        if(Hand.cardsCollected==0){
            firstHandCard(card);
            return;
        }
        laterHandCard(card);
    }

    private void laterHandCard(Card card) {

        Suit startSuit =  Hand.getStartersCard().getSuit();
        if(card.getSuit() == startSuit){
            selected = card;
            return;
        }
        if(cardMap.get(startSuit).isEmpty()){
            selected = card;
            return;
        }


    }

    private void firstHandCard(Card card) {
        if(Round.handRound == 0){
            if(card.getFace() == Face.TWO && card.getSuit() == Suit.CLUBS){
                selected = card;
            }
            return;
        }

        if(card.getValue() == 1){

            if(Round.hasHandPoints()) {
                selected = card;
            }
            if(!hasNonHeart()){
                selected = card;
            }
            return;
        }
        selected = card;
    }

    private boolean hasNonHeart() {
        for(Card c : cards){
            if(c.getValue() != 1){
                return true;
            }
        }
        return false;
    }

    public void setCardOrder(){
        cardOrder = new HashMap<>();
        int i = 0;
        for(Card card : cards){
            cardOrder.put(card, i++);
        }
    }
}
