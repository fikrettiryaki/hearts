package display;

import deck.Card;
import statics.Hand;

import java.awt.*;

public class Animation {
    public static boolean animate = false;
    public static int animatePlayer = 0;

    public static Card animateCard;
    public static int animateCount = 0;
    private static int magicNumber = 50;

    public static Rectangle getRectangle(Rectangle fromRectangle, Rectangle toRectangle) {

        if (animateCount < 1) {
            return toRectangle;
        }
        return new Rectangle((fromRectangle.x * animateCount +  (magicNumber - animateCount) * toRectangle.x) / magicNumber,
                (fromRectangle.y * animateCount +  (magicNumber - animateCount) * toRectangle.y) / magicNumber, toRectangle.width, toRectangle.height);

    }

    public static void setAnimateNext(){
        animateCount--;
        if (animateCount<1){
            animate=false;
        }
    }

    public static void setAnimationCard(Card card, int player) {
        animateCard = card;
        animatePlayer = player;
        animate = true;
        animateCount = magicNumber;
    }


}
