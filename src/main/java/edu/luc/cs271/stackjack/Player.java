import java.util.*;

public class Player {
    //variables
    private String name;
    private int moneyAmount;
    ArrayList<Card> hand = new ArrayList<Card>();
    private boolean bust = false;
    private boolean turnOver = false;
    
    // constructor
    public Player(String nameChoice) {
        moneyAmount = 100;
        name = nameChoice;
    }
    
    // access methods
    public boolean getBustStatus() {
        return bust;
    }
    
    public boolean getTurnOver() {
        return turnOver;
    }
    
    public int getMoney() {
        return moneyAmount;
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
    
    //  change value methods
    public void changeBust(boolean status) {
        bust = status;
    }
    
    public void changeTurnOver(boolean status) {
        turnOver = status;
    }
    
    public void changeMoney(int amount) {
        moneyAmount += amount;
        if(moneyAmount < 0) {
            moneyAmount = 0;
        }
    }
    
    //gameplay methods
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
    
    public void playHand(Deck deck, int bet) {
        //Player is dealt two cards
        System.out.println("(Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            hit(draw);
            System.out.println("> " + draw.getCardString());
        }
        System.out.println("/// TOTAL: " + countHand());
        
        // Player plays
        if(countHand() == 21) {
            //win
            System.out.println("Blackjack!");
            changeTurnOver(true);
        }
        
        while(!getBustStatus() && !getTurnOver()) {
            System.out.println("~~ Type \"H\" to Hit, \"S\" to Stand ~~");
            Scanner scanner = new Scanner(System.in);
            String keyboard = scanner.nextLine(); 
            if(keyboard.equals("H") || keyboard.equals("h")) {
                Card draw = deck.popStack();
                hit(draw);
                System.out.println("> " + draw.getCardString());
                System.out.println("/// TOTAL: " + countHand());
                if(countHand() == 21) {
                    changeTurnOver(true);
                }
                if(countHand() > 21) {
                    System.out.println("Bust");
                    changeBust(true);
                }
            }
            else if(keyboard.equals("S") || keyboard.equals("s")) {
                break;
            }
        }
    }
    
    public void playAutoHand(Deck deck) {
        // Dealer deals two cards
        System.out.println("(Dealer's Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            hit(draw);
            System.out.println("> " + draw.getCardString());
        }
        System.out.println("/// Dealer's Total: " + countHand());
        
        // Dealer plays
        if(countHand() == 21) {
            System.out.println("Blackjack!");
        }
        
        while(!getBustStatus() && countHand() < 17) {
            Card draw = deck.popStack();
            hit(draw);
            System.out.println("> " + draw.getCardString());
            System.out.println("/// Dealer's Total: " + countHand());
            if(countHand() > 21) {
                System.out.println("Dealer Busts");
                changeBust(true);
            }
        }
    }
}