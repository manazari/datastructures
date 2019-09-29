import java.io.*;
import java.util.Scanner;

/**
 * CH12  P12.5 -- Tick Tack Toe
 * 
 * The TickTackToeTester_Nazari program implements
 * an application that loops through tick-tack-toe
 * games against a CPU. At the end of every game
 * the user will be promtped whether or not they
 * want to continue, and everytime they continue
 * the CPU will be smarter 
 *
 * @author  Matthew Nazari
 * @since   2019-09-29
 */
public class TickTackToeTester_Nazari {
    /**
     * This method runs a for loop which is
     * broken if an "N" is scanned after a game
     * signifying the player does not want to
     * replay
     * @param termArgs not used
     */
    public static void main(String[] termArgs) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            new TickTackToeGame().run();
            try {Thread.sleep(1000);} catch(InterruptedException ex) {}
            System.out.println("\nReplay with a smarter CPU? (Y/N) ");
            String restart;
            do restart = sc.next(); while (!(restart.equalsIgnoreCase("Y") || restart.equalsIgnoreCase("N")));
            if (restart.equalsIgnoreCase("N")) break;
        } 
    }
}

/**
 * TickTackToeGame class wraps a 2D int
 * array representing the tick tack toe
 * board. -1 represents "O" and the CPU
 * while 1 represents "X" and the player
 * while 0 represents an unfilled cell
 */
class TickTackToeGame {
    private int[][] board;
    private int[][][] losingBoards;
    private int turn = 1;
    private Scanner sc;

    /**
     * A tick tack toe game starts off
     * with an "empty" 3x3 array where empty
     * means the cells are filled with 0. The
     * scanner used to scan user input for
     * the player's move is also initialized
     */
    public TickTackToeGame() {
        board = new int[3][3];
        losingBoards = new int[0][0][0];
        sc = new Scanner(System.in);
    }

    /**
     * This method will loop playerMove()
     * and computerMove() over and over while
     * there is no winner or draw yet. After
     * a winner is decided or a cats game is
     * drawn, a game ending message will
     * be printed.
     */
    public void run() {
        while (checkWinner() == -2) {
            playerMove();
            if (checkWinner() != -2) break;
            computerMove();
        }
        turn = 0;
        printBoard();
        System.out.println(checkWinner() == 1 ? "YOU WIN  :D" : checkWinner() == -1 ? "CPU WINS  D:" : "CATs GAME  :/");
    }

    /**
     * This method clears the console then
     * prints out the tick tock toe board.
     * This is done by printing a title
     * dependant on whose turn it is. Then
     * the board is drawn row by row with
     * the row and column numbers marked so
     * the player can know what moves
     * they're making
     */
    private void printBoard() {
        try { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); }
        catch (IOException | InterruptedException ex) {}

        System.out.println(turn == 1 ? "Your turn" : turn == -1 ? "CPUs turn" : "=========");
        for (int row = 0; row < board.length; row++) {
            String rowString = (row+1) + " ";
            for (int col = 0; col < board[row].length; col++)
                rowString += (board[row][col] == 1 ? "X" : board[row][col] == -1 ? "O" : " ")+"|";
            System.out.println(rowString.substring(0, rowString.length()-1));
        }
        System.out.println("  1 2 3\n=========");
    }

    /**
     * This method takes user input then
     * updates the board according to
     * the column and row numbers inputted.
     * The board is first printed and then
     * a loop will garauntee the player
     * enterred coords to a non empty cell.
     * Seperate loops garauntee the row and
     * column inputted are valid row and
     * column numbers.
     */
    private void playerMove() {
        turn = 1;
        printBoard();
        
        int row, column;
        do {
            System.out.println("Enter valid cell");
            System.out.print("row: ");
            do {
                while (!sc.hasNextInt()) sc.next();
                row = sc.nextInt();
            } while (!(row >= 1 && row <= 3));
    
            System.out.print("column: ");
            do {
                while (!sc.hasNextInt()) sc.next();
                column = sc.nextInt();
            } while (!(column >= 1 && column <= 3));
        } while(board[row-1][column-1] != 0);

        System.out.println(row + " " + column);

        board[row-1][column-1] = 1;
    }

    /**
     * This method handles the CPU's
     * decision making and updates the board
     * with what the CPU chooses. The board
     * is first printed and then a loop
     * will attempt to find a non empty
     * cell that won't make a losing board.
     * The more losing boards are known,
     * the "smarter" the CPU will be. In case
     * of no smart moves, a random emtpy cell
     * will be selected after 100 attempts
     */
    private void computerMove() {
        turn = -1;
        printBoard();

        int row, column, attempts = 0;
        do {
            do {
                row = (int)Math.floor(Math.random()*3);
                column = (int)Math.floor(Math.random()*3);
            } while (board[row][column] != 0);
            attempts++;
        } while (!willMakeLosingBoard(row, column, -1) && attempts < 100);
        
        // Loading animation to make it look like CPU is trying
        System.out.print("CPU is thinking");
        for (int i = 0; i < 3; i++) {
            System.out.print(". ");
            try {Thread.sleep(500);} catch(InterruptedException ex) {}
        }

        board[row][column] = -1;
    }

    /**
     * This method will return a code
     * which designates if the game is won
     * and who was the winner.
     * @return int  code for game state
     *               1 -> player has won
     *              -1 -> CPU has won
     *               0 -> cats game (draw)
     *              -2 -> still empty spaces
     */
    private int checkWinner() {
        // Check horizontal streaks
        for (int row = 0; row < 3; row++)
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != 0) return board[row][0];
        // Check vertical streaks
        for (int col = 0; col < 3; col++)
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != 0) return board[0][col];
        // Checks diagonal streaks
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != 0) return board[1][1];
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != 0) return board[1][1];

        // If any 0, then board isn't full
        boolean full = true;
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) full = full && board[i][j] != 0;
        return full ? 0 : -2;
    }

    /**
     * This method creates a 
     * hypothetical board updated by
     * updating the cell at (row, column)
     * with player. This hypothetical
     * board is then checked with every
     * losing board and if any are a match
     * then true is returned
     * @param row
     * @param column
     * @param player
     * @return
     */
    private boolean willMakeLosingBoard(int row, int column, int player) {
        // create the hypothetical board
        int[][] newBoard = new int[3][3];
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) newBoard[i][j] = board[i][j];
        newBoard[row][column] = player;

        // see if any of the losing boards match the hypothetical board
        for (int[][] losingBoard : losingBoards) {
            boolean match = true;
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) match = match && newBoard[i][j] == losingBoard[i][j];
            if (match) return true;
        }
        
        return false;
    }
}