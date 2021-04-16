package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;
import display.WrongCard;
import statics.Hand;
import play.game.Round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class HumanPlayer extends Player{

    public Map<Card, Integer> cardOrder = new HashMap<>();

    private Card clicked;
    private boolean cardExpected;
    Object lock;

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Card getNext(int playerOrder, List<Card> addedCards, Map<Suit, TreeSet<Card>> exposedCards) {
        Card selected = null;
        clicked = null;
        while(selected == null) {
            clicked = null;
            while(clicked == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(playerOrder==0){
                if(goodForFirstCard( exposedCards, clicked)){
                    selected = clicked;
                }
            }else{
                if(goodForLaterCard(clicked, addedCards)){
                    selected = clicked;
                }
            }
            if(selected == null){
                WrongCard.set(clicked);
            }
        }
        cards.remove(selected);
        clicked=null;
        return selected;
    }



    //first round no point cards
    //
    public void cardClicked(Card card){
        if(clicked==null) {
            clicked = card;
        }

    }


    private boolean hasOnlyHearts() {
        for(Card c : cards){
            if(c.getValue() != 1){
                return false;
            }
        }
        return true;
    }

    public void setCardOrder(){
        cardOrder = new HashMap<>();
        int i = 0;
        for(Card card : cards){
            cardOrder.put(card, i++);
        }
    }


    private boolean goodForLaterCard(Card card, List<Card> addedCards) {

        Suit startSuit =  addedCards.get(0).getSuit();
        if(card.getSuit() == startSuit){
            return true;
        }
        return hasNoCardLeftForSuit(startSuit) && notStartWithPoint(card);
    }

    private boolean notStartWithPoint(Card card) {
        return cards.size() != 13 || card.getValue() == 0;
    }

    private boolean hasNoCardLeftForSuit(Suit startSuit) {
        for(Card c : cards){
            if(c.getSuit() == startSuit){
                return false;
            }
        }
        return true;
    }

    private boolean goodForFirstCard( Map<Suit, TreeSet<Card>> exposedCards, Card clickedCard) {
        if(cards.size() == 13){
            return clickedCard.getFace() == Face.TWO && clickedCard.getSuit() == Suit.CLUBS;
        }

        if(clickedCard.getValue() == 1){

            if(exposedCards.get(Suit.HEARTS).size() > 0) {
                return true;
            }
            return hasOnlyHearts();
        }
        return true;
    }
}
