import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> table = new ArrayList<Player>();
        Deck deck = new Deck();
        Player dealer = new Player("Dealer");
        
        playIntro();
        // Adding players
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a name for Player 1: ");
        String input = scanner.nextLine();
        Player playerOne = new Player(input);
        table.add(playerOne);
        for(int i = 2; i <= 7; i++) {
            System.out.println("\nEnter another name to add up to 7 Players (or press enter finished)");
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
        
        // gameplay loop
        while(checkTableMoney(table)) {
            
            // Place bets
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    table.get(i).makeBet();
                }
            }
            
            // Dealer plays
            dealer.playAutoHand(deck);
            
            // Player plays
            for(int i = 0; i < table.size(); i++) {  
                if(table.get(i).getMoney() > 1 && !dealer.getBlackjack()) {
                    table.get(i).playHand(deck);
                }
            }
            
            // if dealer blackjack
            for(int i = 0; i < table.size(); i++) {  
                if(table.get(i).getMoney() > 1 && dealer.getBlackjack()) {
                    table.get(i).playTwoCards(deck);
                }
            }
            
            //Dealer reveals
            dealer.revealHand(deck);
            
            //Compare hands
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    if(!dealer.getBlackjack()) {
                        table.get(i).compareToDealer(dealer, 0);
                        if(table.get(i).countHand(1) != 0) {
                            table.get(i).compareToDealer(dealer, 1);
                        }
                    }
                    else if(dealer.getBlackjack() && table.get(i).getBlackjack()) {
                        System.out.println("***" + table.get(i).getName() + " got a stand-off***");
                    }
                    else {
                        System.out.println("***" + table.get(i).getName() + " lost***");
                        table.get(i).changeMoney(-table.get(i).getBet());
                    }
                    // Reset player
                    table.get(i).resetHand();
                }
            }
            
            // Reset dealer
            System.out.println("\n");
            dealer.resetHand();
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
    
    public static void playIntro() {
        System.out.println("  ____  _             _       _            _    ");
        System.out.println(" / ___|| |_ __ _  ___| | __  | | __ _  ___| | __");
        System.out.println(" \\___ \\| __/ _` |/ __| |/ /  | |/ _` |/ __| |/ /");
        System.out.println("  ___) | || (_| | (__|   < |_| | (_| | (__|   < ");
        System.out.println(" |____/ \\__\\__,_|\\___|_|\\_\\___/ \\__,_|\\___|_|\\_\\");
        System.out.println("================================================");
        System.out.println("   A Text Implementation of Blackjack in Java   ");
        System.out.println();
        System.out.println("                  Developed by:                 ");
        System.out.println("        Anthony LiFonti and Ivan Kavanagh       ");
        System.out.println("================================================");
    }
}