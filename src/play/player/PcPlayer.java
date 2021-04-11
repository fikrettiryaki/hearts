package play.player;

import deck.Card;
import deck.Face;
import deck.Suit;
import statics.Hand;
import statics.Round;
import util.Strategy;

public class PcPlayer extends Player {

    public PcPlayer(String name) {
        super(name);
    }

    @Override
    public Card getNext(int begins) {
        analyzeCards();
        Card card;
        if (Hand.cardsCollected == 0) {
            card = firstCard();

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
            if(strategy == Strategy.Safe){
                return getRidOff();
            }
        }
        if(cardMap.get(Hand.getStartersSuit()).size() == 1) {
            return cardMap.get(Hand.getStartersSuit()).iterator().next();
        }
        if(Hand.getStartersSuit() == Suit.SPADES && !Round.girlExposed){
            return getSpades();
        }
        return maxInSuit();

    }

    private Card maxBefore(Card max){
        Card card = null;
        for(Card c : cardMap.get(max.getSuit())){
            if(c.getFace().getValue()<max.getFace().getValue()){
                card = c;
            }
        }
        return card;
    }

    private Card getSpades() {
        if(cardMap.get(Suit.SPADES).contains(new Card(Suit.SPADES, Face.QUEEN))){
            if(Hand.getCurrentMax().getFace().getValue() > Face.QUEEN.getValue()){
                return new Card(Suit.SPADES, Face.QUEEN);
            }
        }
        if(Hand.cardsCollected == 3){
            if(Hand.getScore() == 0){
                Card card = cardMap.get(Suit.SPADES).last();
                if(card.getValue() > 0){
                    return maxBefore(card);
                }
            }
        }
        Card card = maxBefore(Hand.getCurrentMax());
        if(card == null){
            card = maxBefore(new Card(Suit.SPADES, Face.QUEEN));
        }
        if(card == null){
            card = cardMap.get(Suit.SPADES).last();
        }
        return card;
    }

    private Card maxInSuit() {
        Card card = maxBefore(Hand.getCurrentMax());

        if(Hand.cardsCollected == 3){
            if(Hand.getScore() == 0) {
                return cardMap.get(Hand.getStartersSuit()).last();
            }
            if(card == null){
                return cardMap.get(Hand.getStartersSuit()).last();
            }
        }

        if(card != null){
            return card;
        }

        return cardMap.get(Hand.getStartersSuit()).first();
    }

    private Card getRidOff() {
        if (Round.handRound == 0) {
            for (int i = safeOrderedCards.size(); i > 0; i--) {
                if (safeOrderedCards.get(i - 1).getValue() == 0) {
                    return safeOrderedCards.get(i - 1);
                }
            }
        }
        return safeOrderedCards.get(safeOrderedCards.size() - 1);
    }

    private Card firstCard() {
        if (Round.handRound == 0) {
            return cards.iterator().next();
        }

        if(strategy == Strategy.Safe){
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
