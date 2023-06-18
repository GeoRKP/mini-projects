package gr.aueb.cf.Projects;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class MobileContactsApp {
    final static Scanner in = new Scanner(System.in);
    final static String[][] contacts = new String[500][3];
    static int pivot = -1;
    final static Path path = Paths.get("C:/tmp/log-mobile.txt");

    public static void main(String[] args) {
        boolean quit = false;
        String s;
        int choice;
        String phoneNumber;
        do {
            printMenu();
            s = getChoice();
            if (s.matches("[qQ]")) quit = true;
            else {
                try {
                    choice = Integer.parseInt(s);
                    if (!isValid(choice)) {
                        throw new IllegalArgumentException("Error - choice must be between 1 - 5");
                    }

                    switch (choice) {
                        case 1:
                            printContactMenu();
                            insertController(getFirstname(), getLastname(), getPhoneNumber());
                            System.out.println("Επιτυχής");
                            break;
                        case 2:
                            phoneNumber = getPhoneNumber();
                            deleteContactController(phoneNumber);
                            System.out.println("Επιτυχής");
                            break;
                        case 3:
                            phoneNumber = getPhoneNumber();
                            printContactMenu();
                            updateController(phoneNumber, getFirstname(), getLastname(), getPhoneNumber());
                            break;
                        case 4:
                            phoneNumber = getPhoneNumber();
                            String[] contact = getOneController(phoneNumber);
                            printContact(contact);
                            break;
                        case  5:
                            String[][] allContacts = getAllContactsController();
                            printAllContacts(contacts);
                            break;
                        default:
                            throw new IllegalArgumentException("Bad choice");

                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!quit);
    }

    public static void printContact(String[] contact) {
        for (String s : contact) {
            System.out.print(s + " ");
        }
    }

    public static void printAllContacts(String[][] contacts) {
        for (String[] contact : contacts) {
            printContact(contact);
        }
    }

    public static boolean isValid(int choice) {
        return (choice >= 1 && choice <= 5);
    }

    public static void printMenu() {
        System.out.println("Επιλέξτε ένα από τα παρακάτω");
        System.out.println("1. Εισαγωγή επαφής");
        System.out.println("1. Διαγραφή επαφής");
        System.out.println("1. Ενημέρωση επαφής");
        System.out.println("1. Αναζήτηση επαφής");
        System.out.println("1. Εκτύπωση όλων των επαφών");
        System.out.println("Q. Έξοδος");
    }

    static public String getChoice() {
        System.out.println("Εισάγεται επιλογή");
        return in.nextLine().trim();
    }

    public static void printContactMenu() {
        System.out.println("Εισάγεται όνομα επώνυμο και τηλέφωνο");
    }

    static public String getFirstname() {
        System.out.println("Εισάγεται όνομα");
        return in.nextLine().trim();
    }

    static public String getLastname() {
        System.out.println("Εισάγεται επώνυμο");
        return in.nextLine().trim();
    }

    static public String getPhoneNumber() {
        System.out.println("Εισάγεται αριθμό τηλεφώνου");
        return in.nextLine().trim();
    }



    /*
     * Controllers
     */

    public static void insertController(String firstname, String lastname, String phoneNumber) {
        try {
            if (firstname == null || lastname == null || phoneNumber == null) {
                throw new IllegalArgumentException("Nulls are not allowed");
            }
            if (firstname.length() < 2 || firstname.length() > 50) {
                throw new IllegalArgumentException("Firstname is not valid");
            }
            if (lastname.length() < 2 || lastname.length() > 50) {
                throw new IllegalArgumentException("Lastname is not valid");
            }
            if (phoneNumber.length() < 2 || phoneNumber.length() > 50) {
                throw new IllegalArgumentException("Phone number is not valid");
            }

            insertContactService(firstname.trim(), lastname.trim(), phoneNumber.trim());
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            throw e;
        }
    }

    public static void updateController(String oldPhoneNumber, String firstname, String lastname, String newPhoneNumber) {
        try {
            if (oldPhoneNumber == null || firstname == null || lastname == null || oldPhoneNumber == null) {
                throw new IllegalArgumentException("Nulls are not allowed");
            }
            if (oldPhoneNumber.length() < 2 || oldPhoneNumber.length() > 50) {
                throw new IllegalArgumentException("Old phone number is not valid");
            }
            if (firstname.length() < 2 || firstname.length() > 50) {
                throw new IllegalArgumentException("Firstname is not valid");
            }
            if (lastname.length() < 2 || lastname.length() > 50) {
                throw new IllegalArgumentException("Lastname is not valid");
            }
            if (newPhoneNumber.length() < 2 || newPhoneNumber.length() > 50) {
                throw new IllegalArgumentException("New phone number is not valid");
            }

            updateContactService(oldPhoneNumber.trim(), firstname.trim(), lastname.trim(), newPhoneNumber.trim());

        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            throw e;
        }
    }

    public static String[] deleteContactController(String phoneNumber) {
        try {
            if (phoneNumber.length() < 2 || phoneNumber.length() > 50 || phoneNumber == null) {
                throw new IllegalArgumentException("Phone number is not valid");
            }
            return deleteContactService(phoneNumber);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            throw e;
        }
    }

    public static String[] getOneController(String phoneNumber) {
        try {
            if (phoneNumber.length() < 2 || phoneNumber.length() > 50 || phoneNumber == null) {
                throw new IllegalArgumentException("Phone number is not valid");
            }
            return getOneContactService(phoneNumber);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String[][] getAllContactsController() {
        try {
            return getAllContactsService();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
    }



    /*
     * Service layer
     */

    public static String[] getOneContactService(String phoneNumber) {
        try {
            String[] contact = getContactByPhoneNumber(phoneNumber);
            if (contact.length == 0) {
                throw new IllegalArgumentException("Contact not found");
            }

            return contact;
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }

    }

    public static String[][] getAllContactsService() throws IllegalArgumentException {

        String[][] contactsList = getAllContacts();
        try {
            if (contactsList.length == 0) {
                throw new IllegalArgumentException("List is empty");
            }
            return contactsList;
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    public static void insertContactService(String firstname, String lastname, String phoneNumber) {
        try {
            if ((!insert(firstname, lastname, phoneNumber))){
                throw new IllegalArgumentException("Error in insert");
            }
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    public static void updateContactService(String oldPhoneNumber, String firstname, String lastname, String newPhoneNumber) {
        try {
            if (!(update(oldPhoneNumber, firstname, lastname, newPhoneNumber))) {
                throw new IllegalArgumentException("Error in update");
            }

        } catch (IllegalArgumentException e) {
            log(e);
            throw e;

        }
    }

    public static String[] deleteContactService(String phoneNumber) {
        String[] contact;
        try {
            contact = delete(phoneNumber);
            if (contact.length == 0){
                throw new IllegalArgumentException("Error in Delete");
            }
            return contact;
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /*
    Crud services that are provided to Service layer.
     */

    public static int getIndexByPhoneNumber(String phoneNumber) {
        for (int i = 0; i < pivot; i++) {
            if (contacts[i][2].equals(phoneNumber)) {
                return i;
            }
        }
         return -1; // if not found
    }

    public static boolean insert(String firstname, String lastname, String phoneNumber) {
        boolean inserted = false;
        if (isFull(contacts)) {
            return false;
        }

        if (getIndexByPhoneNumber(phoneNumber) != -1) {
            return false;
        }

        pivot++;
        contacts[pivot][0] = firstname;
        contacts[pivot][1] = lastname;
        contacts[pivot][2] = phoneNumber;

        return true;
    }

    public static boolean update(String oldPhoneNumber, String firstname, String lastname, String newPhoneNumber) {
        int positionToUpdate = getIndexByPhoneNumber(oldPhoneNumber);

        if (positionToUpdate == -1) return true;

        contacts[positionToUpdate][0] = firstname;
        contacts[positionToUpdate][1] = lastname;
        contacts[positionToUpdate][2] =newPhoneNumber;

        return true;
    }

    public static String[] delete(String phoneNumber) {
        int positionToDelete = getIndexByPhoneNumber(phoneNumber);
        String[] contact = new String[3];

        if (positionToDelete == -1) {
            return new String[] {};
        }

        for (int i = 0; i < contact.length; i++) {
            contact[i] = contacts[positionToDelete][i];
        }
        //Εναλλακτικά
        //System.arraycopy(contacts[positionToDelete], 0, contact, 0, contact.length);

        if (!(positionToDelete == contacts.length -1)) {
            System.arraycopy(contacts, positionToDelete + 1, contacts, positionToDelete, pivot - positionToDelete);
        }

        pivot--;
        return contact;
    }

    public static String[] getContactByPhoneNumber(String phoneNumber) {
        int positionToReturn = getIndexByPhoneNumber(phoneNumber);

        if (positionToReturn == -1) {
            return new String[] {};
        }

        return contacts[positionToReturn];
    }

    public static String[][] getAllContacts() {
        return Arrays.copyOf(contacts,pivot + 1);
    }

    public static boolean isFull(String[][] arr) {
        return pivot == arr.length - 1;
    }

    /*
     * Custom logger
     */

    public static void log(Exception e, String... message) {
        try (PrintStream ps = new PrintStream(new FileOutputStream(path.toFile(), true))) {
            ps.println(LocalDateTime.now() + "\n" + e.toString());
            ps.printf("%s", (message.length == 1) ? message[0] : "");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
