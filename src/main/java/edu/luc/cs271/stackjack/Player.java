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
        boolean checkAce = false;
        for(int i = 0; i < hand.size(); i++) {
            if(hand.get(i).getValue() == 1) {
                checkAce = true;
            }
            num += hand.get(i).getValue();
        }
        if(checkAce) {
            if((num + 10) <= 21) {
                return num + 10;
            }
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
    
    //money methods
    public int getMoney() {
        return moneyAmount;
    }
    
    public void changeMoney(int amount) {
        moneyAmount += amount;
        if(moneyAmount < 0) {
            moneyAmount = 0;
        }
    }
}