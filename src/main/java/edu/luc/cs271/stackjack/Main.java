import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        ArrayList<Player> table = new ArrayList<Player>();
        Deck deck = new Deck();
        Player dealer = new Player("Dealer");
        
        // Adding players
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a name for Player 1: ");
        String input = scanner.nextLine();
        Player playerOne = new Player(input);
        table.add(playerOne);
        for(int i = 2; i <= 7; i++) {
            System.out.println("\nEnter another name to add up to 7 Players (or enter \"N\" when finished)");
            System.out.print("Name " + i + ": ");
            String name = scanner.nextLine();
            if(name.equals("N") || name.equals("n") || name.equals("")) {
                i = 8;
                System.out.println();
            }
            else {
                table.add(new Player(name));
            }
        }
        
        
        while(checkTableMoney(table)) {
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    //Place bets
                    table.get(i).makeBet();
                    
                    // Dealer plays
                    dealer.playAutoHand(deck);
                    
                    // Player plays
                    table.get(i).playHand(deck);
                    
                    //Dealer reveals
                    dealer.revealHand(deck);
                    
                    //Compare hands
                    if(table.get(i).countHand(0) <= 21 && !table.get(i).getBlackjack()) {
                        table.get(i).compareToDealer(dealer, 0);
                    }
                    if(table.get(i).countHand(1) != 0) {
                        table.get(i).compareToDealer(dealer, 1);
                    }
                    
                    //reset
                    table.get(i).resetHand();
                    dealer.resetHand();
                }
            }
        }
    }
    
    public static boolean checkTableMoney(ArrayList<Player> table) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).getMoney() > 1) {
                return true;
            }
        }
        return false;
    }
}