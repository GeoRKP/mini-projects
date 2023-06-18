package gr.aueb.cf.Projects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Εισαγωγή κειμένου από ένα αρχείο και υπολογισμός συχνότητας εμφάνισης κάθε χαρακτήρα.
 */

public class CharFrequencyApp  {

    public static void main(String[] args) {

        int charFreq[][] = new int[256][2];
        int pivot = 0;
        int counter = 0;

        try (BufferedReader bf = new BufferedReader(new FileReader("C://tmp/project3.txt"))) {
            int character;
            int index = 0;

            while((character = bf.read()) != -1) {
                counter++;
                if ((index = charExist(charFreq, character, pivot)) != -1) {
                    charFreq[index][1]++;
                } else {
                    if (pivot > 255) {
                        System.out.println("The array is full. Character: " + character + " will not be inserted.");
                        continue;
                    } else {
                        charFreq[pivot][0] = character;
                        charFreq[pivot][1] = 1;
                        pivot++;
                    }
                }
            }

            printArr(shortByChar(charFreq, pivot), pivot, counter);
            printArr(shortByFreq(charFreq, pivot), pivot, counter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ελέγχει αν ένας χαρακτήρας υπάρχει ήδη στον πίνακα charFreq.
     *
     * @param charFreq Ο πίνακας με τις συχνότητες εμφάνισης των χαρακτήρων.
     * @param character Ο χαρακτήρας προς έλεγχο.
     * @param pivot Ο δείκτης που υποδεικνύει την επόμενη διαθέσιμη θέση στον πίνακα charFreq.
     * @return Τον δείκτης της θέσης στον πίνακα charFreq αν ο χαρακτήρας υπάρχει, διαφορετικά -1.
     */

    public static int charExist(int[][] charFreq, int character, int pivot) {

        for (int i = 0; i < pivot; i++) {
            if (character == charFreq[i][0] ) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Εκτύπωση των συχνοτήτων εμφάνισης των χαρακτήρων και του συνολικού αριθμού χαρακτήρων.
     *
     * @param charFreq Ο πίνακας με τις συχνότητες εμφάνισης των χαρακτήρων.
     * @param pivot Ο δείκτης που υποδεικνύει τον αριθμό των χαρακτήρων που έχουν καταχωρηθεί στον πίνακα.
     * @param counter Ο συνολικός αριθμός των χαρακτήρων.
     */

    public static void printArr(int[][] charFreq, int pivot, int counter) {
        for (int i = 0; i < pivot; i++) {
            System.out.println("The frequency of char: " + (char) charFreq[i][0] + " by persentage is: " + (float)charFreq[i][1] / counter * 100 + "%");
        }
        System.out.println();
    }

    /**
     * Ταξινόμηση του πίνακα charFreq με βάση τους χαρακτήρες.
     *
     * @param charFreq Ο πίνακας με τις συχνότητες εμφάνισης των χαρακτήρων.
     * @param pivot Ο δείκτης που υποδεικνύει τον αριθμό των χαρακτήρων που έχουν καταχωρηθεί στον πίνακα.
     * @return Τον ταξινομημένο πίνακα charFreq με βάση τους χαρακτήρες.
     */

    public static int[][] shortByChar(int[][] charFreq, int pivot) {
        Arrays.sort(charFreq, 0, pivot, Comparator.comparing(a -> a[0]));
        return charFreq;
    }

    /**
     * Ταξινόμηση του πίνακα charFreq με βάση τις συχνότητες εμφάνισης των χαρακτήρων.
     *
     * @param charFreq Ο πίνακας με τις συχνότητες εμφάνισης των χαρακτήρων.
     * @param pivot Ο δείκτης που υποδεικνύει τον αριθμό των χαρακτήρων που έχουν καταχωρηθεί στον πίνακα.
     * @return Τον ταξινομημένο πίνακα charFreq με βάση τις συχνότητες εμφάνισης των χαρακτήρων.
     */


    public static int[][] shortByFreq(int[][] charFreq, int pivot) {
        Arrays.sort(charFreq, 0, pivot, Comparator.comparing(a -> a[1]));
        return charFreq;
    }
}
