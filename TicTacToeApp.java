package gr.aueb.cf.Projects;

import java.nio.file.Path;
import java.util.Scanner;

public class TicTacToeApp {
    final static Scanner in = new Scanner(System.in);
    static int[][] arr = new int[3][3];
    final static int PLAYER_X = 1;
    final static int PLAYER_O = 2;

    public static void main(String[] args) {
        int player = PLAYER_X;
        int choice = 0;
        do {
            showGameTable();
            System.out.println("Player " + player + " please make your move (Choose from 1 to 9): ");
            choice = getChoice();
            if (choice <= 9 && choice >=1) {
                if (!choiceInitializer(choice, player)) {
                    System.out.println("The position is taken choose another spot");
                    continue;
                }
            } else {
                System.out.println("Wrong Choice. Please try again.");
                continue;
            }
            if (checkWin()) {
                System.out.println("Player " + player + " wins!");
                break;
            }
            if(checkTie()) {
                System.out.println("Its a tie!");
                break;
            }
            player = switchPlayer(player);
        } while(true);
    }

    public static boolean choiceInitializer(int choice, int player) {
        switch (choice) {
            case 1:
                if (arr[0][0] == 0) {
                    arr[0][0] = player;
                    return true;
                }
                break;
            case 2:
                if (arr[0][1] == 0) {
                    arr[0][1] = player;
                    return true;
                }
                break;
            case 3:
                if (arr[0][2] == 0) {
                    arr[0][2] = player;
                    return true;
                }
                break;
            case 4:
                if (arr[1][0] == 0) {
                    arr[1][0] = player;
                    return true;
                }
                break;
            case 5:
                if (arr[1][1] == 0) {
                    arr[1][1] = player;
                    return true;
                }
                break;
            case 6:
                if (arr[1][2] == 0) {
                    arr[1][2] = player;
                    return true;
                }
                break;
            case 7:
                if (arr[2][0] == 0) {
                    arr[2][0] = player;
                    return true;
                }
                break;
            case 8:
                if (arr[2][1] == 0) {
                    arr[2][1] = player;
                    return true;
                }
                break;
            case 9:
                if (arr[2][2] == 0) {
                    arr[2][2] = player;
                    return true;
                }
                break;
        }
        return false;
    }


    public static int getChoice() {
        return in.nextInt();
    }

    public static void showGameTable() {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count++;
                if (arr[i][j] == 0) {
                    System.out.print(" " + count + " ");
                } else if (arr[i][j] == 1) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" O ");
                }
            }
            System.out.println();
        }
    }

    private static boolean checkWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private static boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] != 0 && arr[i][0] == arr[i][1] && arr[i][0] == arr[i][2]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (arr[0][i] != 0 && arr[0][i] == arr[1][i] && arr[0][i] == arr[2][i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonals() {
        if (arr[0][0] != 0 && arr[0][0] == arr[1][1] && arr[0][0] == arr[2][2]) {
            return true;
        }
        if (arr[0][2] != 0 && arr[0][2] == arr[1][1] && arr[0][2] == arr[2][0]) {
            return true;
        }
        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int switchPlayer(int player) {
        if (player == PLAYER_X) {
            return PLAYER_O;
        } else {
            return PLAYER_X;
        }
    }



}

