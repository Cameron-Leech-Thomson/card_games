public class Deck {
    private Card[] data;

    public Deck() {
        // Initialise suits & ranks.
        String suits = "CHSD";
        String ranks = "A234567891JQK";

        // Create deck
        data = new Card[52];

        // Index to cycle deck
        int deckIndex = 0;

        // Fill the deck
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card card = new Card(ranks.charAt(j), suits.charAt(i));
                data[deckIndex++] = card;
            }
        }
    }

    public void display() {
        int index = 1;

        for (int i = 0; i < 52; i++) {            
            if (index % 13 == 0 && i != 0) {
                System.out.println(data[i].getRank() + "" + data[i].getSuit() + " ");
                index++;
            }
            else {
                System.out.print(data[i].getRank() + "" + data[i].getSuit() + " ");
                index++;
            }
        }
    }

    public void shuffle() {
        for (int i = data.length - 1; i > 0; i--) {

            int index = rand(0, i + 1);

            // Create temp value
            Card card = data[index];
            // Swap the two cards.
            data[index] = data[i];
            data[i] = card;
        }
    }

    public static int rand(int min, int max) {
        // Generate a random value between the min and the max values passed.
        int rand = (int) Math.round(Math.random() * (max - min) + min);

        // Return the random value.
        return rand;
    }

    public String getCard(int pos) {
        // Retrieves the card in the position requested.
        return data[pos].displayCard();
    }
}