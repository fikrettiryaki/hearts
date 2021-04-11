package deck;

public enum Face {
    ONE("1",14),
    TWO("2",2),
    THREE("3",3),
    FOUR("4",4),
    FIVE("5",5),
    SIX("6",6),
    SEVEN("7",7),
    EIGHT("8",8),
    NINE("9",9),
    TEN("10",10),
    JACK("J",11),
    QUEEN("Q",12),
    KING("K",13);

        private String sign;
        private int value;

        private Face(String sign, int value) {
            this.sign = sign;
            this.value =value;
        }

        public String getSign() {
            return sign;
        }

    public int getValue() {
        return value;
    }

        public static Face[] items = new Face[]{
                ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
        };

}
