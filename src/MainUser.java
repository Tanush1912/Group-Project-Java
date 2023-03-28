import java.util.*;

/**
 * Class to represent the main user (currently logged-in user)
 * 
 * @version 1.0
 * @author D.Kecha, T.Govind
 */
public class MainUser extends User {
    /**
     * Constructor to create the main user
     * 
     * @param user User object
     */
    public MainUser(User user) {
        super(user.getUsername(), user.getPassword(), user.getFullName(), user.getBio(), user.getEmail(), user.getWorkplace(), user.getCity(), user.getPhoneNumber(), user.getPosts(), user.getInterests());
        this.friends = user.getFriends();
    }

    /**
     * Method to allow the main user to write a new post
     * 
     * @param inputValidator Object to validate the input of the user
     */
    public void writePost(InputValidator inputValidator) {
        System.out.println("\n-> Type your new post ");
        String postContent = inputValidator.processStringInput();

        if (!postContent.isEmpty()) {
            System.out.println("\n-> Type any hashtags that you want to add to your post ");
            List<String> hashtags = inputValidator.processHashtagsInput();

            Random rand = new Random();
            int numberOfLikes = rand.nextInt(100) + 1;

            posts.add(new Post(postContent, numberOfLikes, hashtags));
            System.out.println("\n*** Your post has been successfully published! ***");
            System.out.printf(" - %d users like your post!\n", numberOfLikes);
        }
    }

    /**
     * Method to allow the main user to edit the post
     * 
     * @param postIndex The index of the post to be edited
     * @param inputValidator Object to validate the input of the user
     */
    public void editPost(int postIndex, InputValidator inputValidator) {
        System.out.println("\n-> Please, type the new content of your post:");
        String postContent = inputValidator.processStringInput();
        
        if (!postContent.isEmpty()) {
            System.out.println("\n-> Please, type new hashtags that you want to add to your post");
            List<String> hashtags = inputValidator.processHashtagsInput();

            posts.get(postIndex).setContent(postContent);
            posts.get(postIndex).setHashtags(hashtags);
            System.out.println("\n*** Your post has been successfully updated! ***");
        }
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