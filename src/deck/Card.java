package deck;

import display.Board;

public class Card implements Comparable{
    private Suit suit;
    private Face face;

    public Card(Suit suit, Face face) {
        this.suit = suit;
        this.face = face;
    }

    public Suit getSuit() {
        return suit;
    }

    public Face getFace() {
        return face;
    }

    public int getOrderValue(){
        return suit.getValue() + face.getValue();
    }

    public int getValue(){
        if(suit == Suit.SPADES && face == Face.QUEEN){
            return 13;
        }

        if(suit == Suit.HEARTS){
            return 1;
        }
        return 0;
    }

    public int getSafeValue(){
        if(suit == Suit.SPADES && face == Face.QUEEN){
            return 10000;
        }
        if(suit == Suit.SPADES && face == Face.KING){
            return 9980;
        }
        if(suit == Suit.SPADES && face == Face.ONE){
            return 9970;
        }
        if(suit == Suit.HEARTS ){
            return 8000 + face.getValue();
        }

        return suit.getValue() + face.getValue() * 100;
    }

    @Override
    public int compareTo(Object o) {
        return this.getOrderValue() - ((Card)o).getOrderValue();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Card)obj).getOrderValue() == this.getOrderValue();
    }
}
