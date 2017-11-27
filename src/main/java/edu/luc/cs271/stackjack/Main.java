import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player playerOne = new Player("one");
        System.out.println("Cards: ");
        for(int i = 0; i <= 1; i++) {
            Card draw = deck.popStack();
            playerOne.hit(draw);
            System.out.println(draw.getCardString());
        }
        if(playerOne.countHand() == 21) {
            //win
            System.out.println("Blackjack!");
            playerOne.changeWin(true);
        }
        while(!playerOne.getBustStatus()) {
            System.out.println("Type \"H\" to Hit, \"S\" to Stand");
            Scanner scanner = new Scanner(System.in); 
            String keyboard = scanner.nextLine(); 
            if(keyboard.equals("H")) {
                Card draw = deck.popStack();
                playerOne.hit(draw);
                System.out.println(draw.getCardString());
                if(playerOne.countHand() > 21) {
                    System.out.println("You lose");
                    break;
                }
            }
            else if(keyboard.equals("S")) {
                break;
            }
        }
        
        
        // int k = 0;
        // String val;
        // final Map<String, Integer> cardVals = new MyHashmap<>(101);
        // deck myDeck = new deck(); 
        // LinkedHashSet<String> cards = myDeck.loadDeck();
         
        // Iterator<String> itr = cards.iterator();
        // while(itr.hasNext())
        // {
        //     String card = itr.next();
        //     val = myDeck.trimValueOne(card);
    
        //     cardVals.put(card,Integer.parseInt(val));
        // }
         
        // ArrayList<String>keys = new ArrayList<String>(); 
        // keys.addAll(cardVals.keySet());
        // ArrayList<String> whatYouDrew = new ArrayList<String>();
        // whatYouDrew.addAll(myDeck.hitMe(keys));
       
        // for(int i = 0; i < cardVals.size();i++) {
        //   //char symbol =
        //   System.out.print("you drew: " + cardVals.get(whatYouDrew.get(i)));
        //   myDeck.console(myDeck.trimValueTwo(whatYouDrew.get(i)));
        //   k += myDeck.cardValuecounter(cardVals.get(whatYouDrew.get(i)));
        //   if(k > 21)
        //   {
        //       System.out.println("21");
        //       break;
        //   }
        //   System.out.println("Would you like to continue?"); 
        //   Scanner scanner = new Scanner(System.in); 
        //   String keyboard = scanner.nextLine(); 
        //   if(keyboard.equals("y"))
        //   {
        //       continue;
        //   }
        //   else
        //   {
        //       break;
        //   }
        // }
    }
}