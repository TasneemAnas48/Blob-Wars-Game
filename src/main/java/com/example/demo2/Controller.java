package com.example.demo2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    int width;
    Grid grid;
    Scanner s = new Scanner(System.in);
    int curX, curY, endX, endY;
    int count = 0;
    int level = 0;
    List<Grid> print = new ArrayList<>();

    public void getInputFromPlayer(){
        while (true) {
            System.out.println("\n\n>>>>>>>>> Player 1: <<<<<<<<<");
            System.out.println("\nEnter current posX: ");
            curX = s.nextInt();
            System.out.println("Enter current posY: ");
            curY = s.nextInt();
            System.out.println("Enter end posX: ");
            endX = s.nextInt();
            System.out.println("Enter end posY: ");
            endY = s.nextInt();

            if (curX >= grid.width || curY >= grid.width || endX >= grid.width || endY >= grid.width || endX > curX + 2 || endY > curY + 2 || endX < curX - 2 || endY < curY - 2 || grid.board[curX][curY] != 1 ||  grid.board[endX][endY] == 2) {
                System.out.println(">> Please Try again <<");
                grid.print();
            }
            else {
                grid.playUser(curX, curY, endX, endY);
                grid.print();
                print.add(grid);

                break;
            }
        }
    }

    public void getInputFromComputer(){
        System.out.println("\n\n>>>>>>>>> Computer: <<<<<<<<<");
        max(grid , 0, Integer.MIN_VALUE, Integer.MAX_VALUE, level);
        System.out.println("Computer is play: ");
        grid.print();
        Grid g=new Grid(grid);
        print.add(g);
        System.out.println("\nCount: " + count);
    }

    public int max(Grid b , int depth, int alpha, int beta, int level){
        Grid best = new Grid(width);
        int max = Integer.MIN_VALUE;
        if (b.isLoseOnLastMoveComputer() || depth == level) {
            count++;
            return grid.eval(b);
        }
        List<Grid> list =  b.allNextMoves();
        for (Grid value : list) {
            int max1 = min(value, depth + 1, alpha, beta, level);
            if (max < max1) {
                max = max1;
                best = value;
            }
            alpha = Math.max(alpha, max);
            if (alpha >= beta)
                break;
        }
        if(depth == 0 ) {
            grid = best;
        }
        return max;
    }

    public int min(Grid b , int depth, int alpha, int beta, int level){
        int min = Integer.MAX_VALUE;
        Grid best =new Grid(width);
        if (b.isLoseOnLastMoveComputer() || depth == level) {
            count++;
            return grid.eval(b);
        }
        List<Grid> list =  b.allNextMoves();
        for (Grid value : list) {
            int min1 = max(value, depth + 1, alpha, beta, level);
            if (min1 < min) {
                min = min1;
                best = value;
            }
            beta = Math.min(beta, min);
            if (alpha >= beta)
                break;
        }
        if(depth == 0 ) {
            grid = best;
        }
        return  min;
    }

    public void startGameOnePlayer(){
        System.out.println("Enter Width: ");
        width = s.nextInt();
        System.out.println("Enter Level (1,2,3,4,5) : ");
        level = s.nextInt();
        grid = new Grid(width);
        grid.print();
        while(true) {
            //turn player(USER)
            int res = grid.isLoseOnLastMove(1);
            if (res == 1) {
                System.out.println("\n\nPlayer 1 win\n");
                System.out.println("\n\n\n");
                for (int i=0;i<print.size();i++)
                {
                    print.get(i).print();
                    System.out.println();
                }
                break;
            } else if (res == 2) {
                System.out.println("\n\nComputer win\n");
                System.out.println("\n\n\n");
                for (int i=0;i<print.size();i++)
                {
                    print.get(i).print();
                    System.out.println();
                }
                break;
            }
            getInputFromPlayer();


            //turn Computer
            int res2 = grid.isLoseOnLastMove(2);
            if (res2 == 1) {
                System.out.println("\n\nPlayer 1 win\n");
                System.out.println("\n\n\n");
                for (int i=0;i<print.size();i++)
                {
                    print.get(i).print();
                    System.out.println();
                }
                break;
            } else if (res2 == 2) {
                System.out.println("\n\nComputer win\n");
                System.out.println("\n\n\n");
                for (int i=0;i<print.size();i++)
                {
                    print.get(i).print();
                    System.out.println();
                }
                break;
            }
            getInputFromComputer();

        }
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.startGameOnePlayer();
    }
}
