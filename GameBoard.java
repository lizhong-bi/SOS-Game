import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * 
 *
 */
public class GameBoard extends JPanel {

 public boolean fullBoard = false;
 char[][] pieces;
 Image S = null;
 Image O = null;

 int pointUs = 0;
 int pointThem = 0;

/**
 * 
 * @param board the 2-d array holding all the information on the board
 * @param x the x coordinate of the piece
 * @param y the y coordinate of the piece
 * @param letter the letter to be placed
 * @param isUs Whether or not we made the move
 * @return the number of points received
 */
 public int check(char[][] board, int x, int y, char letter, boolean isUs) {
  // System.out.println(x + 3);
  // System.out.println(y + 3);
  x += 3;
  y += 3;
  int point = 0;
  
  //Doing the checks if an O is placed
  if (letter == 'O') {
   if (board[y][x + 1] == 'S' && board[y][x - 1] == 'S')
    point++;
   if (board[y + 1][x] == 'S' && board[y - 1][x] == 'S')
    point++;
   if (board[y - 1][x - 1] == 'S' && board[y + 1][x + 1] == 'S')
    point++;
   if (board[y + 1][x - 1] == 'S' && board[y - 1][x + 1] == 'S')
    point++;
  }
  //Doing the checks if an S is placed
  else if (letter == 'S') {
   if (board[y][x + 1] == 'O' && board[y][x + 2] == 'S')
    point++;
   if (board[y][x - 1] == 'O' && board[y][x - 2] == 'S')
    point++;
   if (board[y + 1][x] == 'O' && board[y + 2][x] == 'S')
    point++;
   if (board[y - 1][x] == 'O' && board[y - 2][x] == 'S')
    point++;
   if (board[y - 1][x - 1] == 'O' && board[y - 2][x - 2] == 'S')
    point++;
   if (board[y - 1][x + 1] == 'O' && board[y - 2][x + 2] == 'S')
    point++;
   if (board[y + 1][x + 1] == 'O' && board[y + 2][x + 2] == 'S')
    point++;
   if (board[y + 1][x - 1] == 'O' && board[y + 2][x - 2] == 'S')
    point++;
  }
  //Adding points to us
  if (isUs == true){
   pointUs += point;
   
  }
  //Adding points to them
  else{
   
   pointThem += point;
 
  }
  return point;
 }

 public void paint(Graphics g) {
  //Drawing the lines for the board
  for (int count = 0; count < 11; count++) {
   for (int index = 0; index < 11; index++) {
    g.setColor(Color.black);
    g.drawLine(((index + 1) * 50) - 50, count * 50, 500, count * 50);
    g.drawLine(index * 50, ((count + 1) * 50) - 50, index * 50, 500);
   }
  }
  
  //Drawing what is supposed to appear on the board
  for (int y = 3; y < 13; y++) {
   for (int x = 3; x < 13; x++) {
    if (pieces[y][x] == 'O')
     g.drawImage(O, (x - 3) * 50, (y - 3) * 50, 50, 50, this);
    else if (pieces[y][x] == 'S')
     g.drawImage(S, (x - 3) * 50, (y - 3) * 50, 50, 50, this);
   }
  }

 }

 public static void main(String[] args) {
  GameBoard gb = new GameBoard();
  gb.go(gb);
 }

 public void go(GameBoard gb) {
  try {
   S = ImageIO.read(new File("ss.png"));
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 
  try {
   O = ImageIO.read(new File("OO.png"));
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }

  // Load up Array with dashes '-'
  pieces = new char[16][16];

  for (int y = 3; y < pieces.length - 3; y++) {
   for (int x = 3; x < pieces[y].length - 3; x++) {
    pieces[x][y] = '-';
   }
  }

  SOSNetwork nw = new SOSNetwork();
//Setting up the GUI
  JFrame game = new JFrame("SOS");
  game.setSize(520, 540);

  game.getContentPane().add(gb);
  game.setBackground(Color.WHITE);
  game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  game.setVisible(true);
  game.setResizable(false);
  JPanel Black = new JPanel(new GridLayout(10, 10));
  
  Color color= new Color(148,0,221);
  
  Black.setBackground(color);
  game.add(Black);

  AI ai = new AI();

  nw.go();

  // Wait to connect
  while (!nw.isConnected()) {



  }
  System.out.println("Connected");
  while (!isBoardFull(pieces)) {
   

   
   // SEND INFORMATION
//If its our turn
   if (nw.getWhoseTurn() == 0) {
    System.out.println("AI TURN");
    do {

     ai.myTurn(pieces);
     nw.setTurn(0);
    if(check(pieces, ai.getLastX(), ai.getLastY(), ai.getLastChar(), true)==0){
    	nw.setTurn(1);
    }
   
   
    System.out.println("US:" +pointUs);
    System.out.println("Them: " +pointThem);
     game.repaint();
     nw.send(ai.getLastX(), ai.getLastY(), ai.getLastChar(), nw.getWhoseTurn());
     if (isBoardFull(pieces))
    	    break;
     
     
     if(nw.recieveTurn()==1)
    nw.setWhoseTurn(1);
    } while (nw.getWhoseTurn()==0);
   }
   

   // Repaint again
   game.repaint();


   if (isBoardFull(pieces))
    break;

 
   // GET INFORMATION
   // If its their turn
   if (nw.getWhoseTurn() == 1) {
    System.out.println("AI2 TURN");
    do {
    nw.setTurn(0);
     nw.recieve();

     playTurn(pieces, nw.getX(), nw.getY(), nw.getC());
     
    
     ai.displayGrid(pieces);
    
     game.repaint();
     if(check(pieces, nw.getX(), nw.getY(), nw.getC(), false)==0)
     {
     
      System.out.println("NextTurn");
      //If they get no points switch turns
      nw.setWhoseTurn(0);
      nw.setTurn(1);
      nw.sendTurn(1);
     }else
    	 nw.sendTurn(0);
     System.out.println("US:" +pointUs);
     System.out.println("Them: " +pointThem);
     if (isBoardFull(pieces))
    	    break;
    } while (nw.getWhoseTurn()==1);
   }

   // Repaint the board
   game.repaint();

   // Delay
   try {
    Thread.sleep(500);
   } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }

   if (isBoardFull(pieces))
    break;
  }

  // Display points

  System.out.println("US: " + pointUs);
  System.out.println("Them: " + pointThem);
  fullBoard = true;
  nw.closeAll();
 }

 public void playTurn(char[][] grid, int x, int y, char letter) {
  if (grid[y+3][x+3] == '-') {
   grid[y+3][x+3] = letter;
  } else {
   System.out.println(letter + " " + (x - 3) + " " + (y - 3));
   System.out.println("Invalid location");
  }
  // displayGrid(grid);
 }
 
 
 
 public static boolean isBoardFull(char[][] grid) {
  // Make the board
  for (int y = 3; y < grid.length - 3; y++) {
   for (int x = 3; x < grid[y].length - 3; x++) {
    if (grid[y][x] == '-') {
     return false;
    }
   }
  }
  System.out.println("Board is full");
  return true;
 }

 public boolean isFullBoard() {
  return fullBoard;
 }

 public void setFullBoard(boolean fullBoard) {
  this.fullBoard = fullBoard;
 }

 public char[][] getPieces() {
  return pieces;
 }

}
