import java.util.*;

public class MainUser extends User {
    public MainUser(String username, String fullName, String bio, String email, String workplace, String phoneNumber, String city, List<Post> posts) {
        super(username, fullName, bio, email, workplace, phoneNumber, city, posts);
    }

    /**
     * Method to edit the main user's profile
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
     * Method to remove a friend from the main user's friends list
     * 
     * @param user The user to be removed from friends list
     */
    public void removeFriend(User user) {
        friends.remove(user);
    }

    /**
     * Method to write new post
     * 
     * @param content The content of the post
     * @param numberOfLikes The number of likes for the post
     * @param hashtags The hashtags for the post
     */
    public void writePost(String content, int numberOfLikes, List<String> hashtags) {
        posts.add(new Post(content, numberOfLikes, hashtags));
    }

    /**
     * Method to like the post of another user
     * 
     * @param userToLike     User whose post will be liked
     * @param inputValidator Object to validate the user's input
     */
    public void likePost(User userToLike, InputValidator inputValidator) {
        boolean isValid = true;
        do {
            System.out.println(" - Do you want to like any post of this user? (Y/N)");
            String likeChoice = inputValidator.processStringInput();
            if (likeChoice.equalsIgnoreCase("Y")) {
                System.out.println(" - Enter the number of the post you want to like: ");
                int postNumber = inputValidator.processChoiceInput() - 1;
                try {
                    userToLike.getPosts().get(postNumber).addLike();
                    isValid = true;
                    System.out.println("### You have successfully liked this post! ###");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("### Error: This post does not exist! Please, try again.");
                    isValid = false;
                }
            } else if (!likeChoice.equalsIgnoreCase("N")) {
                System.out.println("### Error: Invalid choice. Please, try again.");
                isValid = false;
            }
        } while (!isValid);
    }

}
