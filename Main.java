package com.company;
import java.util.Scanner;

public class Main
{
    static char PLACEHOLDER = '.';
    static Scanner keyboard = new Scanner(System.in);

    //create lines for board
    public static void drawBoard(char[][] board)
    {
        System.out.println();
        for (int row = 0; row < 3; row++)
        {
            System.out.println(" " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);
        }
        System.out.println();
    }

    //put placeholders in board
    public static char[][] createEmptyBoard()
    {
        char[][] gameBoard = new char[3][3];
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                gameBoard[row][col] = (PLACEHOLDER);
            }
        }
        return gameBoard;
    }

    //Ask for position/invalid input response/position full response
    public static void getPositionAndPlaceToken(char[][] board, boolean isXTurn)
    {
        boolean invalidInput;
        boolean full = true;
        int position;

        do
        {
            if (isXTurn)
            {
                System.out.print("X where? ");
            }
            else
            {
                System.out.print("O where? ");
            }
            position = keyboard.nextInt();
            invalidInput = (position < 1 || position > 9);
            if (invalidInput)
            {
                System.out.println("Sorry, position " + position + " is not valid.  1-9 only. Try again. ");
            }
            else
            {
                full = (getTokenAtPosition(position, board) != PLACEHOLDER);
                if (full)
                {
                    System.out.println("Sorry, position " + position + " is already in use.  Pick another. ");
                }
            }
        } while (full || invalidInput);

        placeToken(position, board, isXTurn);
    }


    public static char getTokenAtPosition(int position, char[][] board)
    {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        return board[row][col];
    }

    public static void placeToken(int position, char[][] board, boolean isXTurn)
    {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        if (isXTurn)
        {
            board[row][col] = 'x';
        }
        else
        {
            board[row][col] = 'o';
        }
    }

    //if any spot has placeholder, boardFull is false
    public static boolean boardFull(char[][] board)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col] == PLACEHOLDER)
                {
                    return false;
                }
            }
        }
        return true;
    }

    //if columns 0, 1, 2 are the same in any row, horizontalWin is true
    public static boolean horizontalWin(char[][] board)
    {
        for (int row = 0; row < 3; row++)
        {
            if (board[row][0] != PLACEHOLDER && board[row][0] == board[row][1] && board[row][1] == board[row][2])
            {
                return true;
            }
        }
        return false;
    }

    //if rows 0, 1, 2 are the same in any column, verticalWin is true
    public static boolean verticalWin(char[][] board)
    {
        for (int col = 0; col < 3; col++)
        {
            if (board[0][col] != PLACEHOLDER && board[0][col] == board[1][col]
                    && board[1][col] == board[2][col])
            {
                return true;
            }
        }
        return false;
    }

    //if diagonal row is not placeholders, diagonalWin is true
    public static boolean diagonalWin(char[][] board)
    {
        if (board[0][0] != PLACEHOLDER && board[0][0] == board[1][1]
                && board[1][1] == board[2][2])
        {
            return true;
        }
        if (board[0][2] != PLACEHOLDER && board[0][2] == board[1][1]
                && board[1][1] == board[2][0])
        {
            return true;
        }
        return false;
    }

    //game has been won
    public static boolean gameWon(char[][] board)
    {
        return horizontalWin(board) || verticalWin(board) || diagonalWin(board);
    }

    //ask to play again
    public static boolean wantsToPlayAgain()
    {
        System.out.print("Would you like to play again? y for yes, n for no ");
        keyboard.nextLine();
        String answer = keyboard.nextLine();

        if (answer.equals("n"))
        {
            return false;
        }
        else if (answer.equals("y"))
        {
            return true;
        }
        else
        {
            System.out.print("Not a valid option. ");//After invalid option, have to enter response twice before it's accepted?
        }

        return wantsToPlayAgain();
    }

    //welcome message, runs game, ends
    public static void main(String[] args)
    {
        int xWins = 0;
        int oWins = 0;
        int draws = 0;
        boolean isXTurn = true;

        System.out.println("Welcome to Tic Tac Toe!\n");
        System.out.println("Instructions: When it's your turn, enter the corresponding number");
        System.out.println("for the position you want to place your marker in, as shown below:\n");
        System.out.println("1 2 3");
        System.out.println("4 5 6");
        System.out.println("7 8 9\n");
        System.out.println("You'll need a friend to play with. Ready? Let's start!");

        do
        {
            char[][] gameBoard = createEmptyBoard();
            boolean gameStillGoing = true;
            drawBoard(gameBoard);

            do
            {
                getPositionAndPlaceToken(gameBoard, isXTurn);
                drawBoard(gameBoard);
                if (gameWon(gameBoard))
                {
                    gameStillGoing = false;
                    if (isXTurn)
                    {
                        // X went last so they won
                        xWins++;
                        System.out.println("X wins!");
                    }
                    else
                    {
                        // O went last so they won
                        oWins++;
                        System.out.println("O wins!");
                    }
                }
                else if (boardFull(gameBoard)) //If board is full and no one won it's a draw
                {
                    gameStillGoing = false;
                    draws++;
                    System.out.println("The game is a draw.  Nobody wins.");
                }
                else
                {
                    //continue game
                }
                isXTurn = ! isXTurn; //change starting player
            } while (gameStillGoing);

            System.out.println("Score: X = " + xWins + ", O = " + oWins + ", draws = " + draws);

        } while (wantsToPlayAgain());
        System.out.println("Goodbye!");
    }

}

