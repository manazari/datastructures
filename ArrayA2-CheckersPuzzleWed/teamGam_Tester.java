import java.io.IOException;
import java.lang.Math;

public class teamGam_Tester {
    public static void main(String []terminalArguments) throws InterruptedException { 
        BoardState board = new BoardState(new int[]{2, 2, 4, 3, 3, 5, 3, 7, 3, 7, 5 });
        board.print();
        Thread.sleep(1000);
        System.out.println("\n### LONGEST POSSIBLE JUMP IS " + board.mostJumps());
    }
}

class BoardState {
    public int []state;
    public boolean king;

    public BoardState(int []pieces, boolean isKing) {
        state = pieces;
        king = isKing;
    }

    public BoardState(int []pieces) {
        state = pieces;
        king = pieces[0] == 8;
    }

    public int[][] enemyPieces() {
        int[][] pieces = new int[state[2]][2];
        for (int i = 3, j = 0; i < 3 + state[2]*2; i += 2, j++) {
            pieces[j][0] = state[i];
            pieces[j][1] = state[i+1];
        }
        return pieces;
    }

    public BoardState remOppPiece(int index) {
        // System.out.print("STATE: ");
        // for (int num : state) System.out.print(num + " ");
        // System.out.println("");
        // System.out.println("Want to remove index: " + index);
        index = 3 + index*2;
        // System.out.println("Place in state: " + index);
        int[] newState = new int[state.length - 2];

        for (int i = 0, j = 0; i < state.length; i++) {
            // System.out.println("i, j " + i + ", " + j);
            if (i == index || i == index+1) continue;
            newState[j] = state[i];
            j++;
        }
        newState[2]--;
        return new BoardState(newState, king);
    }

    public boolean canJump(int[] dir) {
        if (dir[1] == +1 && state[1] >= 7) return false;
        if (dir[0] == +1 && state[0] >= 7) return false;
        if (dir[1] == -1 && state[1] <= 2) return false;
        if (dir[0] == -1 && (state[0] <= 2 || !king)) return false;

        int[] landingCoord = {state[0] + dir[0]*2, state[1] + dir[1]*2};
        int[] enemyCoord   = {state[0] + dir[0]  , state[1] + dir[1]  };
        boolean landingClear = true;
        boolean enemyThere   = false;
        for (int[] enemyPiece : enemyPieces()) {
            landingClear = landingClear && !(enemyPiece[0] == landingCoord[0] && enemyPiece[1] == landingCoord[1]);
            enemyThere = enemyThere || (enemyPiece[0] == enemyCoord[0] && enemyPiece[1] == enemyCoord[1]);
        }
        return enemyThere && landingClear;
    }

    public BoardState tryJump(int[] dir) {
        if (!canJump(dir)) return new BoardState(state, king);
        int enemyToJump = 0;
        for (int i = 0; i < enemyPieces().length; i++) {
            int[] enemyPiece = enemyPieces()[i];
            if (state[0] + dir[0] == enemyPiece[0] && state[1] + dir[1] == enemyPiece[1]) enemyToJump = i;
        }
        BoardState newBoardState = remOppPiece(enemyToJump);
        newBoardState.state[0] += dir[0]*2;
        newBoardState.state[1] += dir[1]*2;
        newBoardState.king = king || newBoardState.state[0] == 8;
        return newBoardState;
    }

    public int mostJumps() {
        print();
        BoardState moveTopLeftBoard = tryJump(new int[]{+1, -1});
        int jumpsTopLeft = 0;
        if (!BoardState.equalStates(moveTopLeftBoard.state, state)) {
            print();
            jumpsTopLeft = 1 + moveTopLeftBoard.mostJumps();
        }
        // print();
        BoardState moveTopRightBoard = tryJump(new int[]{+1, +1});
        int jumpsTopRight = 0;
        if (!BoardState.equalStates(moveTopRightBoard.state, state)) {
            print();
            jumpsTopRight = 1 + moveTopRightBoard.mostJumps();
        }
        // print();
        BoardState moveLowLeftBoard = tryJump(new int[]{-1, -1});
        int jumpsLowLeft = 0; 
        if (!BoardState.equalStates(moveLowLeftBoard.state, state)) {
            print();
            jumpsLowLeft = 1 + moveLowLeftBoard.mostJumps();
        }
        // print();
        BoardState moveLowRightBoard = tryJump(new int[]{-1, +1});
        int jumpsLowRight = 0;
        if (!BoardState.equalStates(moveLowRightBoard.state, state)) {
            print();
            jumpsLowRight = 1 + moveLowRightBoard.mostJumps();
        }
        print();
        return Math.max(Math.max(Math.max(jumpsTopLeft, jumpsTopRight), jumpsLowLeft), jumpsLowRight);

    }

    public void print() {
        try { clearConsole(); } catch (IOException | InterruptedException ex) {}
        for (int row = 8; row >= -1; row--) {
            String rowString = "";
            for (int col = -1; col <= 8; col++) {
                if (row <= 8 && row >= 1) {
                    if (col == -1) rowString += ""+row;
                    if (col == 0) rowString += "|";
                    if (state[0] == row && state[1] == col) rowString += king ? "K" : "*";
                    for (int[] enemyPiece : enemyPieces())
                        if (enemyPiece[0] == row && enemyPiece[1] == col) rowString += "o";
                }
                if (row == 0) {
                    if (col == 0) rowString += "+";
                    else rowString += "-";
                }
                if (row == -1) {
                    if (col == -1) rowString += " ";
                    else if (col == 0) rowString += "|";
                    else rowString += ""+col;
                }
                rowString += "  ";
            }
            System.out.println(rowString);
        }
    }

    public static void clearConsole() throws IOException, InterruptedException {
        Thread.sleep(250);
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static boolean equalStates(int[] a, int[] b) {
        if (a.length != b.length) return false;
        boolean equal = true;
        for (int i = 0; i < a.length; i++)
            equal = equal && a[i] == b[i];
        return equal;
    }
}