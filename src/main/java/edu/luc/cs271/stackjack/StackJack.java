import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.PrintStream;
import java.util.*;

public class StackJack {

    private JTextField textEntry;
    final JTextField betEntry = new JTextField(10);
    
    final private DefaultListModel<String> list1 = new DefaultListModel<String>();
    final DefaultListModel<String> listModelNames = new DefaultListModel<String>();
    final DefaultListModel<String> listModelMoney = new DefaultListModel<String>();
    final DefaultListModel<String> listModelBet = new DefaultListModel<String>();
    
    final JButton buttonBet = new JButton("Bet");
    final JButton buttonHit = new JButton("Hit");
    final JButton buttonStand = new JButton("Stand");
    final JButton buttonDouble = new JButton("Double Down");
    final JButton buttonSplit = new JButton("Split");
    final JButton buttonDeal = new JButton("Deal");
    
    JList<String> listNames = new JList<String>();
    ArrayList<Player> table = new ArrayList<Player>();
    Deck deck = new Deck();
    Player dealer = new Player("Dealer");
    int gamemode = 0;
    int roundCount = 20;
        
    public StackJack() {
      players();
    }
    
    public void players(){
        final JFrame frame = new JFrame("Stackjack - Adding Players");
        frame.setVisible(true);
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBackground(new Color(57, 163, 55));
        frame.add(listPanel, BorderLayout.CENTER);
         
        final JList<String> list = new JList<String>(list1);
          
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setPreferredSize(new Dimension(300, 300));
        list.setFont(new Font("Arial",Font.PLAIN,32));
        
        JLabel addLabel = new JLabel("Add Players");
        addLabel.setFont(new Font("Arial",Font.ITALIC,36));
        JLabel title = new JLabel("STACKJACK");
        title.setFont(new Font("Arial",Font.BOLD,62));
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 0;
        listPanel.add(title,c);
        c.gridx = 0;
        c.gridy = 1;
        listPanel.add(addLabel,c);
        c.gridx = 0;
        c.gridy = 2;
        listPanel.add(list,c);
        
        
        // Action Panel
        JPanel actionPanel = new JPanel(new GridBagLayout());
        frame.add(actionPanel, BorderLayout.SOUTH);
        
        JLabel addPromptLabel = new JLabel("Add Players");
        addPromptLabel.setFont(new Font("Arial",Font.ITALIC,12));
        
        final JButton buttonRemove = new JButton("Remove Player");
        buttonRemove.setEnabled(false);
        final JButton buttonContinue = new JButton("Continue");
        buttonContinue.setEnabled(false);
        
        // Options Panel
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        frame.add(optionsPanel, BorderLayout.NORTH);
        optionsPanel.setBackground(new Color(201, 221, 255));
        JLabel decksLabel = new JLabel("Number of Decks");
        decksLabel.setFont(new Font("Arial",Font.PLAIN,28));
        
        JLabel gamemodeLabel = new JLabel("Game Mode");
        gamemodeLabel.setFont(new Font("Arial",Font.PLAIN,28));
        JLabel gamemodeTipLabel = new JLabel("(1) Standard (2) Race to $1k (3) Best of 20 Hands");
        gamemodeTipLabel.setFont(new Font("Arial",Font.PLAIN,12));
        
        JSlider numberDecks = new JSlider(JSlider.HORIZONTAL, 1, 8, 6);
        numberDecks.setMajorTickSpacing(1);
        numberDecks.setMinorTickSpacing(0);
        numberDecks.setPaintTicks(true);
        numberDecks.setPaintLabels(true);
        numberDecks.setBackground(new Color(201, 221, 255));
        
        JSlider numberGamemode = new JSlider(JSlider.HORIZONTAL, 1, 3, 1);
        numberGamemode.setMajorTickSpacing(1);
        numberGamemode.setMinorTickSpacing(0);
        numberGamemode.setPaintTicks(true);
        numberGamemode.setPaintLabels(true);
        numberGamemode.setBackground(new Color(201, 221, 255));
        
        c.insets = new Insets(10,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(decksLabel,c);
        c.gridx = 1;
        c.gridy = 0;
        optionsPanel.add(numberDecks,c);
        c.gridx = 0;
        c.gridy = 1;
        optionsPanel.add(gamemodeLabel,c);
        c.gridx = 1;
        c.gridy = 1;
        optionsPanel.add(numberGamemode,c);
        c.gridx = 2;
        c.gridy = 1;
        optionsPanel.add(gamemodeTipLabel,c);
        
        //numberDecks slider
//        numberDecks.addChangeListener(new ChangeListener() {
//          @Override
//          void stateChanged(ChangeEvent e) {
//            // handle click
//          }
//        });
        
        //buttonRemove
        buttonRemove.addActionListener(
          new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                list1.remove(index);
                table.remove(index);
                
                int size = list1.getSize();
                
                if (size == 0) {
                  buttonRemove.setEnabled(false);
                  buttonContinue.setEnabled(false);
                  
                } else { //Select an index.
                  if (index == list1.getSize()) {
                    //removed item in last position
                    index--;
                  }
                  
                  list.setSelectedIndex(index);
                  list.ensureIndexIsVisible(index);
                }
              }
         }
        );
          
