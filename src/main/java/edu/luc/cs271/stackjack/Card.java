public class Card {
    //variables
    private String suit;
    private int value;
    
    //constructor
    public Card(String cardSuit, int cardValue) {
        suit = cardSuit;
        value = cardValue;
    }
    
    public String getSuit() {
        return suit;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getCardString() {
        String result = "";
        result += suit;
        String numValue = "";
        if(value > 1 && value <= 10) {
            result += " " + value;
        }
        else if(value == 1) result += " A";
        else if(value == 11) result += " J";
        else if(value == 12) result += " Q";
        else if(value == 13) result += " K";
        
        return result;
    }
}