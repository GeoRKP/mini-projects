package gr.aueb.cf.Projects;

import java.util.Scanner;

/**
 * Εφαρμογή διαχείρισης θέσεων ενός θεάτρου.
 *
 * @ Giorgos Kapatsinas
 */
public class TheaterBookingApp {
    final static Scanner in = new Scanner(System.in);
    static boolean[][] seats = new boolean[30][12];

    public static void main(String[] args) {
        do {
            if (isFull()) {
                System.out.println("Το θέατρο είναι γεμάτο.");
                System.out.println("Η μόνη διαθέσιμη επιλογή είναι η ακύρωση θέσης.");
                cancelSeat();
                continue;
            }

            printMenu();
            String choice = getChoice();
            processChoice(choice);
        } while (true);
    }

    public static void printMenu() {
        System.out.println("Επιλέξτε ένα από τα παρακάτω:");
        System.out.println("1. Κράτηση θέσης");
        System.out.println("2. Ακύρωση θέσης");
        System.out.println("3. Προβολή διαθέσιμων θέσεων");
    }
    /**
     * Λαμβάνει την επιλογή του χρήστη.
     *
     * @return Την επιλογή του χρήστη
     */
    public static String getChoice() {
        System.out.print("Εισάγετε την επιλογή σας: ");
        return in.nextLine().trim();
    }
    /**
     * Επεξεργάζεται την επιλογή του χρήστη.
     *
     * @param choice Η επιλογή του χρήστη
     */
    public static void processChoice(String choice) {
        switch (choice) {
            case "1":
                bookSeat();
                break;
            case "2":
                cancelSeat();
                break;
            case "3":
                showAvailableSeats();
                break;
            default:
                System.out.println("Μη έγκυρη επιλογή. Παρακαλώ προσπαθήστε ξανά.");
                break;
        }
    }
    /**
     * Κάνει κράτηση μιας θέσης.
     */
    public static void bookSeat() {
        System.out.print("Εισάγετε τη στήλη (A-L): ");
        char column = Character.toUpperCase(in.nextLine().charAt(0));
        System.out.print("Εισάγετε τη σειρά (1-30): ");
        int row = Integer.parseInt(in.nextLine());

        if (isValidSeat(column, row)) {
            if (!isBooked(column, row)) {
                seats[row - 1][column - 'A'] = true;
                System.out.println("Η θέση " + column + row + " κρατήθηκε με επιτυχία.");
            } else {
                System.out.println("Η θέση " + column + row + " είναι ήδη κρατημένη.");
            }
        } else {
            System.out.println("Μη έγκυρη θέση. Παρακαλώ προσπαθήστε ξανά.");
        }
    }
    /**
     * Ακυρώνει την κράτηση μιας θέσης.
     */
    public static void cancelSeat() {
        System.out.print("Εισάγετε τη στήλη (A-L): ");
        char column = Character.toUpperCase(in.nextLine().charAt(0));
        System.out.print("Εισάγετε τη σειρά (1-30): ");
        int row = Integer.parseInt(in.nextLine());

        if (isValidSeat(column, row)) {
            if (isBooked(column, row)) {
                seats[row - 1][column - 'A'] = false;
                System.out.println("Η κράτηση για τη θέση " + column + row + " ακυρώθηκε επιτυχώς.");
            } else {
                System.out.println("Η θέση " + column + row + " δεν είναι κρατημένη.");
            }
        } else {
            System.out.println("Μη έγκυρη θέση. Παρακαλώ προσπαθήστε ξανά.");
        }
    }
    /**
     * Εμφανίζει τις διαθέσιμες θέσεις.
     */
    public static void showAvailableSeats() {
        System.out.println("Διαθέσιμες θέσεις:");

        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (!seats[row][col]) {
                    char column = (char) ('A' + col);
                    int seatNumber = row + 1;
                    System.out.print(column + "" + seatNumber + " ");
                }
            }
        }

        System.out.println();
    }
    /**
     * Ελέγχει αν οι θέση που δόθηκε είναι έγκυρη.
     *
     * @param column Η στήλη
     * @param row    Η σειρά
     * @return true αν η θέση είναι έγκυρη, αλλιώς false
     */
    public static boolean isValidSeat(char column, int row) {
        int columnIndex = column - 'A';
        int rowIndex = row - 1;
        return columnIndex >= 0 && columnIndex < seats[0].length && rowIndex >= 0 && rowIndex < seats.length;
    }
    /**
     * Ελέγχει αν η θέση είναι κρατημένη.
     *
     * @param column Η στήλη
     * @param row    Η σειρά
     * @return true αν η θέση είναι κρατημένη, αλλιώς false
     */
    public static boolean isBooked(char column, int row) {
        int columnIndex = column - 'A';
        int rowIndex = row - 1;
        return seats[rowIndex][columnIndex];
    }
    /**
     * Ελέγχει αν όλες οι θέσεις είναι κρατημένες.
     *
     * @return true αν αν όλες οι θέσεις είναι κρατημένες, αλλιώς false
     */
    public static boolean isFull() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (!seats[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}
