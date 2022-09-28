package com.example.demo2;
import java.util.LinkedList;
import java.util.List;

public class Grid {
    int width;
    int[][] board;
    int Num1, Num2;

    public Grid(int width) {
        this.width = width;
        Num1 = Num2 = 2;
        board = new int[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == width - 1))
                    board[i][j] = 1;
                else if ((i == width - 1 && j == 0) || (i == width - 1 && j == width - 1))
                    board[i][j] = 2;
                else
                    board[i][j] = 0;
            }
        }
    }

    public Grid(Grid g) {
        this.width = g.width;
        this.Num2 = g.Num2;
        this.Num1 = g.Num1;
        board = new int[width][width];
        for (int i = 0; i < width; i++) {
            System.arraycopy(g.board[i], 0, board[i], 0, width);
        }
    }

    public void playUser(int CurX, int CurY, int EndX, int EndY) {
        for (int i = CurX - 2; i <= CurX + 2; i++)
            for (int j = CurY - 2; j <= CurY + 2; j++) {
                boolean b = ((Math.abs(CurX - i)) == 2 || (Math.abs(CurY - j) == 2)) ;
                if (b && (i == EndX) && (j == EndY) && i<width && j<width && i>=0 && j>=0 && board[EndX][EndY] == 0) {
                    board[EndX][EndY] = 1;
                    board[CurX][CurY] = 0;
                    convertEnemy(EndX, EndY, 1);
                } else if (!b && (i == EndX) && (j == EndY) && i<width && j<width && i>=0 && j>=0 && board[EndX][EndY] == 0){
                    board[EndX][EndY] = 1;
                    convertEnemy(EndX, EndY, 1);
                    Num1++;
                }
            }
    }

    public boolean playComputer(int CurX, int CurY,int i, int j) {
        boolean b = ((Math.abs(CurX - i)) == 2 || (Math.abs(CurY - j) == 2));
        if (b && i<width && j<width && i>=0 && j>=0 && board[i][j] == 0) {
            board[i][j] = 2;
            board[CurX][CurY] = 0;
            convertEnemy(i,j, 2);
            return true;
        } else if (!b && i<width && j<width && i>=0 && j>=0 && board[i][j] == 0) {
            board[i][j] = 2;
            convertEnemy(i, j, 2);
            Num2++;
            return true;
        }
        return false;
    }

    public void convertEnemy(int EndX, int EndY, int playerNumber){
        for (int i = EndX - 1; i <= EndX + 1; i++)
            for (int j = EndY - 1; j <= EndY + 1; j++)
                if (i < width && i >= 0 && j < width && j >= 0)
                    if (playerNumber == 2) {
                        if (board[i][j] == 1)
                        {
                            Num2++;
                            Num1--;
                            board[i][j] = 2;
                        }
                    }
                    else {
                        if (board[i][j] == 2)
                        {
                            Num1++;
                            Num2--;
                            board[i][j] = 1;
                        }
                    }

    }

    public List<Grid> allNextMoves() {
        List<Grid> nextBoards = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 2) {
                    for (int k = i - 2; k <= i + 2; k++)
                        for (int l = j - 2; l <= j + 2; l++) {
                            Grid nextBoard = new Grid(this);
                            if (nextBoard.playComputer(i,j,k,l))
                                nextBoards.add(nextBoard);
                        }
                }
            }
        }
        return nextBoards;
    }

    public boolean canMove(int playerNumber){
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                if (board[i][j] == playerNumber)
                    for (int k = i-2; k <= i+2; k++) {
                        for (int l = j-2; l <= j+2; l++) {
                            if ( k>=0 && l>=0 && k<width && l<width && board[k][l] == 0)
                            {
                                return true;
                            }
                        }
                    }
        return false;
    }

    public int isLoseOnLastMove(int playerNumber) {
        int player1 = 0, player2 = 0, empty = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
            {
                if (board[i][j] == 1)
                    player1++;
                else if (board[i][j] == 2)
                    player2++;
                else
                    empty++;
            }
        if (empty == 0){
            if (player1 <= player2)
                return 2;
            else
                return 1;
        }
        else {
            if (!canMove(playerNumber)) {
                if (player1 <= player2)
                    return 2;
                else
                    return 1;
            } else
                return 0;
        }

    }

    public boolean isLoseOnLastMoveComputer() {
        int player1 = 0, player2 = 0, empty = 0;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
            {
                if (board[i][j] == 1)
                    player1++;
                else if (board[i][j] == 2)
                    player2++;
                else
                    empty++;
            }
        if (empty == 0){
            return player1 > player2;
        }
        else {
            if (!canMove(2)) {
                return player1 > player2;
            }
        }
        return false;

    }

    public int eval(Grid grid) {
        return (grid.Num2 - grid.Num1) ;
    }

    public void print(){
//        System.out.println("user: " + Num1 + " , computer: " + Num2 + "\n");
        System.out.print("     ");
        for (int k = 0; k <width ; k++)
            System.out.print(k + "  ");
        for (int i = 0; i < width; i++) {
            System.out.println();
            System.out.print(i + "   ");
            for (int j = 0; j < width; j++)
                switch (board[i][j]){
                    case 1:
                        System.out.print(Color.ANSI_RED_BACKGROUND + "   " + Color.ANSI_RESET);
                        break;
                    case 2:
                        System.out.print(Color.ANSI_YELLOW_BACKGROUND + "   " + Color.ANSI_RESET);
                        break;
                    case 0:
                        System.out.print(Color.ANSI_BLACK_BACKGROUND + "   " + Color.ANSI_RESET);
                        break;
                    default:
                        break;
                }

        }
    }
}
