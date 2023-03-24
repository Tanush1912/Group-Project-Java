import java.util.List;
import java.util.Map;
import java.util.Random;

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
     * Object to provide access to the data of all the users in the social network
     */
    private Map<String, User> usersData;

    /**
     * Default constructor to initialise the fields necessary for menu and load the
     * social network from file
     */
    public Menu() {
        socialNetwork = new SocialNetwork();
        socialNetwork.loadNetwork();
        inputValidator = new InputValidator();
        mainUser = socialNetwork.getMainUser();
        usersData = socialNetwork.getUsersData();
    }

    /**
     * Method to display the main menu to the user
     */
    public void displayMenu() {
        System.out.println("*** Welcome to the Facebook! ***");
        System.out.println("Please, choose one of the following options: ");
        System.out.println(" - 1. View my profile");
        System.out.println(" - 2. Edit my profile");
        System.out.println(" - 3. View my list of friends");
        System.out.println(" - 4. View Recommended Friends");
        System.out.println(" - 5. Manage my Posts");
        System.out.println(" - 6. Search posts by hashtag");
        System.out.println(" - 7. Save the social network");
        System.out.println(" - 0. Sign out");
    }

    /**
     * Method to process the option selected by the user
     */
    public void runMenu() {
        int choice = -1;
        while (choice != 0) {
            displayMenu();
            choice = inputValidator.processChoiceInput();
            switch (choice) {
                case 1:
                    mainUser.viewProfile();
                    break;
                case 2:
                    editMainUserProfile();
                    break;
                case 3:
                    interactWithFriends();
                    break;
                case 4:
                socialNetwork.recommendFriends();
                    socialNetwork.recommendFriendsBasedOnCityAndWorkplace();
                    
                    break;
                case 5:
                    writeNewPost();
                    //managePosts();
                    break;
                case 6:
                    searchForPost();
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
        }
    }

    /**
     * Method to search for the posts by hashtag
     */
    public void searchForPost() {
        System.out.println("*** Please, enter the hashtag you would like to search for: ");
        String hashtag = inputValidator.processStringInput();
        List<Post> posts = socialNetwork.searchByHashtag(hashtag);
        if (posts.size() > 0) {
            System.out.println("*** The following posts have been found: ");
            for (Post post : posts) {
                System.out.printf(" - \"%s\" | posted on %s | %d likes\n", post.getContent(), post.getFormattedDate(), post.getNumberOfLikes());
            }
            System.out.println("");
            mainUser.likePost(inputValidator, posts);
        } else {
            System.out.printf("### Error: No posts found with the hashtag: %s!\n", hashtag);
        }
    }

    //A menu for manage posts with 3 options 1. write new post 2. edit posts 3. back to main menu
    public void managePosts() {
        System.out.println(
                "*** Choose what you would like to do with your posts: \n - 1. Write new post \n - 2. Edit posts \n - 0. Back to main menu");
        int choice = -1;
        do {
            choice = inputValidator.processChoiceInput();
            switch (choice) {
                case 1:
                    writeNewPost();
                    break;
                case 2:
                    break;
                case 0:
                    System.out.println("*** You have succesfully signed out! ***");
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Method to edit the main user's profile
     */
    public void editMainUserProfile() {
        System.out.println(
                "*** Choose what information you would like to edit: \n - 1. Full name \n - 2. Email \n - 3. Bio \n - 4. Workplace \n - 5. City \n - 6. Phone number \n - 0. Exit");
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
        mainUser.editProfile(editChoice, input);
    }

    /**
     * Method to interact with the friends of the main user and run queries on them
     */
    public void interactWithFriends() {
        mainUser.viewFriends();
        System.out.println(
                "*** Choose what you would like to do with your friends: \n - 1. View the profile of a friend \n - 2. Remove a friend \n - 3. View the friend's list of friends \n - 4. Sort the List of Friends \n - 5. Filter the List of friends \n - 0. Back to main menu");
        int choice = -1;
        do {
            choice = inputValidator.processChoiceInput();
            switch (choice) {
                case 1:
                    String usernameProfile = inputValidator.processUsernameInput();
                    if (mainUser.getFriends().contains(usersData.get(
                            usernameProfile))) {
                        usersData.get(usernameProfile).viewProfile();
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
                case 2:
                    String usernameToRemove = inputValidator.processUsernameInput();
                    if (mainUser.getFriends().contains(usersData.get(
                            usernameToRemove))) {
                        mainUser.removeFriend(usersData.get(usernameToRemove));
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
                case 3:
                    String usernameFriendsList = inputValidator.processUsernameInput();
                    if (mainUser.getFriends().contains(usersData.get(usernameFriendsList))) {
                        User userToView = usersData.get(usernameFriendsList);
                        userToView.viewFriends();
                        socialNetwork.compareFriends(userToView);
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
                case 4:
                    sort();
                    break;
                case 5:
                    filter();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }

    public void sort() {
        System.out.println(
                "Choose how you would like to sort your friends list: \n - 1. By first name \n - 2. By last name \n - 3. By Number of friends \n - 0. Back to sub menu");
        int choice = -1;
        do {
            choice = inputValidator.processChoiceInput();
            switch (choice) {
                case 1:
                    socialNetwork.sortFriendsListByFirstName();
                    break;
                case 2:
                    socialNetwork.sortFriendsListByLastName();
                    break;
                case 3:
                    socialNetwork.sortFriendsListByNumberOfFriends();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("### Error: Invalid choice. Please, try again.");
                    break;
            }
        } while (choice != 0);
    }

    public void filter() {
        System.out.println(
                "Choose how you would like to filter your friends list: \n - 1. By City \n - 2. By Workplace \n - 0. Back to sub menu");
        int choice = -1;
        do {
            choice = inputValidator.processChoiceInput();
            switch (choice) {
                case 1:
                    socialNetwork.filterFriendsListByCity();
                    break;
                case 2:
                    socialNetwork.filterFriendsListByWorkplace();
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
     * Method to write a new post
     */
    public void writeNewPost() {
        System.out.println(" - Type your new post: ");
        String postContent = inputValidator.processStringInput();
        System.out.println(" - Type any hashtags you want to add to your post: ");
        List<String> hashtags = inputValidator.processHashtagsInput();

        Random rand = new Random();
        int numberOfLikes = rand.nextInt(100) + 1;
        mainUser.writePost(postContent, numberOfLikes, hashtags);
        System.out.println("*** Your post has been successfully published! ***");
        System.out.printf(" - %d users like your post!\n", numberOfLikes);
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
