import java.util.*;

/**
 * Class to represent the main user (currently logged-in user)
 * 
 * @version 1.0
 * @author D.Kecha, T.Govind
 */
public class MainUser extends User {
    public MainUser(String username, String fullName, String bio, String email, String workplace, String phoneNumber, String city, List<Post> posts) {
        super(username, fullName, bio, email, workplace, phoneNumber, city, posts);
    }

    /**
     * Method to allow the main user to edit their profile
     * 
     * @param choice The choice of the user on what to edit
     */
    public void editProfile(int choice, String input) {
        switch (choice) {
            case 1:
                setFullName(input);
                break;
            case 2:
                setEmail(input);
                break;
            case 3:
                setBio(input);
                break;
            case 4:
                setWorkplace(input);
                break;
            case 5:
                setCity(input);
                break;
            case 6:
                setPhoneNumber(input);
                break;
            default:
                break;
        }
    }

    /**
     * Method to allow the main user to write a new post
     * 
     * @param inputValidator Object to validate the input of the user
     */
    public void writePost(InputValidator inputValidator) {
        System.out.println("\n-> Type your new post ");
        String postContent = inputValidator.processStringInput();

        System.out.println("\n-> Type any hashtags that you want to add to your post ");
        List<String> hashtags = inputValidator.processHashtagsInput();

        Random rand = new Random();
        int numberOfLikes = rand.nextInt(100) + 1;

        posts.add(new Post(postContent, numberOfLikes, hashtags));
        System.out.println("\n*** Your post has been successfully published! ***");
        System.out.printf(" - %d users like your post!\n", numberOfLikes);
    }

    /**
     * Method to allow the main user to edit the post
     * 
     * @param postIndex The index of the post to be edited
     * @param inputValidator Object to validate the input of the user
     */
    public void editPost(int postIndex, InputValidator inputValidator) {
        System.out.println("\n-> Type the new content of your post ");
        String postContent = inputValidator.processStringInput();

        System.out.println("\n-> Type new hashtags that you want to add to your post ");
        List<String> hashtags = inputValidator.processHashtagsInput();

        posts.get(postIndex).setContent(postContent);
        posts.get(postIndex).setHashtags(hashtags);
        System.out.println("\n*** Your post has been successfully updated! ***");
    }

    /**
     * Method to allow the main user to delete the post
     * 
     * @param postIndex The index of the post to be deleted
     */
    public void deletePost(int postIndex) {
        posts.remove(postIndex);
    }
}