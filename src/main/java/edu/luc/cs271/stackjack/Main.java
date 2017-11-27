import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player playerOne = new Player("one");
        
        //Player is dealt two cards
        System.out.println("Cards: ");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            playerOne.hit(draw);
            System.out.println(draw.getCardString());
        }
        System.out.println("Total: " + playerOne.countHand());
        
        // Player plays
        if(playerOne.countHand() == 21) {
            //win
            System.out.println("Blackjack!");
        }
        
        while(!playerOne.getBustStatus()) {
            System.out.println("Type \"H\" to Hit, \"S\" to Stand");
            Scanner scanner = new Scanner(System.in); 
            String keyboard = scanner.nextLine(); 
            if(keyboard.equals("H")) {
                Card draw = deck.popStack();
                playerOne.hit(draw);
                System.out.println(draw.getCardString());
                System.out.println("Total: " + playerOne.countHand());
                if(playerOne.countHand() > 21) {
                    System.out.println("You lose");
                    break;
                }
            }
            else if(keyboard.equals("S")) {
                break;
            }
        }
        

        if(playerOne.countHand() < 21) {
        Player dealer = new Player("Dealer");
        
        // Dealer deals two cards
        System.out.println("Dealer's Cards: ");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            dealer.hit(draw);
            System.out.println(draw.getCardString());
        }
        System.out.println("Total: " + dealer.countHand());
        
        // Dealer plays
        if(dealer.countHand() == 21) {
            System.out.println("Blackjack!");
        }
        
        while(!dealer.getBustStatus() && dealer.countHand() < 17) {
            Card draw = deck.popStack();
            dealer.hit(draw);
            System.out.println(draw.getCardString());
            System.out.println("Total: " + dealer.countHand());
            if(dealer.countHand() > 21) {
                    System.out.println("You lose");
                    dealer.changeBust(true);
            }
        }
        
        // Compare
        if(playerOne.countHand() > dealer.countHand()) {
            System.out.println("You won!");
        }
        
        else if(playerOne.countHand() == dealer.countHand()) {
            System.out.println("Push");
        }
        
        else {
            System.out.println("You lost");
        }
        }
    }
}