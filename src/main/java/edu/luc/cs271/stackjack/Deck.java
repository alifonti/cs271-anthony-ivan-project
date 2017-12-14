import java.util.*;

public class Deck {
    ArrayList<Card> allCards = new ArrayList<Card>();
    Stack<Card> stackedDeck;
    private int numOfDecks = 8;
    
    //constructor
    public Deck() {
        for(int j = 1; j <= numOfDecks; j++) {
            for(int i = 1; i <= 13; i++) {
                allCards.add(new Card("Spade", i));
                allCards.add(new Card("Heart", i));
                allCards.add(new Card("Diamond", i)); 
                allCards.add(new Card("Club", i));
            }
        }
    }

    // prompt for Main
    public void setNumofDecks(int num) {
        numOfDecks = num;
    }
    
    // setup methods
    public void setDeck() {
        System.out.println("---shuffling a new deck---");
        stackedDeck = shuffleAndStack();
    }

    public Stack<Card> shuffleAndStack() {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        Stack<Card> randomizedCards = new Stack<Card>();
        nums.addAll(drawAtRandom());
        for(int i = 0; i < 51*numOfDecks; i++) {
            randomizedCards.push(allCards.get(nums.get(i)));
        }
        return randomizedCards;
        
    }
    public ArrayList<Integer> drawAtRandom() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i <= 51*numOfDecks; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
    
    public Card popStack() {
        if(!stackedDeck.empty()) {
            return stackedDeck.pop();
        }
        else {
            System.out.println("---shuffling a new deck---");
            stackedDeck = shuffleAndStack();
            return stackedDeck.pop();
        }
    }
    
    // input verifier
    public boolean isNumber(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}