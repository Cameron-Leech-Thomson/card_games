public class Card {

    // Instance variables:
    private char rank;
    private char suit;

    public Card(char r, char s) {
        this.rank = r;
        this.suit = s;
    }

    public char getRank() {
        return this.rank;
    }

    public char getSuit() {
        return this.suit;
    }

    public String displayCard() {
        String outString = this.rank + "" + this.suit;
        return outString;
    }
}