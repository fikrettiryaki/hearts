package deck;

import display.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private List<Card> playDeck;

    public Deck() {
        cards = new ArrayList<>(52);
        for (Suit suit : Suit.items) {
            for (Face face : Face.items) {
                cards.add(new Card(suit, face));
            }
        }
        playDeck = new ArrayList<>(cards);
        Collections.shuffle(playDeck);
        Collections.shuffle(playDeck);
        Collections.shuffle(playDeck);
    }


    public boolean hasNext(){
        return !playDeck.isEmpty();
    }

    public Card pop() {
        if (playDeck.isEmpty()) {
            return null;
        }
        return playDeck.remove(0);
    }

}
