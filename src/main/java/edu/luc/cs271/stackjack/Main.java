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
            
            // Player plays
            playerOne.playHand(deck);
            
            if(playerOne.countHand(0) <= 21 && !playerOne.getBlackjack()) {
                dealer.playAutoHand(deck);
                playerOne.compareToDealer(dealer, 0);
            }
            if(playerOne.countHand(1) != 0) {
                playerOne.compareToDealer(dealer, 1);
            }
            playerOne.resetHand();
            dealer.resetHand();
        }
    }
}