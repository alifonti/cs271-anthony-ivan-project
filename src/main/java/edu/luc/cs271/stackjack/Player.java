import java.util.*;

public class Player {
    //variables
    private String name;
    private int moneyAmount;
    private int bet;
    ArrayList<Card> hand = new ArrayList<Card>();
    private boolean bust = false;
    private boolean turnOver = false;
    private boolean blackjack = false;
    private boolean aceAsOne = false;
    
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
    
    public boolean getBlackjack() {
        return blackjack;
    }
    
    public boolean getAceAsOne() {
        return aceAsOne;
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
                aceAsOne = true;
                return num + 10;
            }
            else {
                aceAsOne = false;
            }
        }
        return num;
    }
    
    public void resetHand() {
        hand.clear();
        bet = 0;
        bust = false;
        turnOver = false;
        blackjack = false;
        aceAsOne = false;
    }
    
    //  change value methods
    public void changeBust(boolean status) {
        bust = status;
    }
    
    public void changeTurnOver(boolean status) {
        turnOver = status;
    }
    
    public void changeBlackjack(boolean status) {
        blackjack = status;
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
            bet = number;
            return number;
        }
        else {
            System.out.println("Invalid bet");
            return makeBet();
        }
    }
    
    public void playHand(Deck deck) {
        //Player is dealt two cards
        System.out.println("(Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            hit(draw);
            System.out.println("> " + draw.getCardString());
        }
        printTotal();
        
        // Player plays
        if(countHand() == 21) {
            //win
            System.out.println("Blackjack!");
            changeBlackjack(true);
            changeTurnOver(true);
            changeMoney(bet + (bet/2));
        }
        
        while(!getBustStatus() && !getTurnOver()) {
            System.out.println("~~ Type \"H\" to Hit, \"S\" to Stand ~~");
            Scanner scanner = new Scanner(System.in);
            String keyboard = scanner.nextLine(); 
            if(keyboard.equals("H") || keyboard.equals("h")) {
                Card draw = deck.popStack();
                hit(draw);
                System.out.println("> " + draw.getCardString());
                printTotal();
                if(countHand() == 21) {
                    changeTurnOver(true);
                }
                if(countHand() > 21) {
                    System.out.println("Bust");
                    changeMoney(-bet);
                    changeBust(true);
                }
            }
            else if(keyboard.equals("S") || keyboard.equals("s")) {
                changeTurnOver(true);
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
        printTotal();
        
        // Dealer plays
        if(countHand() == 21) {
            System.out.println("Blackjack!");
        }
        
        while(!getBustStatus() && countHand() < 17) {
            Card draw = deck.popStack();
            hit(draw);
            System.out.println("> " + draw.getCardString());
            printTotal();
            if(countHand() > 21) {
                System.out.println("Dealer Busts");
                changeBust(true);
            }
        }
    }
    
    public void compareToDealer(Player dealer) {
        if(dealer.getBustStatus()) {
            System.out.println("You won!");
            changeMoney(bet);
        }
        else if(countHand() > dealer.countHand()) {
            System.out.println("You won!");
            changeMoney(bet);
        }
        
        else if(countHand() == dealer.countHand()) {
            System.out.println("Push");
        }
        
        else {
            System.out.println("You lost");
            changeMoney(-bet);
        }
    }
    
    public void printTotal() {
        countHand();
        if(aceAsOne) {
            System.out.println("/// TOTAL: " + (countHand() - 10) + " or " + countHand());
        }
        else {
            System.out.println("/// TOTAL: " + countHand());
        }
    }
}