import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player dealer = new Player("Dealer");
        Player playerOne = new Player("one");
        
        while(playerOne.getMoney() > 0) {
            //Place bets
            playerOne.makeBet();
            
            // Player plays
            playerOne.playHand(deck);
            
            if(playerOne.countHand() <= 21) {
                dealer.playAutoHand(deck);
                playerOne.compareToDealer(dealer);
            }
            playerOne.resetHand();
            dealer.resetHand();
        }
    }
}