/**
 * Class responsible for displaying the menu to the user
 * 
 * @version 1.0
 * @author D.Kecha, T.Govind
 */
public class Menu {
    /**
     * Object to access the social network
     */
    private SocialNetwork socialNetwork;
    /**
     * Object to validate the input of the user
     */
    private InputValidator inputValidator;

    /**
     * Default constructor to initialise the fields necessary for menu and load the social network from file
     */
    public Menu() {
        socialNetwork = new SocialNetwork();
        socialNetwork.loadNetwork();
        inputValidator = new InputValidator();
    }

    /**
     * Method to display the main menu to the user
     */
    public void displayMenu() {
        System.out.println("*** Welcome to the Facebook! ***");
        System.out.println("Please, choose one of the following options: ");
        // Example of how to format the options of the menu and display them: 
        System.out.println(" - 1. View the profile");
        System.out.println(" - 2. Edit the profile");
        System.out.println(" - 3. View the list of friends");
        // System.out.println(" - 0. Exit the program");
    }

    /**
     * Method to process the option selected by the user
     */
    public void processUserChoice() {
        int choice = inputValidator.processChoiceInput();
        switch (choice) {
            case 1:
                // Option 1 is selected...
                break;
            case 2:
                System.out.println("*** Choose what information to edit: \n - 1. Full name \n - 2. Email \n - 3. Bio \n - 4. Workplace \n - 5. City \n - 6. Phone number \n - 0. Exit");
                int editChoice = inputValidator.processChoiceInput();
                String input = ""; 
                boolean isValid = true;
                do {
                    input = inputValidator.processStringInput();
                    switch (editChoice) {
                        case 1:
                            if (!inputValidator.isValidFullName(input)) {
                                System.out.println("### Error: Invalid full name. Please, try again.");
                                isValid = false;
                            } else {
                                isValid = true;
                            }
                            break;
                        case 2:
                            if (!inputValidator.isValidEmail(input)) {
                                System.out.println("### Error: Invalid email. Please, try again.");
                                isValid = false;
                            } else {
                                isValid = true;
                            }
                            break;
                        case 6:
                            if (!inputValidator.isValidPhoneNumber(input)) {
                                System.out.println("### Error: Invalid phone number. Please, try again.");
                                isValid = false;
                            } else {
                                isValid = true;
                            }
                            break;
                    }
                } while (!isValid);
                socialNetwork.getMainUser().editProfile(editChoice, input);
                break;
            case 0:
                // Option 0 is selected...
                break;
            default:
                System.out.println("### Error: Invalid choice. Please, try again.");
                break;
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.displayMenu();
        menu.processUserChoice();
    }
}
