import java.util.*;

public class Deck {
    ArrayList<Card> allCards = new ArrayList<>();
    Stack<Card> stackedDeck;
    
    //constructor
    public Deck() {
        ArrayList<Card> Spades = new ArrayList<Card>();
        ArrayList<Card> Hearts = new ArrayList<Card>();
        ArrayList<Card> Diamonds = new ArrayList<Card>();
        ArrayList<Card> Clubs = new ArrayList<Card>();
        for(int i = 1; i <= 13; i++) {
                Spades.add(new Card("Spade", i));
                Hearts.add(new Card("Heart", i));
                Diamonds.add(new Card("Diamond", i)); 
                Clubs.add(new Card("Club", i));
        }
        allCards.addAll(Spades);
        allCards.addAll(Hearts);
        allCards.addAll(Diamonds);
        allCards.addAll(Clubs);
        
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
        return stackedDeck.pop();
    }
}