import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Player> table = new ArrayList<Player>();
        Deck deck = new Deck();
        Player dealer = new Player("Dealer");
        int gamemode = 0;
        int roundCount = 20;
        // Intro
        playIntro();
        
        // Adding players
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a name for Player 1: ");
        String input = scanner.nextLine();
        Player playerOne = new Player(input);
        table.add(playerOne);
        for(int i = 2; i <= 7; i++) {
            System.out.println("\n--Press enter when finished--\nor enter another name to add a player (for up to 7 Players)");
            System.out.print("Name " + i + ": ");
            String name = scanner.nextLine();
            if(name.equals("")) {
                i = 8;
                System.out.println();
            }
            else {
                table.add(new Player(name));
            }
        }
        
        // choose gamemode
        gamemode = chooseGamemode();
        
        // set deck
        deck.setNumofDecks();
        deck.setDeck();
        System.out.println("\n");
        
        // gameplay loop
        while(checkPlayingStatus(table, gamemode, roundCount)) {
            
            // Place bets
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    table.get(i).makeBet();
                }
            }
            
            // Dealer plays
            dealer.playAutoHand(deck);
            delay(1);
            
            // Player plays
            for(int i = 0; i < table.size(); i++) {  
                if(table.get(i).getMoney() > 1 && !dealer.getBlackjack()) {
                    table.get(i).playHand(deck);
                }
                delay(1);
            }
            
            // if dealer blackjack
            for(int i = 0; i < table.size(); i++) {  
                if(table.get(i).getMoney() > 1 && dealer.getBlackjack()) {
                    table.get(i).playTwoCards(deck);
                }
                delay(0);
            }
            
            //Dealer reveals
            dealer.revealHand(deck);
            delay(0);
            
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
                        System.out.println("*** " + table.get(i).getName() + " got a stand-off ***");
                    }
                    else {
                        System.out.println("*** " + table.get(i).getName() + " lost ***");
                        table.get(i).changeMoney(-table.get(i).getBet());
                    }
                    // Reset player
                    table.get(i).resetHand();
                }
                else if(table.get(i).getBet() > 0) {
                    System.out.println("*** " + table.get(i).getName() + " is out of money! ***");
                    table.get(i).resetHand();
                }
            }
            
            // Reset dealer
            System.out.println("\n");
            dealer.resetHand();
            roundCount--;
            delay(1);
        }
    }
    
    public static void delay(int length) {
        if(length == 1) {
            try{Thread.sleep(500);}
            catch(InterruptedException ex){Thread.currentThread().interrupt();}
        }
        else {
            try{Thread.sleep(200);}
            catch(InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }
    
    public static boolean checkPlayingStatus(ArrayList<Player> table, int gm, int rc) {
        if(gm == 0) {
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    return true;
                }
            }
            System.out.println("No more players at the table have money. Game over.");
            return false;
        }
        else if(gm == 1) {
            boolean win = false;
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() >= 1000) {
                    System.out.println(table.get(i).getName() + " won!");
                    win = true;
                }
            }
            if(win){return false;}
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney() > 1) {
                    return true;
                }
            }
            System.out.println("No more players at the table have money. Game over.");
            return false;
        }
        
        else if(gm == 2) {
            if(rc == 0) {
                System.out.println(">>>  Game over!  <<<");
                int posBest = 0;
                for(int i = 0; i < table.size(); i++) {
                    if(table.get(i).getMoney() > table.get(posBest).getMoney()) {
                        posBest = i;
                    }
                }
                System.out.println(table.get(posBest).getName() + " won with $" + table.get(posBest).getMoney() + "!");
                return false;
            }
            else {
                System.out.println(">>>  " + rc + " hands to go  <<<\n");
                return true;
            }
        }
        else {
            return true;
        }
    }
    
    public static int chooseGamemode() {
        System.out.println("Would you like to play the standard gamemode?");
        System.out.print("Type \"Y\" for Yes (Standard) or \"N\" for No.\nYour choice: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.equals("N") || input.equals("n")) {
            System.out.println("    Which gamemode would you like to play?");
            System.out.println("    ( 1 ) - Standard. Play unlimited hands.");
            System.out.println("    ( 2 ) - Race to $1,000. First player to reach $1,000 wins!");
            System.out.println("    ( 3 ) - Best of Twenty Hands. Player with the most money after 20 hands wins.");
            System.out.print("    Your choice: ");
            String input2 = scanner.nextLine();
            if(input2.equals("1")) {
                return 0;
            }
            else if(input2.equals("2")) {
                return 1;
            }
            else if(input2.equals("3")) {
                return 2;
            }
            else {
                System.out.println("Invalid Number");
                chooseGamemode();
            }
        }
        else {
            return 0;
        }
        return 0;
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