import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;


/*This game is played as follows:
    1)User deposits an amount (Must be above the minimum bet value of $2)
    2)Press enter to deposit into playing wallet
    3)Select a bet ($2,$5 or $10)
    4)Select higher or lower
    5)If selection was correct the game continues with the second card hidden from view
    6)As long as the user has sufficient funds in the game wallet and guess correctly the game continues
    7)Once the user guesses incorrectly the game is over and the relevant bet amount is deducted from the game wallet
    8)The user is shown the amount they have won this round in the results area
    9)The user is asked if they want to play again, they are given the option of two buttons yes to play again, no to end the game.
    10)If the user wishes to play again the program restarts
    **If no bet is placed and the user selects higher or lower, the user is informed that no bet was placed and the round is over, they are asked if they want to play again
    **The maximum length of a round is 20 cards
    **Ace cards have a value of one in this game

*/

class GUI extends JFrame {
    int width = 1200;
    int height = 800;
    //◆','♠','♣','♥'

    //colours
    Color bkgColor = new Color(39, 119, 20);
    Color bColor = new Color(204, 204, 0);
    Color bet1Colour = new Color(255, 0, 0);
    Color bet2Colour = new Color(0, 0, 255);
    Color bet3Colour = new Color(255, 102, 0);
    Color yesColour = new Color(0,255,51);
    Color noColour = new Color(255, 51, 51);

    //buttons
    JButton bHi = new JButton();
    JButton bLo = new JButton();
    JButton bet1 = new JButton();
    JButton bet2 = new JButton();
    JButton bet3 = new JButton();
    JLabel card1 = new JLabel();
    JLabel card1a = new JLabel();

//
    JLabel card2 = new JLabel();
    JLabel card2a = new JLabel();
//        JLabel card1Icon = new JLabel();
//        JLabel card2Icon = new JLabel();

    JLabel results = new JLabel("RESULTS");
    JLabel resultsMsg = new JLabel();
    JLabel closeMsg = new JLabel("It's been fun, see you soon! ");

    //font
    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font betFont = new Font("Times New Roman", Font.PLAIN, 16);
    Font walletFont = new Font("Times New Roman", Font.BOLD, 24);
    Font closeFont = new Font("Times New Roman", Font.BOLD,24);
    Font cardFont = new Font("Helvetica", Font.BOLD,22);
    Font iconFont = new Font("Helvetica",Font.PLAIN,30);

    //game grid and dimensions
    int gridX = 50;
    int gridY = 25;
    int gridW = 1100;
    int gridH = 325;

    //card placement and dimensions
//    int spacing = 10;
//    int edgeCurve = 10;
    int card1X = 75;
    int card1Y = 50;
    int card1W = 180;
    int card1H = 250;

    int card2X = 900;
    int card2Y = 50;
    int card2W = 180;
    int card2H = 250;

    //results grid and dimensions
    int resX = 800;
    int resY = 400;
    int resW = 280;
    int resH = 280;

    //wallet grid and dimensions
    int wX = 50;
    int wY = 400;
    int wW = 200;
    int wH = 80;

    //Required lists

    ArrayList<Card> deck = new ArrayList<Card>();
    ArrayList<Card> round = new ArrayList<Card>(20);

    //global scope variables

    boolean choice;
    int rand = new Random().nextInt(52);
    String wager = new String("");
    String wagerString = new String("");
    int wagerNum = 0;
    JLabel walletLabel;
    JTextField wallet;
    int balance = 0;
    JLabel balanceLabel;
    JLabel amountLabel;

    Board board;
    char randomSuit;



    boolean winning = false;



    boolean gameOver = false;
    boolean boolBet1 = false;
    boolean boolBet2 = false;
    boolean boolBet3 = false;
    int betSize = 0;
    public int x = 0;

