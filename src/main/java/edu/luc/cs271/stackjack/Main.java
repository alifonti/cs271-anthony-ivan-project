import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player playerOne = new Player("one");
        
        // Player dealer = new Player("dealer");
        // while(playerOne.getMoney() > 0) {
        //     playHand(playerOne);
        //     dealerPlay();
        //     evaluateHand(playerOne);
        // }
        
        //Place bets
        int bet = playerOne.makeBet();
        
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
            Scanner scanner = new Scanner(System.in);
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
        System.out.println(playerOne.getMoney());
    }
    
    // public static void playHand(Player playerUp) {
    //     Player player = playerUp;
        
    //     //Player is dealt two cards
    //     System.out.println("(Cards)");
    //     for(int i = 0; i <= 1; i++) {
    //         Card draw = deck.popStack();
    //         player.hit(draw);
    //         System.out.println("> " + draw.getCardString());
    //     }
    //     System.out.println("/// TOTAL: " + player.countHand());
        
    //     // Player plays
    //     if(player.countHand() == 21) {
    //         //win
    //         System.out.println("Blackjack!");
    //         player.changeBust(true);
    //     }
        
    //     while(!player.getBustStatus()) {
    //         System.out.println("~~ Type \"H\" to Hit, \"S\" to Stand ~~");
    //         String keyboard = scanner.nextLine(); 
    //         if(keyboard.equals("H") || keyboard.equals("h")) {
    //             Card draw = deck.popStack();
    //             player.hit(draw);
    //             System.out.println("> " + draw.getCardString());
    //             System.out.println("/// TOTAL: " + player.countHand());
    //             if(player.countHand() == 21) {
    //                 player.changeBust(true);
    //             }
    //             if(player.countHand() > 21) {
    //                 System.out.println("Bust");
    //                 player.changeMoney(0 - bet);
    //                 player.changeBust(true);
    //             }
    //         }
    //         else if(keyboard.equals("S") || keyboard.equals("s")) {
    //             break;
    //         }
    //     }
    // }
    
    // public static void dealerPlay() {
    //     // Dealer deals two cards
    //     System.out.println("(Dealer's Cards)");
    //     for(int i = 0; i <= 1; i++) {
    //         Card draw = deck.popStack();
    //         dealer.hit(draw);
    //         System.out.println("> " + draw.getCardString());
    //     }
    //     System.out.println("/// Dealer's Total: " + dealer.countHand());
        
    //     // Dealer plays
    //     if(dealer.countHand() == 21) {
    //         System.out.println("Blackjack!");
    //     }
            
    //     while(!dealer.getBustStatus() && dealer.countHand() < 17) {
    //         Card draw = deck.popStack();
    //         dealer.hit(draw);
    //         System.out.println("> " + draw.getCardString());
    //         System.out.println("/// Dealer's Total: " + dealer.countHand());
    //         if(dealer.countHand() > 21) {
    //             System.out.println("Dealer Busts");
    //             dealer.changeBust(true);
    //         }
    //     }
    // }
    
    // public static void evaluateHand(Player player) {
    //     if(dealer.getBustStatus()) {
    //         System.out.println("You won!");
    //         player.changeMoney(bet);
    //     }
    //     else if(player.countHand() > dealer.countHand()) {
    //         System.out.println("You won!");
    //         player.changeMoney(bet);
    //     }
        
    //     else if(player.countHand() == dealer.countHand()) {
    //         System.out.println("Push");
    //     }
        
    //     else {
    //         System.out.println("You lost");
    //         player.changeMoney(0 - bet);
    //     }
        
    //     System.out.println("Your money: $" + player.getMoney());
    // }
}