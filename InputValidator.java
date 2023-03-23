import java.util.Scanner;

/**
 *  Class responsible for processing and validating input of the main user
 * 
 *  @version 1.0
 *  @author D.Kecha, T.Govind
 */
public class InputValidator {
    /*
     * Scanner object to read the input from the main user
     */
    private Scanner scanner;

    /**
     * Default constructor to initialise the scanner
     */
    public InputValidator() {
        scanner = new Scanner(System.in);
    }

    /**
     * Method to check whether the username complies with the requirements
     * @param username Username to be validated
     */
    public boolean isValidUsername(String username) {
        String pattern = "^[a-zA-Z0-9_-]{3,20}$";
        return username.matches(pattern);
    }

    /**
     * Method to check whether the full name complies with the requirements
     * @param fullName Full name to be validated
     */
    public boolean isValidFullName(String fullName) {
        String pattern = "^[a-zA-Z]+\\s+[a-zA-Z]+$";
        return fullName.matches(pattern);
    }

    /**
     * Method to check whether the email complies with the requirements
     * @param email Email to be validated
     */
    public boolean isValidEmail(String email) {
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(pattern);
    }

    /**
     * Method to check whether the phone number complies with the requirements
     * @param phoneNumber Phone number to be validated
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^[+]?[0-9]{10,13}$";
        return phoneNumber.matches(pattern);
    }

    /**
     * Method to process the input of username from the user
     * 
     * @return Username entered by the user
     */
    public String processUsernameInput() {
        System.out.println(" - Please, enter the username: ");
        boolean isValid;
        String username = "";
        do {
            System.out.print(">>> ");
            username = scanner.nextLine();
            isValid = isValidUsername(username);

            if (!isValid) {
                System.out.println("### Error: Username must be between 3 and 20 characters long and can contain only letters, numbers, underscores and dashes.");
            }
        } while(!isValid);
        return username;
    }

    /**
     * Method to process the input of choice from the user
     * 
     * @return Choice entered by the user
     */
    public int processChoiceInput() {
        System.out.println(" - Please, enter your choice: ");
        boolean isValid;
        int choice = 0;
        do {
            System.out.print(">>> ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                // Check if the choice is within the range of options
                if (choice < 0 || choice > 9) {
                    throw new NumberFormatException();
                }
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("### Error: Please, enter a valid choice."); 
                isValid = false;
            }
        } while(!isValid);
        return choice;
    }

    /**
     * Method to process the input of any string from the user
     * 
     * @return String entered by the user
     */
    public String processStringInput() {
        System.out.println(" - Please, enter the value: ");
        System.out.print(">>> ");
        String input = scanner.nextLine();
        return input;
    }
}
