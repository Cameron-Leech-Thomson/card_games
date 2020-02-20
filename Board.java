public class Board {
    private static Card[] pile1;
    private static Card[] pile2;
    private static Card[] pile3;
    private static Card[] pile4;
    private static Card[] pile5;
    private static Card[] pile6;
    private static Card[] pile7;
    private static Card[] spares;
    private static Card[] pileC;
    private static Card[] pileH;
    private static Card[] pileS;
    private static Card[] pileD;

    public static Deck deck = new Deck();

    public static void setPile(Card[] pile, int pileNum) {
        // Creates the slot for where in the deck these cards are taken from.
        int pileSlot = 0;
        int numCopy = pileNum;
        int a = 1;
        while (numCopy > 0) {
            pileSlot += a;
            a += 1;
            numCopy -= 1;
        }
        System.out.println(pileSlot);

        for (int i = 0; i < pileNum - 1; i++) {

        }

    }

    public static void main(String[] args) {
        deck.shuffle();

        setPile(pile1,2);
        System.out.println(pile1[0]);

    }

}