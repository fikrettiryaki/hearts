package deck;

import java.awt.*;

public enum Suit {
    CLUBS("♣",0, Color.black), DIAMONDS("♦",20, Color.red), SPADES("♠", 40, Color.black), HEARTS("♥", 60, Color.red);

    private String sign;
    private int value;
    private Color color;


    Suit(String sign, int value, Color color) {
        this.sign = sign;
        this.value = value;
        this.color = color;
    }

    public String getSign() {
        return sign;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public static Suit[] items = new Suit[]{
            CLUBS, DIAMONDS, HEARTS, SPADES
    };


}
