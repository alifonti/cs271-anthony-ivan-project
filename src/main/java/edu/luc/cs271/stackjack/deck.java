import java.util.*;

public class deck 
{
    LinkedHashSet<String> Spades = new LinkedHashSet<>();
    LinkedHashSet<String> Hearts = new LinkedHashSet<>();
    LinkedHashSet<String> Diamonds = new LinkedHashSet<>();
    LinkedHashSet<String> Clubs = new LinkedHashSet<>();
    ArrayList<Integer> Hand = new ArrayList<Integer>();
    
   
    public LinkedHashSet loadDeck()
    {
        LinkedHashSet<String> allCards = new LinkedHashSet<>();
        for(Integer i = 1; i < 14; i++)
            {
               
               String a = Integer.toString(i); 
               // System.out.println(a);
                String spadesValue = "s" + a;
                String heartsValue = "h" + a; 
                String diamondsValue = "d" + a; 
                String clubsValue = "c" + a;
                //System.out.println(spadesValue); 
                //results we want
                Spades.add(spadesValue);
                Hearts.add(heartsValue);
                Diamonds.add(diamondsValue); 
                Clubs.add(clubsValue);
             }
            
             allCards.addAll(Spades);
             allCards.addAll(Hearts);
             allCards.addAll(Diamonds);
             allCards.addAll(Clubs);
             return allCards;   
    }
    public ArrayList<String> hitMe(ArrayList<String> valueSize)
    {
      
       ArrayList<Integer> nums = new ArrayList<>();
       ArrayList<String>randomizedKeys = new ArrayList<>();
       nums.addAll(drawAtRandom());
        for(int i=0; i < valueSize.size(); i++)
        {
            
            
          
            randomizedKeys.add(valueSize.get(nums.get(i)));
          
            
            
        }
        return randomizedKeys;
        
    }
    public ArrayList<Integer> drawAtRandom() {
        ArrayList<Integer> numbers = new ArrayList<>();
        // range 0-51
        for (int i = 0; i <= 51; i++)
        {
            
            numbers.add(i);
           Collections.shuffle(numbers);
           
        
        }
        return numbers;
    }
    
    public void console(char cardType)
    {
        switch(cardType)
        {
        case 's':
        System.out.println(" of Spades");
        break;
        case 'd':
        System.out.println(" of Diamonds");
        break;
        case 'c':
        System.out.println(" of Clubs"); 
        break;
        case 'h':
        System.out.println(" of Hearts");
        break; 
        default :
            System.out.println("Invalid card");
        }
       
    }
    public String trimValueOne(String value)
    {
        if(value.length() > 2)
            {
                value = value.substring(1,3);
            }
            else
            {
                value = value.substring(1);
            }
            return value;
    }
    public char trimValueTwo(String value)
    {
        
            
        char sValue = value.charAt(0); 
        return sValue;
           
    }
    public int cardValuecounter(int input) 
    {
        int i = 1; 
        while(i < input)
        {
           i++;
           //System.out.println(i);
        }
        return i; 
        
    }
   
    
}