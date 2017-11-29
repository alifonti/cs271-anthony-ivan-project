import java.util.*;

public class Player {
    //variables
    private String name;
    private int moneyAmount;
    private int bet;
    ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Card> altHand = new ArrayList<Card>();
    private boolean bust = false;
    private boolean turnOver = false;
    private boolean blackjack = false;
    private boolean specialAce = false;
    
    // constructor
    public Player(String nameChoice) {
        hands.add(hand);
        hands.add(altHand);
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
        return specialAce;
    }
    
    public int getMoney() {
        return moneyAmount;
    }
    
    public Card getCard(int pos, int hand) {
        return hands.get(hand).get(pos);
    }
    
    // input verifier (Credit: adarshr on Stack Overflow )
    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
    
    // hand methods
    public void hit(Card drawn, int hand) {
        hands.get(hand).add(drawn);
    }
    
    public int countHand(int hand) {
        int num = 0;
        boolean checkAce = false;
        for(int i = 0; i < hands.get(hand).size(); i++) {
            if(hands.get(hand).get(i).getValue() == 1) {
                checkAce = true;
            }
            num += hands.get(hand).get(i).getValue();
        }
        if(checkAce) {
            if((num + 10) <= 21) {
                specialAce = true;
                return num + 10;
            }
            else {
                specialAce = false;
            }
        }
        return num;
    }
    
    public boolean checkPair() {
        if(hands.get(0).get(0).getNumber() == hands.get(0).get(1).getNumber()) {
            return true;
        }
        else return false;
    }
    
    public void resetHand() {
        hands.get(0).clear();
        hands.get(1).clear();
        bet = 0;
        bust = false;
        turnOver = false;
        blackjack = false;
        specialAce = false;
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
        System.out.println("[   Your money: $" + getMoney() + "   ]");
        System.out.print("How much would you like to bet? (Divisable by two): $");
        Scanner scanner = new Scanner(System.in); 
        String input = scanner.nextLine();
        System.out.println();
        int number = 0;
        if(!isAlpha(input)) {
            number = Integer.parseInt(input);
        }
        else {
            System.out.println("! Not a number !");
            return makeBet();
        }
        if((getMoney() - number >= 0) && number % 2 == 0) {
            bet = number;
            return number;
        }
        else {
            System.out.println("! Invalid bet !");
            return makeBet();
        }
    }
    
    public void playHand(Deck deck) {
        //Player is dealt two cards
        System.out.println("(Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            hit(draw, 0);
            System.out.println("> " + draw.getCardString());
        }
        printTotal(0);
        
        // Player plays
        if(countHand(0) == 21) {
            //win
            System.out.println("Blackjack!");
            changeBlackjack(true);
            changeTurnOver(true);
            changeMoney(bet + (bet/2));
        }
        
        while(!getBustStatus() && !getTurnOver()) {
            System.out.print("~~ Type \"H\" to Hit, \"S\" to Stand, \"T\" to Split, \"D\" to Double Down ~~");
            Scanner scanner = new Scanner(System.in);
            String keyboard = scanner.nextLine();
            System.out.println();
            if(keyboard.equals("H") || keyboard.equals("h")) {
                Card draw = deck.popStack();
                hit(draw, 0);
                System.out.println("> " + draw.getCardString());
                printTotal(0);
                if(countHand(0) == 21) {
                    changeTurnOver(true);
                }
                if(countHand(0) > 21) {
                    System.out.println("  *** Bust ***");
                    System.out.println();
                    changeMoney(-bet);
                    changeBust(true);
                }
            }
            else if(keyboard.equals("S") || keyboard.equals("s")) {
                changeTurnOver(true);
            }
            else if(keyboard.equals("T") || keyboard.equals("t")) {
                if(checkPair() && bet * 2 <= getMoney()) {
                    Card first = getCard(0, 0);
                    Card second = getCard(1, 0);
                    System.out.println("@@   Left Hand   @@");
                    System.out.println("> " + getCard(0,0).getCardString());
                    playSplitHand(deck, first, 0);
                    System.out.println();
                    System.out.println("@@   Right Hand   @@");
                    System.out.println("> " + getCard(1,0).getCardString());
                    playSplitHand(deck, second, 1);
                    changeTurnOver(true);
                    
                }
                else if(bet * 2 > getMoney()) {
                    System.out.println("Insufficient money to split. Need at least double your bet");
                }
                else {
                    System.out.println(" ?? Cannot split this hand. You can only split if dealt a pair.");
                    System.out.println();
                }
            }
            else if(keyboard.equals("D") || keyboard.equals("d")) {
                if(countHand(0) >= 9 && countHand(0) <= 11 && bet * 2 <= getMoney()) {
                    bet = bet * 2;
                    System.out.println("## Your bet is now $" + bet + " ##");
                    Card draw = deck.popStack();
                    hit(draw, 0);
                    System.out.println("> " + draw.getCardString());
                    printTotal(0);
                    changeTurnOver(true);
                }
                else {
                    System.out.println(" ?? Cannot double down on this hand. First two cards must add up to 9, 10, or 11.");
                    System.out.println();
                }
            }
        }
    }
    
