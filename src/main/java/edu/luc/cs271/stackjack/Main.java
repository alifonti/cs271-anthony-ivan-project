import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player dealer = new Player("Dealer");
        Player playerOne = new Player("one");
        
        while(playerOne.getMoney() > 1) {
            //Place bets
            playerOne.makeBet();
            
            // Dealer plays
            dealer.playAutoHand(deck);
            
            // Player plays
            playerOne.playHand(deck);
            
            //Dealer reveals
            dealer.revealHand(deck);
            
            //Compare hands
            if(playerOne.countHand(0) <= 21 && !playerOne.getBlackjack()) {
                playerOne.compareToDealer(dealer, 0);
            }
            if(playerOne.countHand(1) != 0) {
                playerOne.compareToDealer(dealer, 1);
            }
            
            //reset
            playerOne.resetHand();
            dealer.resetHand();
        }
    }
}