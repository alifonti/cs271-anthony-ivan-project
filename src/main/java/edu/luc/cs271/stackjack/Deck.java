import java.util.*;

public class Deck {
    ArrayList<Card> allCards = new ArrayList<>();
    Stack<Card> stackedDeck;
    
    //constructor
    public Deck() {
        for(int i = 1; i <= 13; i++) {
            allCards.add(new Card("Spade", i));
            allCards.add(new Card("Heart", i));
            allCards.add(new Card("Diamond", i)); 
            allCards.add(new Card("Club", i));
        }
        stackedDeck = shuffleAndStack();
    }

    public Stack<Card> shuffleAndStack() {
        ArrayList<Integer> nums = new ArrayList<>();
        Stack<Card> randomizedCards = new Stack<Card>();
        nums.addAll(drawAtRandom());
        for(int i = 0; i < 51; i++) {
            randomizedCards.push(allCards.get(nums.get(i)));
        }
        return randomizedCards;
        
    }
    public ArrayList<Integer> drawAtRandom() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 51; i++) {
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
}