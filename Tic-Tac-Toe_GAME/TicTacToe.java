//Imports that are essential for our game's functioning, such as swing for the GUI, and awt for layout managing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {

    //here we define the sizes of our GUI which we will play tic-tac-toe on
    int boardHeight = 700;
    int boardWidth = 750;

    //here we are creating the GUI, along with a label, panel, and boardpanel, we will explain each later
    JFrame frame = new JFrame("Tic-Tac-Toe Game");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    //here we give each player a mark so X and O, and at every turn the current player will be shown in the text Label (will see later)
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    //here we are setting a board with 9 different buttons, and also a replay button
    JButton[][] board = new JButton[3][3];
    JButton replayButton = new JButton("Replay");


    //the gameOver variable is key to stopping the game once someone has won, and the moveCounter is used to declare a draw when there is one
    boolean gameOver = false;
    int moveCounter = 0;

    //This is our program's constructor, the whole functioning of the game is HERE
    TicTacToe(){
        //we start with configurating the frame, whose size is of the ones we defined earlier, its unresizable (static size we can say)
        //and we set the close operation so that when we close the GUI we exit the program
        //we also divide the layout of the JFrame
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //here we set all the properties of the replay button, showing that it will not be visible during a game but only at the end
        replayButton.setBackground(Color.white);
        replayButton.setForeground(Color.darkGray);
        replayButton.setFont(new Font("Arial", Font.BOLD,20));
        replayButton.setFocusable(false);
        replayButton.setVisible(false);

        //this is an action listener, which will execute when the replay button is clicked.
        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }

        });

        //here we set a specific area with JPanel for the replay button (its at the bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.add(replayButton);

        //here we are adding the properties to the text Label object which will basically say tic-tac-toe at first,
        //then show when its each player's turn, and will tell us the game's result at the end.
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        //we are adding the textlabel to the textpanel, also the replay button, and then the text panel to our GUI frame, specifying each one's location
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //here we give our board panel it's properties so 9 tiles which we can insert an X or O, and then we complete the frame by ading it in
        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        //So right there is where we start with the "backend" kindof for our game, in which we have a loop that will go over every single tile
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                //we see here that the boardPanel will at the end contain 9 buttons which can be interacted with an processed with an action listener
                JButton tile = new JButton();
                board[i][j] = tile;
                boardPanel.add(tile);

                //here we set the properties for each til text, so how the O or the X will be displayed
                tile.setForeground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD,120));
                tile.setFocusable(false);

                //here we have the main action listener of our game, which basically makes the button that is clicked show the player's letter,
                //so if player X clicks it, it is marked X, and if a button is already clicked, then it won't be marked again,
                //it also regulates the "player's turn" printings which will happen at every turn, changing the textLabel accordingly.
                //We also have a check winner method, which basically checks if someone has won or if theres a tie everytime a tile is clicked (will explain later!)
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver){ return;}
                        JButton tile = (JButton) e.getSource();

                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            moveCounter++;
                            checkWinner();
                            if (!gameOver) {

                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;

                                textLabel.setText(currentPlayer + "'s turn!");
                            }
                        }
                    }
                });
            }
        }


    }


    //this method checks the winner of each game and tells the players when there is a tie
    public void checkWinner(){


        for (int i = 0; i < 3; i++) {

            //horizontal check for 3 equal marks, makes sure it doesn't consider equal empty tiles, and also sets gameover to true, signaling the end of the game,
            //it also displays the winner and changes the color of the three tiles marks which made the player win, making the replay button visible and exiting the method.
            if ( (!board[i][0].getText().equals("")) && (!board[i][1].getText().equals("")) && (!board[i][2].getText().equals("")) ){

                if ((board[i][0].getText().equals(board[i][1].getText())) && (board[i][1].getText().equals(board[i][2].getText()))){
                    gameOver = true;
                    textLabel.setText("Player " + board[i][1].getText() + " wins! ");
                    textLabel.setForeground(Color.GREEN);
                    for (int j = 0; j < 3; j++) {
                        board[i][j].setForeground(Color.GREEN);
                    }
                    replayButton.setVisible(true);
                    return;
                }
            }

            //same as before, but its for vertical checks
            if ( (!board[0][i].getText().equals("")) && (!board[1][i].getText().equals("")) && (!board[2][i].getText().equals("")) ) {

                if ((board[0][i].getText().equals(board[1][i].getText())) && (board[0][i].getText().equals(board[2][i].getText()))) {
                    gameOver = true;
                    textLabel.setText("Player " + board[1][i].getText() + " wins! ");
                    textLabel.setForeground(Color.GREEN);
                    for (int j = 0; j < 3; j++) {
                        board[j][i].setForeground(Color.GREEN);
                    }
                    replayButton.setVisible(true);
                    return;
                }
            }

        }

        //diagonal 1 check
        if ( (!board[0][0].getText().equals("")) && (!board[1][1].getText().equals("")) && (!board[2][2].getText().equals("")) ) {

            if ((board[0][0].getText().equals(board[1][1].getText())) && (board[1][1].getText().equals(board[2][2].getText()))) {
                gameOver = true;
                textLabel.setText("Player " + board[1][1].getText() + " wins! ");
                textLabel.setForeground(Color.GREEN);
                board[0][0].setForeground(Color.GREEN);
                board[1][1].setForeground(Color.GREEN);
                board[2][2].setForeground(Color.GREEN);
                replayButton.setVisible(true);
                return;
            }
        }

        //diagonal 2 check
        if ( (!board[2][0].getText().equals("")) && (!board[1][1].getText().equals("")) && (!board[0][2].getText().equals("")) ) {


            if ((board[2][0].getText().equals(board[1][1].getText()) && (board[1][1].getText().equals(board[0][2].getText())))) {
                gameOver = true;
                textLabel.setText("Player " + board[1][1].getText() + " wins! ");
                textLabel.setForeground(Color.GREEN);
                board[2][0].setForeground(Color.GREEN);
                board[1][1].setForeground(Color.GREEN);
                board[0][2].setForeground(Color.GREEN);
                replayButton.setVisible(true);
                return;
            }
        }

        //this will make sure that in the case of no winner so no 3 marks, the textLabel will display that its a draw and that theres no winner,
        //will also make the replay button visible
        if (moveCounter == 9){
            gameOver = true;
            textLabel.setText("It's a tie!");
            textLabel.setForeground(Color.GREEN);
            replayButton.setVisible(true);
            return;
        }

    }

    //this method will reset the game once the replay button is clicked, with the current player set to X again, all the tiles becoming empty again,
    //the gameover boolean becoming false signaling a new game started, and we see the replay button disappearing with the textLabel signaling whose turn it is again.
    public void resetGame(){

        gameOver = false;
        moveCounter = 0;
        currentPlayer = playerX;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                board[i][j].setText("");
                board[i][j].setForeground(Color.darkGray);
            }
        }

        textLabel.setText(currentPlayer + "'s turn!");
        textLabel.setForeground(Color.white);
        replayButton.setVisible(false);

    }





}


