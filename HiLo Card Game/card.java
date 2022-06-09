
class Card {
    int value;
    String suit;
    String symbol;
    String name;
    boolean used = false;

    Card(int n, String s){
        this.value = n;
        this.suit = s;
        if (value == 1){
            symbol = "A";
            name = "Ace";
        } else if (value > 1 && value < 11){
            symbol = Integer.toString(value);
            name = Integer.toString(value);
        } else if (value == 11){
            symbol = "J";
            name = "Jack";
        }else if (value == 12){
            symbol = "Q";
            name = "Queen";
        }else {
            symbol = "K";
            name = "King";
        }


        System.out.println("Card " + name + " of " + suit + " was created." );
    }

} // end Card.java file
