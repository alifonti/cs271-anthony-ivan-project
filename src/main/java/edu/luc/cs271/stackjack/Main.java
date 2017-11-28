import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player playerOne = new Player("one");
        
        //Place bets
        System.out.println("Your money: $" + playerOne.getMoney());
        System.out.print("How much would you like to bet? (Divisable by two): $");
        Scanner scanner = new Scanner(System.in); 
        String input = scanner.nextLine();
        int number = Integer.parseInt(input);
        int bet = 0;
        if((playerOne.getMoney() - number >= 0) && number % 2 == 0) {
            bet = number;
        }
        else {
            System.out.println("Invalid bet");
        }
        
        //Player is dealt two cards
        System.out.println("(Cards)");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            playerOne.hit(draw);
            System.out.println("> " + draw.getCardString());
        }
        System.out.println("/// TOTAL: " + playerOne.countHand());
        
        // Player plays
        if(playerOne.countHand() == 21) {
            //win
            System.out.println("Blackjack!");
            playerOne.changeBust(true);
        }
        
        while(!playerOne.getBustStatus()) {
            System.out.println("~~ Type \"H\" to Hit, \"S\" to Stand ~~");
            String keyboard = scanner.nextLine(); 
            if(keyboard.equals("H") || keyboard.equals("h")) {
                Card draw = deck.popStack();
                playerOne.hit(draw);
                System.out.println("> " + draw.getCardString());
                System.out.println("/// TOTAL: " + playerOne.countHand());
                if(playerOne.countHand() == 21) {
                    playerOne.changeBust(true);
                }
                if(playerOne.countHand() > 21) {
                    System.out.println("Bust");
                    playerOne.changeMoney(0 - bet);
                    playerOne.changeBust(true);
                }
            }
            else if(keyboard.equals("S") || keyboard.equals("s")) {
                break;
            }
        }
        

        if(playerOne.countHand() <= 21) {
            Player dealer = new Player("Dealer");
            
            // Dealer deals two cards
            System.out.println("(Dealer's Cards)");
            for(int i = 0; i <= 1; i++) {
                Card draw = deck.popStack();
                dealer.hit(draw);
                System.out.println("> " + draw.getCardString());
            }
            System.out.println("/// Dealer's Total: " + dealer.countHand());
            
            // Dealer plays
            if(dealer.countHand() == 21) {
                System.out.println("Blackjack!");
            }
            
            while(!dealer.getBustStatus() && dealer.countHand() < 17) {
                Card draw = deck.popStack();
                dealer.hit(draw);
                System.out.println("> " + draw.getCardString());
                System.out.println("/// Dealer's Total: " + dealer.countHand());
                if(dealer.countHand() > 21) {
                    System.out.println("Dealer Busts");
                    dealer.changeBust(true);
                }
            }
            
            // Compare
            if(dealer.getBustStatus()) {
                System.out.println("You won!");
                playerOne.changeMoney(bet);
            }
            else if(playerOne.countHand() > dealer.countHand()) {
                System.out.println("You won!");
                playerOne.changeMoney(bet);
            }
            
            else if(playerOne.countHand() == dealer.countHand()) {
                System.out.println("Push");
            }
            
            else {
                System.out.println("You lost");
                playerOne.changeMoney(0 - bet);
            }
        }
        System.out.println("Your money: $" + playerOne.getMoney());
    }
}