    public GUI() {
        this.setSize(width, height);
        this.setTitle("HiLo");
        this.setVisible(true);          //gui is invisible to user unless we include this statement
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board = new Board();
        this.setContentPane(board);
        this.setLayout(null);

        walletLabel = new JLabel();
        walletLabel.setBounds(55, 410, 200, 20);
        walletLabel.setText("WALLET");
        walletLabel.setFont(walletFont);

        balanceLabel = new JLabel();
        balanceLabel.setBounds(resX+40, resY-100, resW, resH);

        amountLabel = new JLabel();
        amountLabel.setBounds(55,440,200,30);


        wallet = new JTextField();
        wallet.setBounds(50, 510, 200, 20);
        wallet.setEditable(true);

        results.setBounds(resX+5, resY - 120, resW+100,resH);
        results.setFont(walletFont);

        resultsMsg.setBounds(resX+5, resY - 100,resW,resH);

        closeMsg.setBounds(resX+5, resY+250, 300,25);
        closeMsg.setFont(closeFont);
        closeMsg.setVisible(false);

//        card1Icon.setBounds(165,170,100,100);
//        String url = new String("https://www.google.com/search?q=spades+keyboard+icon&sxsrf=ALeKk00N9F-887g9_6tiRCwoHCKsyl7sHA:1628868386777&source=lnms&tbm=isch&sa=X&sqi=2&ved=2ahUKEwjSitHMp67yAhUCGFkFHdnFAdcQ_AUoAXoECAEQAw&biw=1920&bih=937#imgrc=r_ATCwncaX_dWM");
//        ImageIcon imgThisImg = new ImageIcon(url);
//        card1Icon.setIcon(imgThisImg);
//        card1Icon.setVisible(true);



        board.add(closeMsg);
        board.add(resultsMsg);
        board.add(results);
        board.add(wallet);
        board.add(amountLabel);
        board.add(walletLabel);
        board.add(balanceLabel);

        //deposit button
        deposit dep = new deposit();
        wallet.addActionListener(dep);

        //higher button
        hiAct selectHi = new hiAct();
        bHi.addActionListener(selectHi);

        bHi.setBounds(400, 400, 120, 80);
        bHi.setBackground(bColor);
        bHi.setText("Higher");
        bHi.setFont(buttonFont);
        board.add(bHi);

        //lower button
        loAct selectLo = new loAct();
        bLo.addActionListener(selectLo);

        bLo.setBounds(560, 400, 120, 80);
        bLo.setBackground(bColor);
        bLo.setText("Lower");
        bLo.setFont(buttonFont);
        board.add(bLo);

        //small bet button
        smallBet selectSmall = new smallBet();
        bet1.addActionListener(selectSmall);

        bet1.setBounds(400, 520, 60, 60);
        bet1.setBackground(bet1Colour);
        bet1.setText("$2");
        bet1.setFont(betFont);
        board.add(bet1);

        //medium bet button
        medBet selectMed = new medBet();
        bet2.addActionListener(selectMed);

        bet2.setBounds(515, 520, 60, 60);
        bet2.setBackground(bet2Colour);
        bet2.setText("$5");
        bet2.setFont(betFont);
        board.add(bet2);

        //medium bet button
        lrgBet selectLrg = new lrgBet();
        bet3.addActionListener(selectLrg);

        bet3.setBounds(620, 520, 60, 60);
        bet3.setBackground(bet3Colour);
        bet3.setText("$10");
        bet3.setFont(betFont);
        board.add(bet3);




        String family = null;
        for (int suit = 0; suit < 4; suit++) {
            if (suit == 0) {
                family = "Spades";
            } else if (suit == 1) {
                family = "Diamonds";
            } else if (suit == 2) {
                family = "Hearts";
            } else
                family = "Clubs";
            for (int i = 1; i < 14; i++) {                  //nested for loop
                deck.add(new Card(i, family));               //adding all cards into a "deck"
            }
        }

            do {                                                //fills the 'round' list with 10 random cards

                rand = new Random().nextInt(52);
                round.add(deck.get(rand));
                deck.get(rand).used = true;

                rand = new Random().nextInt(52);
                while (true) {
                    if (deck.get(rand).used == false) {
                        round.add(deck.get(rand));
                        deck.get(rand).used = true;
                        break;
                    } else
                        rand = new Random().nextInt(52);
                }
            } while (round.size() <= 51);

        //random suit generator

        randomSuit = getSuit();
        card1.setBounds(80, 55, 100, 40);
        card1.setText(round.get(x).symbol + randomSuit);
        card1.setFont(cardFont);

        card1a.setBounds(215, 265, 100, 40);
        card1a.setText(round.get(x).symbol + randomSuit);
        card1a.setFont(cardFont);

        //setting up second cards corners (labels)
        card2.setBounds(905, 55, 100, 40);
        card2.setFont(cardFont);
        card2a.setBounds(1040, 265, 100, 40);
        card2a.setFont(cardFont);

        board.add(card1);
        board.add(card1a);
        board.add(card2);
        board.add(card2a);

        for (int i = 0; i < 52; i++) {
            System.out.println(round.get(i).name + " of " + round.get(i).suit);
        }

    }   // END GUI CONSTRUCTOR
    public char getSuit() {
        char[] suitsArr = {'◆', '♠', '♣', '♥'};
        int suitRand = new Random().nextInt(4);

        return suitsArr[suitRand];
    }

