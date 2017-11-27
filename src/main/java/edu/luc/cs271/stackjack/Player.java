import java.util.*;

public class Player {
    //variables
    private String name;
    private int moneyAmount;
    ArrayList<Card> hand = new ArrayList<Card>();
    private boolean bust = false;
    
    // constructor
    public Player(String nameChoice) {
        moneyAmount = 100;
        name = nameChoice;
    }
    
    // hand methods
    public void hit(Card drawn) {
        hand.add(drawn);
    }
    
    public int countHand() {
        int num = 0;
        for(int i = 0; i < hand.size(); i++) {
            num += hand.get(i).getValue();
        }
        return num;
    }
    
    public void clearHand() {
        hand.clear();
    }
    
    // status methods
    public boolean getBustStatus() {
        return bust;
    }
    
    public void changeBust(boolean status) {
        bust = status;
    }
}