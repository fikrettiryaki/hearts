package play.player;

import deck.Card;
import deck.Suit;
import play.game.Round;

import java.util.*;
import java.util.stream.Collectors;

public class PcPlayer extends Player {

    public PcPlayer(String name) {
        super(name);
    }

    @Override
    public Card getNext(int playerOrder, List<Card> addedCards, Map<Suit, TreeSet<Card>> exposedCards) {
        Card card;
        if (playerOrder == 0) {
            card = firstCard(exposedCards);
        } else {
            card = laterHandCard(playerOrder, addedCards);
        }
        cards.remove(card);
        return card;

    }

    private Card laterHandCard(int playerOrder, List<Card> addedCards) {
        Suit handSuit = addedCards.get(0).getSuit();
        List<Card> sameSuitCards = getSameSuitCards(handSuit);

        if(sameSuitCards.size() == 0) {
            return getRidOff();
        }

        if(sameSuitCards.size() == 1) {
            return sameSuitCards.get(0);
        }

        if(playerOrder==3){
            return getLastCard(addedCards, sameSuitCards);
        }

        return getMaxBefore(addedCards, sameSuitCards);
    }

    private Card getLastCard(List<Card> addedCards, List<Card> sameSuitCards) {
        if(hasPoints(addedCards)){
            return getMaxBeforeOrMax(addedCards, sameSuitCards);
        }
        Card c = sameSuitCards.get(sameSuitCards.size()-1);
        if(c.getValue()==13){
            return sameSuitCards.get(sameSuitCards.size()-2);
        }
        return c;
    }

    private Card getMaxBefore(List<Card> addedCards, List<Card> sameSuitCards) {
        Card handMax = getCurrentMax( addedCards);
        Card selected = null;
        Card oneBigger = null;
        for (Card card : sameSuitCards) {
            if (card.getFace().getValue() < handMax.getFace().getValue()) {
                selected = card;
            } else {
                if (oneBigger == null) {
                    oneBigger = card;
                }
            }
        }

        if (selected != null) {
            return selected;
        }

        if (oneBigger.getValue() == 13) {
            return sameSuitCards.get(sameSuitCards.size() - 1);
        }

        return oneBigger;
    }

    private Card getMaxBeforeOrMax(List<Card> addedCards, List<Card> sameSuitCards) {
        Card handMax = getCurrentMax(addedCards);
        Card selected = null;
        for(Card card : sameSuitCards) {
            if(card.getFace().getValue() < handMax.getFace().getValue()){
                selected = card;
            }
        }
        if(selected != null) {
            return selected;
        }
       if(sameSuitCards.get(sameSuitCards.size() - 1).getValue()==13){
            return sameSuitCards.get(sameSuitCards.size() - 2);
        }
        return sameSuitCards.get(sameSuitCards.size() - 1);

    }

    private List<Card> getSameSuitCards(Suit handSuit) {
        return cards.stream().filter(c->c.getSuit() == handSuit).collect(Collectors.toList());
    }

    private boolean hasPoints(List<Card> addedCards){
        return addedCards.stream().mapToInt(Card::getValue).sum() > 0;
    }


    private Card getRidOff() {
        if (cards.size() == 13) {
            cards.stream().filter(c->c.getValue()==0).collect(Collectors.toList())
                    .stream().min((o1, o2) -> o2.getSafeValue() - o1.getSafeValue()).get();
            }
        return cards.stream().min((o1, o2) -> o2.getSafeValue() - o1.getSafeValue()).get();
    }

    private Card firstCard(Map<Suit, TreeSet<Card>> exposedCards) {
        if (cards.size() == 13) {
            return cards.iterator().next();
        }
        return  getSafeCard(exposedCards);

    }

    private Card getSafeCard(Map<Suit, TreeSet<Card>> exposedCards) {
        List<AnalyzeSuit> analyzedSuits = new ArrayList<>();
        for(Suit suit : Suit.items) {
            TreeSet<Card> suitList = new TreeSet<>();
             cards.stream().filter(c->c.getSuit() == suit).forEach(suitList::add);
             if(suitList.size()>0){
                 analyzedSuits.add(new AnalyzeSuit(suitList, exposedCards.get(suit)));
             }
        }
        if(analyzedSuits.size()==1){
            return analyzedSuits.get(0).card;
        }
        analyzedSuits.sort(Comparator.comparingInt(o -> o.dangerLevel));

        if(analyzedSuits.get(0).card.getValue() == 1 && exposedCards.get(Suit.HEARTS).size() == 0){
            return analyzedSuits.get(1).card;
        }


        return analyzedSuits.get(0).card;
    }

    public Card getCurrentMax(List<Card> cards) {
        Card starterCard = cards.get(0);

        for(Card c : cards){
            if (starterCard.getSuit() == c.getSuit()) {
                if (starterCard.getOrderValue() < c.getOrderValue()) {
                    starterCard = c;
                }
            }
        }
        return starterCard;
    }

}
