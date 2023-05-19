import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The GUI program implements an GUI for a card game
 * This is the only class of the program.
 * This class has two public methods, main and initiate.
 * This class has six inner classes, ReplaceCard1_Listener, ReplaceCard2_Listener, ReplaceCard3_Listener, StartListener, ResultListener, MenuitemListener
 */
public class GUI {
    private ImageIcon dealerCardImage_1;
    private ImageIcon dealerCardImage_2;
    private ImageIcon dealerCardImage_3;
    private ImageIcon playerCardImage_1;
    private ImageIcon playerCardImage_2;
    private ImageIcon playerCardImage_3;
    private JLabel dealerCard_1;
    private JLabel dealerCard_2;
    private JLabel dealerCard_3;
    private JLabel playerCard_1;
    private JLabel playerCard_2;
    private JLabel playerCard_3;
    private JTextField betInputTextField;
    private JLabel importantMessagesLabel;
    private JLabel remainingBudgetLabel;
    private int currentBet = 100;
    private int bet = 0;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean card1IsReplaced = false;
    private boolean card2IsReplaced = false;
    private boolean card3IsReplaced = false;
    private int replacedCardCount = 0;
    private ArrayList<String> cardDeck;
    private int cardCount = 0;

    /**
     * This main method creates a GUI instance and initiate it.
     * @param args This is not useful in this method.
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.initiate();
    }

    /**
     * This method initiates the GUi for the card game.
     * It adds components needed into the frame and initiate the card disk.
     */
    public void initiate() {
        dealerCardImage_1 = new ImageIcon("card_back.gif");
        dealerCardImage_2 = new ImageIcon("card_back.gif");
        dealerCardImage_3 = new ImageIcon("card_back.gif");
        playerCardImage_1 = new ImageIcon("card_back.gif");
        playerCardImage_2 = new ImageIcon("card_back.gif");
        playerCardImage_3 = new ImageIcon("card_back.gif");

        dealerCard_1 = new JLabel();
        dealerCard_2 = new JLabel();
        dealerCard_3 = new JLabel();
        playerCard_1 = new JLabel();
        playerCard_2 = new JLabel();
        playerCard_3 = new JLabel();

        dealerCard_1.setIcon(dealerCardImage_1);
        dealerCard_2.setIcon(dealerCardImage_2);
        dealerCard_3.setIcon(dealerCardImage_3);
        playerCard_1.setIcon(playerCardImage_1);
        playerCard_2.setIcon(playerCardImage_2);
        playerCard_3.setIcon(playerCardImage_3);

        JButton rpCard1 = new JButton("Replace Card 1");
        JButton rpCard2 = new JButton("Replace Card 2");
        JButton rpCard3 = new JButton("Replace Card 3");
        JButton startButton = new JButton("Start");
        JButton resultButton = new JButton("Result");

        JLabel betLabel = new JLabel("Bet: $");
        importantMessagesLabel = new JLabel("Please place you bet! ");
        remainingBudgetLabel = new JLabel("Amount of money you have: $" + currentBet);

        betInputTextField = new JTextField(10);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Control");
        JMenuItem menuItem = new JMenuItem("Exit");

        menu.add(menuItem);
        menuBar.add(menu);

        JPanel mainPanel = new JPanel();
        JPanel dealerPanel = new JPanel();
        JPanel playerPanel = new JPanel();
        JPanel rpCardBtnPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel infoPanel = new JPanel();

        dealerPanel.add(dealerCard_1);
        dealerPanel.add(dealerCard_2);
        dealerPanel.add(dealerCard_3);

        playerPanel.add(playerCard_1);
        playerPanel.add(playerCard_2);
        playerPanel.add(playerCard_3);

        rpCardBtnPanel.add(rpCard1);
        rpCardBtnPanel.add(rpCard2);
        rpCardBtnPanel.add(rpCard3);

        buttonPanel.add(betLabel);
        buttonPanel.add(betInputTextField);
        buttonPanel.add(startButton);
        buttonPanel.add(resultButton);

        infoPanel.add(importantMessagesLabel);
        infoPanel.add(remainingBudgetLabel);

        mainPanel.setLayout(new GridLayout(5, 1));
        mainPanel.add(dealerPanel);
        mainPanel.add(playerPanel);
        mainPanel.add(rpCardBtnPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(infoPanel);

        dealerPanel.setBackground(Color.green);
        playerPanel.setBackground(Color.green);
        rpCardBtnPanel.setBackground(Color.green);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(mainPanel);
        rpCard1.addActionListener(new ReplaceCard1_Listener());
        rpCard2.addActionListener(new ReplaceCard2_Listener());
        rpCard3.addActionListener(new ReplaceCard3_Listener());
        startButton.addActionListener(new StartListener());
        resultButton.addActionListener(new ResultListener());

        frame.setJMenuBar(menuBar);
        menuItem.addActionListener(new MenuitemListener());

        frame.setTitle("A Simple Card Game");
        frame.setSize(400, 700);
        frame.setVisible(true);

        cardDeck = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            for (int j = 1; j < 14; j++){
                String card = String.valueOf(i) + j;
                cardDeck.add(card);
            }
        }
    }
    private void startGame(){
        if (gameOver){
            JOptionPane.showMessageDialog(null, """
                    Game over!
                    You have no more Money!
                    Please start a new game!""");
            return;
        }
        if (gameStarted){
            JOptionPane.showMessageDialog(null, "The game has started!");
            return;
        }
        try {
            bet = Integer.parseInt(betInputTextField.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid input!");
            return;
        }
        if (bet <= 0){
            JOptionPane.showMessageDialog(null, "Invalid input!");
            return;
        }
        if (bet > currentBet){
            JOptionPane.showMessageDialog(null, "You don't have enough bet!");
            return;
        }
        gameStarted = true;
        importantMessagesLabel.setText("Your current bet is: $" + bet + " ");
        shuffleCard();

        String cardFileName;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        playerCardImage_1 = new ImageIcon(cardFileName);
        playerCard_1.setIcon(playerCardImage_1);
        cardCount++;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        playerCardImage_2 = new ImageIcon(cardFileName);
        playerCard_2.setIcon(playerCardImage_2);
        cardCount++;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        playerCardImage_3 = new ImageIcon(cardFileName);
        playerCard_3.setIcon(playerCardImage_3);
        cardCount++;
    }
    private void showResult(){
        if (gameOver){
            JOptionPane.showMessageDialog(null, """
                    Game over!
                    You have no more Money!
                    Please start a new game!""");
            return;
        }
        if (!gameStarted){
            JOptionPane.showMessageDialog(null, "The game hasn't started yet!");
            return;
        }
        gameStarted = false;

        String cardFileName;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        dealerCardImage_1 = new ImageIcon(cardFileName);
        dealerCard_1.setIcon(dealerCardImage_1);
        cardCount++;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        dealerCardImage_2 = new ImageIcon(cardFileName);
        dealerCard_2.setIcon(dealerCardImage_2);
        cardCount++;
        cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
        dealerCardImage_3 = new ImageIcon(cardFileName);
        dealerCard_3.setIcon(dealerCardImage_3);
        cardCount++;

        int playerSpecial = 0;
        int dealerSpecial = 0;
        int playerSum = 0;
        int dealerSum = 0;

        boolean playerWon = false;

        String playerCardImage_1_FileName = playerCard_1.getIcon().toString();
        int playerCard1Value = Integer.parseInt(playerCardImage_1_FileName.substring(playerCardImage_1_FileName.lastIndexOf("/") + 7, playerCardImage_1_FileName.lastIndexOf(".")));
        if (playerCard1Value > 10)
            playerSpecial++;
        else
            playerSum += playerCard1Value;

        String playerCardImage_2_FileName = playerCard_2.getIcon().toString();
        int playerCard2Value = Integer.parseInt(playerCardImage_2_FileName.substring(playerCardImage_2_FileName.lastIndexOf("/") + 7, playerCardImage_2_FileName.lastIndexOf(".")));
        if (playerCard2Value > 10)
            playerSpecial++;
        else
            playerSum += playerCard2Value;

        String playerCardImage_3_FileName = playerCard_3.getIcon().toString();
        int playerCard3Value = Integer.parseInt(playerCardImage_3_FileName.substring(playerCardImage_3_FileName.lastIndexOf("/") + 7, playerCardImage_3_FileName.lastIndexOf(".")));
        if (playerCard3Value > 10)
            playerSpecial++;
        else
            playerSum += playerCard3Value;

        String dealerCardImage_1_FileName = dealerCard_1.getIcon().toString();
        int dealerCard1Value = Integer.parseInt(dealerCardImage_1_FileName.substring(dealerCardImage_1_FileName.lastIndexOf("/") + 7, dealerCardImage_1_FileName.lastIndexOf(".")));
        if (dealerCard1Value > 10)
            dealerSpecial++;
        else
            dealerSum += dealerCard1Value;

        String dealerCardImage_2_FileName = dealerCard_2.getIcon().toString();
        int dealerCard2Value = Integer.parseInt(dealerCardImage_2_FileName.substring(dealerCardImage_2_FileName.lastIndexOf("/") + 7, dealerCardImage_2_FileName.lastIndexOf(".")));
        if (dealerCard2Value > 10)
            dealerSpecial++;
        else
            dealerSum += dealerCard2Value;

        String dealerCardImage_3_FileName = dealerCard_3.getIcon().toString();
        int dealerCard3Value = Integer.parseInt(dealerCardImage_3_FileName.substring(dealerCardImage_3_FileName.lastIndexOf("/") + 7, dealerCardImage_3_FileName.lastIndexOf(".")));
        if (dealerCard3Value > 10)
            dealerSpecial++;
        else
            dealerSum += dealerCard3Value;

        if (playerSpecial > dealerSpecial)
            playerWon = true;
        else if (dealerSpecial == playerSpecial) {
            if ((playerSum % 10) > (dealerSum % 10)) {
                playerWon = true;
            }
        }
        if (playerWon) {
            JOptionPane.showMessageDialog(null, "Congregations! You win this round!");
            currentBet += bet;
            importantMessagesLabel.setText("Please place your bet! ");
            remainingBudgetLabel.setText("Amount of money you have: $ " + currentBet);
        } else if ((currentBet - bet) == 0){
            JOptionPane.showMessageDialog(null, """
                    Game over!
                    You have no more Money!
                    Please start a new game!""");
            currentBet -= bet;
            gameOver = true;
            importantMessagesLabel.setText("You have no more money! ");
            remainingBudgetLabel.setText("Please start a new game!");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Sorry! The Dealer wins this round!");
            currentBet -= bet;
            importantMessagesLabel.setText("Please place your bet! ");
            remainingBudgetLabel.setText("Amount of money you have: $ " + currentBet);
        }
        dealerCardImage_1 = new ImageIcon("card_back.gif");
        dealerCardImage_2 = new ImageIcon("card_back.gif");
        dealerCardImage_3 = new ImageIcon("card_back.gif");
        playerCardImage_1 = new ImageIcon("card_back.gif");
        playerCardImage_2 = new ImageIcon("card_back.gif");
        playerCardImage_3 = new ImageIcon("card_back.gif");

        dealerCard_1.setIcon(dealerCardImage_1);
        dealerCard_2.setIcon(dealerCardImage_2);
        dealerCard_3.setIcon(dealerCardImage_3);
        playerCard_1.setIcon(playerCardImage_1);
        playerCard_2.setIcon(playerCardImage_2);
        playerCard_3.setIcon(playerCardImage_3);

        card1IsReplaced = false;
        card2IsReplaced = false;
        card3IsReplaced = false;
        replacedCardCount = 0;
        cardCount = 0;
    }

    private void shuffleCard(){
        for (int i = 0; i < cardDeck.size(); i++){
            int index = (int) (Math.random() * cardDeck.size());
            String temp = cardDeck.get(i);
            cardDeck.set(i, cardDeck.get(index));
            cardDeck.set(index, temp);
        }
    }

    /**
     * This class is the action listener of the replaceCard1 bottom.
     * If activated, it will replace the first card of the player.
     * It will be disabled if that card has already been replaced or there are 2 cards have already been replaced.
     */
    class ReplaceCard1_Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (!gameStarted){
                JOptionPane.showMessageDialog(null, "The game hasn't started yet!");
                return;
            }
            if (card1IsReplaced){
                JOptionPane.showMessageDialog(null, "You have replaced this card!");
                return;
            }
            if (replacedCardCount == 2){
                JOptionPane.showMessageDialog(null, "You can only replace two cards!");
                return;
            }
            String cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
            playerCardImage_1 = new ImageIcon(cardFileName);
            playerCard_1.setIcon(playerCardImage_1);
            cardCount++;
            card1IsReplaced = true;
            replacedCardCount++;
        }
    }
    /**
     * This class is the action listener of the replaceCard2 bottom.
     * If activated, it will replace the second card of the player.
     * It will be disabled if that card has already been replaced or there are 2 cards have already been replaced.
     */
    class ReplaceCard2_Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (!gameStarted){
                JOptionPane.showMessageDialog(null, "The game hasn't started yet!");
                return;
            }
            if (card2IsReplaced){
                JOptionPane.showMessageDialog(null, "You have replaced this card!");
                return;
            }
            if (replacedCardCount == 2){
                JOptionPane.showMessageDialog(null, "You can only replace two cards!");
                return;
            }
            String cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
            playerCardImage_2 = new ImageIcon(cardFileName);
            playerCard_2.setIcon(playerCardImage_2);
            cardCount++;
            card2IsReplaced = true;
            replacedCardCount++;
        }
    }
    /**
     * This class is the action listener of the replaceCard3 bottom.
     * If activated, it will replace the third card of the player.
     * It will be disabled if that card has already been replaced or there are 2 cards have already been replaced.
     */
    class ReplaceCard3_Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (!gameStarted){
                JOptionPane.showMessageDialog(null, "The game hasn't started yet!");
                return;
            }
            if (card3IsReplaced){
                JOptionPane.showMessageDialog(null, "You have replaced this card!");
                return;
            }
            if (replacedCardCount == 2){
                JOptionPane.showMessageDialog(null, "You can only replace two cards!");
                return;
            }
            String cardFileName = "card_" + cardDeck.get(cardCount) + ".gif";
            playerCardImage_3 = new ImageIcon(cardFileName);
            playerCard_3.setIcon(playerCardImage_3);
            cardCount++;
            card3IsReplaced = true;
            replacedCardCount++;
        }
    }
    /**
     * This class is the action listener of the start bottom.
     * If activated, it will start a new game.
     * The startGame() function will shuffle the card disk, give the player three cards, and let the player replace them.
     */
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            startGame();
        }
    }
    /**
     * This class is the action listener of the result bottom.
     * If activated, it will show the result.
     * The showResult() function will show the cards of the dealer, calculate the value of cards, and tell the player the result.
     */
    class ResultListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            showResult();
        }
    }
    /**
     * This class is the action listener of the menuitem.
     * If activated, it will quit the game.
     */
    class MenuitemListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    }
}