        //buttonContinue
        buttonContinue.addActionListener(
          new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                game();
              }
         }
        );
        
        // textEntry
        textEntry = new JTextField(20);
        textEntry.addActionListener(
          new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                if(list1.getSize() <= 6) {
                  String text = textEntry.getText();
                  list1.addElement(text);
                  table.add(new Player(text));
                  textEntry.setText("");
                  buttonRemove.setEnabled(true);
                  buttonContinue.setEnabled(true);
                }
              }
          }
        );
        
        // adding compenents to panel
        c.insets = new Insets(10,10,10,10);
        actionPanel.add(textEntry);
        c.gridx = 1;
        c.gridy = 0;
        actionPanel.add(addPromptLabel, c);
        c.gridx = 2;
        c.gridy = 0;
        actionPanel.add(buttonRemove, c);
        c.gridx = 3;
        c.gridy = 0;
        actionPanel.add(buttonContinue, c);
    }
    
    
    
//    game method
    
    
    public void game() {
        JFrame frameGame = new JFrame("Stackjack");
        frameGame.setVisible(true);
        frameGame.setSize(1200,800);
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel background = new JPanel();
        background.setBackground(new Color(57, 163, 55));
        frameGame.add(background);
        
//        outputPanel
        JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBackground(new Color(57, 163, 55));
        
        buttonDeal.setEnabled(false);
        
        final JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        textArea.setFont(new Font("Courier New",Font.PLAIN,20));
        textArea.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        scrollPane.setPreferredSize(new Dimension(700, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputPanel.add(scrollPane);
        frameGame.add(outputPanel, BorderLayout.CENTER);
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 1;
        outputPanel.add(buttonDeal, c);
        
        
//        tablePanel
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(new Color(20, 200, 55));
        frameGame.add(tablePanel, BorderLayout.WEST);
        
        // labels
        JLabel playersLabel = new JLabel("Players");
        playersLabel.setFont(new Font("Arial",Font.PLAIN,24));
        JLabel moneyLabel = new JLabel("Money");
        moneyLabel.setFont(new Font("Arial",Font.ITALIC,24));
        JLabel betLabel = new JLabel("Bet");
        betLabel.setFont(new Font("Arial",Font.BOLD,24));
        
        // lists
        for(int i = 0; i < table.size(); i++) {
          listModelNames.addElement(table.get(i).getName());
        }
        listNames = new JList<String>(listModelNames);
        listNames.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listNames.setLayoutOrientation(JList.VERTICAL_WRAP);
        listNames.setVisibleRowCount(-1);
        listNames.setPreferredSize(new Dimension(100, 250));
        listNames.setFont(new Font("Arial",Font.PLAIN,24));
        listNames.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        
        for(int i = 0; i < table.size(); i++) {
          String amt = "" + table.get(i).getMoney();
          listModelMoney.addElement(amt);
        }
        final JList<String> listMoney = new JList<String>(listModelMoney);
        listMoney.setEnabled(false);
        listMoney.setLayoutOrientation(JList.VERTICAL_WRAP);
        listMoney.setVisibleRowCount(-1);
        listMoney.setPreferredSize(new Dimension(100, 250));
        listMoney.setFont(new Font("Arial",Font.ITALIC,24));
        listMoney.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        
        for(int i = 0; i < table.size(); i++) {
          String amt = "" + table.get(i).getBet();
          listModelBet.addElement(amt);
        }
        final JList<String> listBet = new JList<String>(listModelBet);
        listBet.setEnabled(false);
        listBet.setLayoutOrientation(JList.VERTICAL_WRAP);
        listBet.setVisibleRowCount(-1);
        listBet.setPreferredSize(new Dimension(100, 250));
        listBet.setFont(new Font("Arial",Font.BOLD,24));
        listBet.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        
        // arrangment
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 0;
        tablePanel.add(playersLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        tablePanel.add(moneyLabel, c);
        c.gridx = 2;
        c.gridy = 0;
        tablePanel.add(betLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        tablePanel.add(listNames,c);
        c.gridx = 1;
        c.gridy = 1;
        tablePanel.add(listMoney,c);
        c.gridx = 2;
        c.gridy = 1;
        tablePanel.add(listBet,c);
        
        
//        actionPanel
        JPanel actionPanel = new JPanel(new GridBagLayout());
        frameGame.add(actionPanel, BorderLayout.SOUTH);
        buttonHit.setEnabled(false);
        buttonStand.setEnabled(false);
        buttonDouble.setEnabled(false);
        buttonSplit.setEnabled(false);
        
        //betEntry
        betEntry.addActionListener(
          new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                
              }
          }
        );
        
        //buttonBet
        buttonBet.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String bet = betEntry.getText();
               table.get(listNames.getSelectedIndex()).makeBet(bet);
               betEntry.setText("");
               listModelBet.remove(listNames.getSelectedIndex());
               String amt = "" + table.get(listNames.getSelectedIndex()).getBet();
               listModelBet.add(listNames.getSelectedIndex(), amt);
               System.out.println("\n  * " + table.get(listNames.getSelectedIndex()).getName() + "'s bet is $" + amt);
               
               boolean allBet = true;
               for(int i = 0; i < table.size(); i++) {
                 if(table.get(i).getBet() == 0 && table.get(i).getMoney() > 0){
                   allBet = false;
                 }
                 if(table.get(i).getBet() > table.get(i).getMoney() && table.get(i).getMoney() > 0 ){
                   allBet = false;
                 }
               }
               if(allBet) {
                 buttonDeal.setEnabled(true);
               }
               
               int num = listNames.getSelectedIndex();
               if(num < table.size() - 1) {
                 listNames.setSelectedIndex(num + 1);
               }
           }
          }
        );
        
        //buttonDeal
        buttonDeal.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              deck.setDeck();
              System.out.println("\n"); 
              dealer.playAutoHand(deck);
              for(int i = 0; i < table.size(); i++) {
                if(table.get(i).getMoney()>0){
                  table.get(i).dealHand(deck);
                  System.out.println("");
                }
              }
              
              int num = 0;
              while(num < table.size()) {
                if(table.get(num).getMoney()>0){
                  System.out.println(table.get(num).getName() + ", it's your turn.\n");
                  if(table.size()>1) {table.get(num).showCards();}
                  listNames.setSelectedIndex(num);
                  buttonDeal.setEnabled(false);
                  buttonBet.setEnabled(false);
                  betEntry.setEnabled(false);
                  buttonHit.setEnabled(true);
                  buttonStand.setEnabled(true);
                  checkHit(table.get(num));
                  checkDouble(table.get(num));
                  checkSplit(table.get(num));
                  break;
                }
                else{
                  num++;
                }
              }
            }
          }
        );
        
        //buttonHit
        buttonHit.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Player current = table.get(listNames.getSelectedIndex());
              buttonDouble.setEnabled(false);
              buttonSplit.setEnabled(false);
              Card draw = deck.popStack();
              current.hit(draw, 0);
              System.out.println("> " + draw.getCardString());
              current.printTotal(0);
              if(current.countHand(0) == 21) {
                  current.changeTurnOver(true);
                  System.out.println("\n----------");
              }
              if(current.countHand(0) > 21) {
                  System.out.println("  *** Bust ***\n");
                  current.changeMoney(-current.getBet());
                  current.changeBust(true);
              }
              if(current.getTurnOver() || current.getBustStatus()) {
                checkNextPlayer();
              }
            }
          }
        );
        
        //buttonStand
        buttonStand.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Player current = table.get(listNames.getSelectedIndex());
              current.changeTurnOver(true);
              System.out.println("\n----------");
              if(current.getTurnOver() || current.getBustStatus()) {
                checkNextPlayer();
              }
            }
          }
        );
        
        //buttonSplit
        buttonSplit.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Player current = table.get(listNames.getSelectedIndex());
              Card first = current.getCard(0, 0);
              Card second = current.getCard(1, 0);
              System.out.println("@@   Left Hand   @@");
              System.out.println("> " + first.getCardString());
              System.out.println();
              System.out.println("@@   Right Hand   @@");
              System.out.println("> " + second.getCardString());
              //current.playSplitHand(deck, second, 1);
              current.changeTurnOver(true);
              System.out.print("\n----------\n");
              if(current.getTurnOver() || current.getBustStatus()) {
                checkNextPlayer();
              }
            }
          }
        );
        
        //buttonDouble
        buttonDouble.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Player current = table.get(listNames.getSelectedIndex());
              current.setBet(current.getBet() * 2);
              System.out.println("## Your bet is now $" + current.getBet() + " ##");
              Card draw = deck.popStack();
              current.hit(draw, 0);
              System.out.println("> " + draw.getCardString());
              current.printTotal(0);
              current.changeTurnOver(true);
              System.out.print("\n----------\n");
              if(current.getTurnOver() || current.getBustStatus()) {
                checkNextPlayer();
              }
            }
          }
        );
        
        
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 0;
        actionPanel.add(buttonBet, c);
        c.gridx = 1;
        c.gridy = 0;
        actionPanel.add(betEntry);
        c.gridx = 2;
        c.gridy = 0;
        actionPanel.add(buttonHit, c);
        c.gridx = 3;
        c.gridy = 0;
        actionPanel.add(buttonStand, c);
        c.gridx = 4;
        c.gridy = 0;
        actionPanel.add(buttonDouble, c);
        c.gridx = 5;
        c.gridy = 0;
        actionPanel.add(buttonSplit, c);
        
        //begin game
        betStart();
    }    
    
    public void betStart() {
      buttonHit.setEnabled(false);
      buttonStand.setEnabled(false);
      buttonDouble.setEnabled(false);
      buttonSplit.setEnabled(false);
      buttonBet.setEnabled(true);
      betEntry.setEnabled(true);
      for(int i = 0; i < table.size(); i++) {
          listNames.setSelectedIndex(i);
          if(table.get(i).getMoney() > 0) {
            System.out.println(table.get(i).getName() + ", place your bet.");     
          }
      }
      listNames.setSelectedIndex(0);
      
      boolean allBet = true;
      for(int i = 0; i < table.size(); i++) {
         if(table.get(i).getBet() == 0 || table.get(i).getBet() > table.get(i).getMoney()){
           allBet = false;
         }
      }
      if(allBet) {
        buttonDeal.setEnabled(true);
      }
      else {
        buttonDeal.setEnabled(false);
      }
    }
    
    public void checkNextPlayer() {
      int num = listNames.getSelectedIndex();
      while(num < table.size() - 1) {
        if(table.get(num+1).getMoney()>0){
          listNames.setSelectedIndex(num + 1);
          System.out.println("\n" + table.get(num + 1).getName() + ", it's your turn.\n");
          checkDouble(table.get(num+1));
          checkSplit(table.get(num+1));
          checkHit(table.get(num+1));
          table.get(num + 1).showCards();
          break;
        }
        else{
          num++;
        }
      }
      if(num >= table.size()-1){
        evaluate();
      }
    }
    
    
    public void checkHit(Player current) {
      if(current.countHand(0) == 21) {
        buttonHit.setEnabled(false);
      }
      else {
        buttonHit.setEnabled(true);
      }
    }
    public void checkDouble(Player current) {
      if(current.countHand(0) >= 9 && current.countHand(0) <= 11 && current.getBet() * 2 <= current.getMoney()) {
        buttonDouble.setEnabled(true);
      }
      else {
        buttonDouble.setEnabled(false);
      }
    }
    public void checkSplit(Player current) {
      if(current.checkPair() && current.getBet() * 2 <= current.getMoney()) {
        buttonSplit.setEnabled(true);
      }
      else {
        buttonSplit.setEnabled(false);
      }
    }
    
    public void evaluate() {
      //Dealer reveals
      dealer.revealHand(deck);
      //Compare hands
      for(int i = 0; i < table.size(); i++) {
        if(table.get(i).getMoney() > 0) {
            if(!dealer.getBlackjack()) {
                table.get(i).compareToDealer(dealer, 0);
                if(table.get(i).countHand(1) != 0) {
                    table.get(i).compareToDealer(dealer, 1);
                }
            }
            else if(dealer.getBlackjack() && table.get(i).getBlackjack()) {
                System.out.println("*** (~) " + table.get(i).getName() + " got a stand-off ***");
            }
            else {
                System.out.println("*** (-) " + table.get(i).getName() + " lost ***");
                table.get(i).changeMoney(-table.get(i).getBet());
            }
            // Reset player
            table.get(i).resetHand();
            listModelMoney.remove(i);
            String amt = "" + table.get(i).getMoney();
            listModelMoney.add(i, amt);
        }
        else if(table.get(i).getBet() > 0) {
            System.out.println("*** " + table.get(i).getName() + " is out of money! ***");
            table.get(i).resetHand();
            listModelMoney.remove(i);
            String amt = "" + table.get(i).getMoney();
            listModelMoney.add(i, amt);
        }
      }
            
      // Reset dealer
      System.out.println("\n");
      dealer.resetHand();
      roundCount--;
      betStart();
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StackJack();
            }
        });
    }
}