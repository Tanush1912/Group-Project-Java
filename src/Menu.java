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
     * Object to provide access to the main user of the social network
     */
    private MainUser mainUser;


    /**
     * Default constructor to initialise the fields necessary for menu and load the social network from file
     */
    public Menu() {
        socialNetwork = new SocialNetwork();
        socialNetwork.loadNetwork();
        inputValidator = new InputValidator();
        mainUser = socialNetwork.getMainUser();
    }

    /**
     * Method to display the main menu to the user
     */
    public void displayMainMenu() {
        System.out.println("\n*** Welcome to the BeNotReal! ***\n");
        System.out.println(" - Please, select one the following options: ");
        System.out.println(" -> 1. View my Profile");
        System.out.println(" -> 2. Edit my Profile");
        System.out.println(" -> 3. Manage my Friends");
        System.out.println(" -> 4. View Recommended Friends");
        System.out.println(" -> 5. Manage my Posts");
        System.out.println(" -> 6. Search Posts by Hashtag");
        System.out.println(" -> 7. Update the social network");
        System.out.println(" -> 0. Sign out");
    }

    /**
     * Method to process the option selected by the user
     */
    public void runMenu() {
        int choice;
        int maxChoice = 7;
        do {
            displayMainMenu();
            choice = inputValidator.processChoiceInput(maxChoice);
            switch (choice) {
                case 1:
                    mainUser.viewProfile();
                    break;
                case 2:
                    editProfileMenu();
                    break;
                case 3:
                    manageFriendsMenu();
                    break;
                case 4: 
                    socialNetwork.recommendFriends();
                    socialNetwork.recommendFriendsByCityAndWorkplace();
                    break;
                case 5:
                    managePostsMenu();
                    break;
                case 6:
                    socialNetwork.searchPostByHashtag(inputValidator);
                    break;
                case 7:
                    socialNetwork.saveNetwork();
                    break;
                case 0:
                    System.out.println("*** You have succesfully signed out! ***");
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0) ;
    }

    public void editProfileMenu() {
        System.out.println(
                "\n -> Select what information you would like to edit: \n - 1. Full name \n - 2. Email \n - 3. Bio \n - 4. Workplace \n - 5. City \n - 6. Phone number \n - 0. Exit");
        int maxChoice = 6;
        int editChoice = inputValidator.processChoiceInput(maxChoice);
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
                case 0:
                    isValid = true;
                    break;
            }
        } while (!isValid);

        if (editChoice != 0) {
            mainUser.editProfile(editChoice, input);
            System.out.println("*** Your profile has been successfully updated! ***");
        }
    }

    public void manageFriendsMenu() {
        int choice;
        do {
            mainUser.viewFriends();
            System.out.println(
                    "\n -> Select what you would like to do with your friends: \n - 1. View Profile of my Friend \n - 2. Remove my Friend \n - 3. View my Friend's List of Friends \n - 4. Sort my Friends \n - 5. Filter my Friends \n - 0. Back to Main Menu");
            int maxChoice = 5;
            choice = inputValidator.processChoiceInput(maxChoice);
            switch (choice) {
                case 1:
                    String usernameProfile = inputValidator.processUsernameInput();
                    socialNetwork.manageFriends(choice, usernameProfile, inputValidator);
                    break;
                case 2:
                    String usernameToRemove = inputValidator.processUsernameInput();
                    socialNetwork.manageFriends(choice, usernameToRemove, inputValidator);
                    break;
                case 3:
                    String usernameFriendsList = inputValidator.processUsernameInput();
                    socialNetwork.manageFriends(choice, usernameFriendsList, inputValidator);
                    break;
                case 4:
                    displaySortOptions();
                    break;
                case 5:
                    displayFilterOptions();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Method to display the options how to sort the friends list
     */
    public void displaySortOptions() {
        int choice;
        int maxChoice = 3;
        do {
            System.out.println(
                    "\n -> Select how you would like to sort your friends list: \n - 1. By First name \n - 2. By Last name \n - 3. By Number of friends \n - 0. Back to Sub Menu");
            choice = inputValidator.processChoiceInput(maxChoice);
            switch (choice) {
                case 1:
                    socialNetwork.sortFriendsListByFirstName();
                    break;
                case 2:
                    socialNetwork.sortFriendsListByLastName();
                    break;
                case 3:
                    socialNetwork.sortFriendsListByTotalNumberOfFriends();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }


    /**
     * Method to display the options how to filter the friends list
     */
    public void displayFilterOptions() {
        int choice;
        int maxChoice = 2;
        do {
            System.out.println(
                    "\n -> Select how you would like to filter your friends list: \n - 1. By City \n - 2. By Workplace \n - 0. Back to Sub Menu");
            choice = inputValidator.processChoiceInput(maxChoice);
            switch (choice) {
                case 1:
                    socialNetwork.filterFriendsListByCity(inputValidator);
                    break;
                case 2:
                    socialNetwork.filterFriendsListByWorkplace(inputValidator);
                    break;
                case 0:
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Method to display the options of managing the posts of the main user
     */
    public void managePostsMenu() {
        int choice;
        do {
            mainUser.viewPosts();
            System.out.println(
                    "\n -> Select what you would like to do with your posts: \n - 1. Edit Post \n - 2. Write New Post \n - 3. Delete Post \n - 0. Back to Main Menu");
            int maxChoice = 3;
            choice = inputValidator.processChoiceInput(maxChoice);
            switch (choice) {
                case 1:
                    System.out.println(" - Choose the post you would like to edit");
                    int postToEdit = inputValidator.processChoiceInput(mainUser.getPosts().size()) - 1;
                    mainUser.editPost(postToEdit, inputValidator);
                    break;
                case 2:
                    mainUser.writePost(inputValidator);
                    break;
                case 3:
                    System.out.println(" - Choose the post you would like to delete");
                    int postToDelete = inputValidator.processChoiceInput(mainUser.getPosts().size()) - 1;
                    mainUser.deletePost(postToDelete);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Main method to launch the program
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.runMenu();
    }
}
