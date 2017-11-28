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
    
    public int makeBet() {
        System.out.println("Your money: $" + getMoney());
        System.out.print("How much would you like to bet? (Divisable by two): $");
        Scanner scanner = new Scanner(System.in); 
        String input = scanner.nextLine();
        int number = Integer.parseInt(input);
        if((getMoney() - number >= 0) && number % 2 == 0) {
            return number;
        }
        else {
            System.out.println("Invalid bet");
            return makeBet();
        }
    }
}