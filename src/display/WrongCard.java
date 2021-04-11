package display;

import deck.Card;

public class WrongCard {
    public static boolean exists;
    public static Card card;
    public static int animateDuration;

    public static void next() {
        animateDuration--;
        if(animateDuration<0){
            exists=false;
        }
    }

    public static void set(Card wrong){
        card = wrong;
        animateDuration=10;
        exists=true;
    }
}
