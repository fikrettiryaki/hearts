package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;
import statics.Hand;
import statics.Round;

import java.util.*;
import java.util.stream.Collectors;

public class PcPlayer extends Player {

    private int strategy = 0;


    List<Card> safeOrderedCards = new ArrayList<>();


    @Override
    public void analyzeCards() {
        safeOrderedCards = cards.stream().collect(Collectors.toList());
        safeOrderedCards.sort(Comparator.comparingInt(Card::getSafeValue));
    }

    @Override
    public Card getNext(int begins) {

    Card card;
        if (Hand.cardsCollected == 0) {
            card = firstHandCard();

        } else {
            card = laterHandCard();
        }
        cardMap.get(card.getSuit()).remove(card);
        cards.remove(card);
        safeOrderedCards.remove(card);
        return card;

    }

    private Card laterHandCard() {
        if(cardMap.get(Hand.getStartersSuit()).size() == 0){
            if(strategy == 0){
                return getRidOff();
            }
        }
        return maxInSuit();
    }

    private Card maxInSuit() {

        Card card = null;
        Card max = null;
        for(Card c : cardMap.get(Hand.getStartersSuit())){
            if(c.getFace().getValue()<Hand.getCurrentMaxFace()){
                card = c;
            }
            max = c;
        }
        if(Hand.cardsCollected == 3){
            if(max.getSuit() == Suit.SPADES && max.getFace() == Face.QUEEN){
                //TODO: max before queen
               return cardMap.get(Hand.getStartersSuit()).iterator().next();
           }
            if(Hand.getScore() == 0) {
                return max;
            }
            if(card == null){
                return max;
            }
        }

        if(card != null){
            return card;
        }


        return cardMap.get(Hand.getStartersSuit()).iterator().next();
    }

    private Card getRidOff() {
        return safeOrderedCards.get(safeOrderedCards.size()-1);
    }

    private Card firstHandCard() {
        if (Round.handRound == 0) {
            return cards.iterator().next();
        }

        if(strategy == 0){
            return  getSafeCard();
        }
        return getWinnerCard();


    }

    private Card getWinnerCard() {
        return cards.iterator().next();
    }

    private Card getSafeCard() {
        return safeOrderedCards.iterator().next();
    }


}