    public class Board extends JPanel {        //JPanel is a container for storage of components
        public void paintComponent(Graphics g) {    //allows colour insertion
            g.setColor(bkgColor);
            g.fillRect(0, 0, width, height);        //fills the JPanel with the selected colour

            //temp game area grid
            g.setColor(Color.black);
            g.drawRect(gridX, gridY, gridW, gridH);
            //temp results area
//            g.setColor(Color.black);
//            g.drawRect(resX, resY, resW, resH);
            //wallet area
            g.setColor(Color.black);
            g.drawRect(wX, wY, wW, wH);

            //card1
            g.setColor(Color.black);
            g.drawRect(card1X, card1Y, card1W, card1H);
            g.setColor(Color.white);
            g.fillRect(card1X, card1Y, card1W, card1H);

            //card2
            g.setColor(Color.black);
            g.drawRect(card2X, card2Y, card2W, card2H);
            g.setColor(Color.white);
            g.fillRect(card2X, card2Y, card2W, card2H);

        }
    }
    public void playAgain() {
        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");
        JLabel playAgain = new JLabel("Do you want to play again?");

        playAgain.setBounds(resX+5, resY-50, resW, resH);
        playAgain.setFont(walletFont);

        yesBtn.setBounds(resX+70, resY+120,60,70);
        yesBtn.setBackground(yesColour);

        noBtn.setBounds(resX+135, resY+120,60,70);
        noBtn.setBackground(noColour);

        board.add(playAgain);
        board.add(yesBtn);
        board.add(noBtn);

        yesPlay yes =  new yesPlay();
        yesBtn.addActionListener(yes);

        noPlay no =  new noPlay();
        noBtn.addActionListener(no);

    }
    public class yesPlay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("we will play another round");
            new GUI();

        }
    }
    public class noPlay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeMsg.setVisible(true);
            System.out.println("we wont play another round");
        }
    }