    public void playSplitHand(Deck deck, Card card, int hand) {
        hands.get(hand).clear();
        hit(card, hand);
        printTotal(hand);
        while(!getBustStatus() && !getTurnOver()) {
            System.out.print("~~ Type \"H\" to Hit, \"S\" to Stand ~~ <Split Pair>");
            Scanner scanner = new Scanner(System.in);
            String keyboard = scanner.nextLine();
            System.out.println();
            if(keyboard.equals("H") || keyboard.equals("h")) {
                Card draw = deck.popStack();
                hit(draw, hand);
                System.out.println("> " + draw.getCardString());
                printTotal(hand);
                if(countHand(hand) == 21) {
                    changeTurnOver(true);
                }
                if(countHand(hand) > 21) {
                    System.out.println("  *** Bust ***");
                    System.out.println();
                    changeMoney(-bet);
                    changeBust(true);
                }
            }
            else if(keyboard.equals("S") || keyboard.equals("s")) {
                changeTurnOver(true);
            }
        }
        changeBust(false);
        changeTurnOver(false);
    }
    
    public void playAutoHand(Deck deck) {
        // Dealer deals two cards
        System.out.println("================");
        System.out.println("(Dealer's Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            hit(draw, 0);
            System.out.println("> " + draw.getCardString());
        }
        printTotal(0);
        
        // Dealer plays
        if(countHand(0) == 21) {
            System.out.println("Blackjack!");
        }
        
        while(!getBustStatus() && countHand(0) < 17) {
            Card draw = deck.popStack();
            hit(draw, 0);
            System.out.println("> " + draw.getCardString());
            printTotal(0);
            if(countHand(0) > 21) {
                System.out.println("Dealer Busts");
                changeBust(true);
            }
        }
        System.out.println("================");
    }
    
    public void compareToDealer(Player dealer, int hand) {
        if(dealer.getBustStatus() && countHand(hand) <= 21) {
            System.out.println("*** You won! ***");
            changeMoney(bet);
        }
        else if(countHand(hand) > dealer.countHand(0) && countHand(hand) <= 21) {
            System.out.println("*** You won! ***");
            changeMoney(bet);
        }
        
        else if(countHand(hand) == dealer.countHand(0)) {
            System.out.println("  *** Push ***");
        }
        
        else {
            System.out.println("*** You lost ***");
            changeMoney(-bet);
        }
        System.out.println();
        System.out.println();
    }
    
    public void printTotal(int hand) {
        countHand(hand);
        if(specialAce) {
            System.out.println("    /// TOTAL: " + (countHand(hand) - 10) + " or " + countHand(hand));
        }
        else {
            System.out.println("    /// TOTAL: " + countHand(hand));
        }
    }
}