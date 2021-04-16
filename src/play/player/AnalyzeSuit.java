package play.player;

import deck.Card;
import deck.Face;
import java.util.TreeSet;

public class AnalyzeSuit {
    int dangerLevel;
    Card card;

    public AnalyzeSuit(TreeSet<Card> ownedCards, TreeSet<Card> exposedCards) {
        card = ownedCards.first();
        int ownedCount = ownedCards.size();
        int exposedCount = exposedCards.size();
        int othersRemaining = 13 - ownedCount - exposedCount;
        int othersGreater = getGreaterCount(ownedCards, exposedCards);
        int othersSmaller = getSmallerCount(ownedCards, exposedCards);;

        if(othersGreater == 0) {
            dangerLevel = 100;
        } else if (othersSmaller==0){
            dangerLevel=0;
        } else{
            dangerLevel = (othersSmaller * 13 / othersGreater );
            if(ownedCards.first().getValue() == 0){
                dangerLevel = (dangerLevel * (13 - othersRemaining))/13;
            }
        }

    }

    private int getSmallerCount(TreeSet<Card> ownedCards, TreeSet<Card> exposedCards) {
        int smallerCount = 0;
        for(Face f : Face.items){
            if(f.getValue() < card.getFace().getValue()){
                if(exposedCards.contains(new Card(card.getSuit(), f))){
                    continue;
                }
                if(ownedCards.contains(new Card(card.getSuit(), f))){
                    continue;
                }
                smallerCount++;
            }
        }

        return smallerCount;
    }

    private int getGreaterCount(TreeSet<Card> ownedCards, TreeSet<Card> exposedCards) {

        int greaterCount = 0;
        for(Face f : Face.items){
            if(f.getValue() > card.getFace().getValue()){
                if(exposedCards.contains(new Card(card.getSuit(), f))){
                    continue;
                }
                if(ownedCards.contains(new Card(card.getSuit(), f))){
                    continue;
                }
                greaterCount++;
            }
        }

        return greaterCount;
    }


    public boolean girlExposed(TreeSet<Card> exposedCards) {
        for(Card card : exposedCards){
            if(card.getValue() == 13){
                return true;
            }
        }
        return false;
    }










}