//    public String convert(int a){
//        wager = wallet.getText();
//        wagerNum = Integer.parseInt(wager);
//        wagerNum += a;
//        wagerString = String.valueOf(wagerNum);
//
//        return wagerString;
//    }

    public class hiAct implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("clicked hi");               //testing event listener
            choice = true;
            char randomSuitHi = getSuit();


            if (choice && (round.get(x + 1).value > round.get(x).value)) {
                winning = true;
                if (boolBet1){
                    balance += 2;
                    resultsMsg.setText("You won $2");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else if (boolBet2){
                    balance += 5;
                    resultsMsg.setText("You won $5");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else if (boolBet3){
                    balance += 10;
                    resultsMsg.setText("You won $10");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else{
                    resultsMsg.setText("No bet was placed");
                    playAgain();
//                    System.out.println("You were correct!");
                }
//                System.out.println("Correct choice, You win!");

            } else if ((choice) && (round.get(x + 1).value < round.get(x).value)) {
                System.out.println("You lose!");
//                resultsMsg.setText("You lose!");
                if (boolBet1){
                    balance -= 2;
                    resultsMsg.setText("You lost $2! You won " + balance + " euro this round.");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else if (boolBet2){
                    balance -= 5;
                    resultsMsg.setText("You lost $5! You won " + balance + " euro this round.");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else if (boolBet3){
                    balance -= 10;
                    resultsMsg.setText("You lost $10! You won " + balance + " euro this round.");
                    amountLabel.setText("$" + String.valueOf(balance));
                }else{
                    resultsMsg.setText("No bet was placed.");
                    playAgain();
//                    System.out.println("You were incorrect!");
                }
                winning = false;

            } else if ((round.get(x + 1).value == round.get(x).value)) {
                System.out.println("You lost on a tie!");
                resultsMsg.setText("You lost on a tie! You won " + balance + " euro this round.");
                winning = false;

            }
             if ((winning)) {
                x++;
                card1.setText(round.get(x).symbol + randomSuitHi);
                board.add(card1);
                card1a.setText(round.get(x).symbol + randomSuitHi );
                board.add(card1a);

                card2.setText("");
                card2a.setText("");

                amountLabel.setText("$" + String.valueOf(balance));
            }else{
                 gameOver = true;

                System.out.println("Loser");

                card1.setText(round.get(x).symbol + randomSuitHi);
                board.add(card1);
                card1a.setText(round.get(x).symbol + randomSuitHi);
                board.add(card1a);
                char randomSuitHi2 = getSuit();
                card2.setText(round.get(x + 1).symbol + randomSuitHi2);
                card2a.setText(round.get(x + 1).symbol + randomSuitHi2);
                board.add(card2);
                board.add(card2a);

        playAgain();
            }

        }
    }



    public class loAct implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked low");
                choice = false;


                if ((!choice) && (round.get(x+1).value > round.get(x).value)) {
                    System.out.println("Incorrect choice, You lose!");
                    System.out.println("You lose!");
//                resultsMsg.setText("You lose!");
                    if (boolBet1){
                        balance -= 2;
                        resultsMsg.setText("You lost $2! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet2){
                        balance -= 5;
                        resultsMsg.setText("You lost $5! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet3){
                        balance -= 10;
                        resultsMsg.setText("You lost $10! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else{
                        resultsMsg.setText("No bet was placed.");
                        System.out.println("You were incorrect!");
                    }
                    winning = false;
                    resultsMsg.setText("You lose! You won " + balance + " euro this round.");


                } else if ((!choice) && (round.get(x+1).value < round.get(x).value)) {
                    System.out.println("You win!");
                    if (boolBet1){
                        resultsMsg.setText("You won $2");
                        balance += 2;
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet2){
                        resultsMsg.setText("You won $5");
                        balance += 5;
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet3){
                        resultsMsg.setText("You won $10");
                        balance += 10;
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else{
                        resultsMsg.setText("No bet was placed");
                        System.out.println("You were correct!");
                    }
//                    amountLabel.setText(String.valueOf(balance));
//                    resultsMsg.setText("Correct choice, You won " + wagerString + " euro");
                    winning = true;
                } else if ((round.get(x+1).value == round.get(x).value)) {
                    System.out.println("You lost on a tie");
//                resultsMsg.setText("You lose!");
                    if (boolBet1){
                        balance -= 2;
                        resultsMsg.setText("You lost $2! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet2){
                        balance -= 5;
                        resultsMsg.setText("You lost $5! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else if (boolBet3){
                        balance -= 10;
                        resultsMsg.setText("You lost $10! You won " + balance + " euro this round.");
                        amountLabel.setText("$" + String.valueOf(balance));
                    }else{
                        resultsMsg.setText("No bet was placed.");
                        System.out.println("You were incorrect!");
                    }
                    winning = false;
                    resultsMsg.setText("You lost on a tie! You won " + balance + "  euro this round.");

                }
                char randomSuitLo = getSuit();
                if (winning) {

                    x++;
                    card1.setText(round.get(x).symbol + randomSuitLo);
                    board.add(card1);
                    card1a.setText(round.get(x).symbol + randomSuitLo);
                    board.add(card1a);

                    card2.setText("");
                    card2a.setText("");

                }else {
                    gameOver = true;

                    card1.setText(round.get(x).symbol + randomSuitLo);
                    board.add(card1);
                    card1a.setText(round.get(x).symbol + randomSuitLo);
                    board.add(card1a);
                    char randomSuitLo2 = getSuit();
                    card2.setText(round.get(x + 1).symbol + randomSuitLo2);
                    card2a.setText(round.get(x + 1).symbol+ randomSuitLo2);
                    board.add(card2);
                    board.add(card2a);

                    playAgain();
                }

            }
        }

        public class smallBet implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolBet1 = true;
                boolBet2 = false;
                boolBet3 = false;
                betSize = 2;
                if (balance < betSize){
                    resultsMsg.setText("Not enough in your wallet!");
                    wallet.setEditable(true);
                }else {

//                wager = wallet.getText();
//                smallBetPlaced = true;
//                if (wallet.getText() != null){
//                    wagerNum = Integer.parseInt(wager);
//                    if (choice) {
//                        wagerNum += 2;
//                        wagerString = String.valueOf(wagerNum);
//                        walletLabel.setText(wagerString);
//                        System.out.println("new balance is: " + wagerString);
//                    }
//                }
                    System.out.println("Small bet selected");
//                    System.out.println(balance);
                }
            }
        }

        public class medBet implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolBet1 = false;
                boolBet2 = true;
                boolBet3 = false;
                betSize = 5;
                if (balance < betSize){
                    resultsMsg.setText("Not enough in your wallet!");
                    wallet.setEditable(true);
                }else
                System.out.println("Medium bet selected");
            }
        }

        public class lrgBet implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolBet1 = false;
                boolBet2 = false;
                boolBet3 = true;
                betSize = 10;
                if (balance < betSize){
                    resultsMsg.setText("Not enough in your wallet!");
                    wallet.setEditable(true);
                }else
                System.out.println("Large bet selected");
            }
        }


        public class deposit implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wallet == null){
                    amountLabel.setText("You must deposit to play");
                }else {
                    balance = Integer.parseInt(wallet.getText());
                    amountLabel.setText("You have " + balance + " euro in your account.");
                    wallet.setEditable(false);

//
//                    switch(betSize){
//                        case 0: wallet.setText(String.valueOf(wagerNum + 2));
//                        case 1: wallet.setText(String.valueOf(wagerNum + 5));
//                        default: wallet.setText(String.valueOf(wagerNum + 10));
//                    }


//                    if (boolBet1){
//                        wager = wallet.getText();
//                        wagerNum = Integer.parseInt(wager);
//                        wagerString = String.valueOf(wagerNum);
//                        wagerNum += betSize;
//                        balanceLabel.setText(wagerString);
//                    } else if (boolBet2){
//
//                        wagerNum += betSize;
//                        balanceLabel.setText(wagerString);
//                    }else {
//
//                        wagerNum += betSize;
//                        balanceLabel.setText(wagerString);
//                    }

                    System.out.println("Deposit of " + wallet.getText() + " made");
                }
            }
        }

} // end GUI.java file