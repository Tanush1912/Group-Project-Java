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

    //method to return main user's full name
    public String getFullName() {
        return fullName;
    }
    
    /**
     * Method to write new post
     * 
     * @param content The content of the post
     * @param numberOfLikes The number of likes for the post
     */
    public void writePost(String content, int numberOfLikes) {
        posts.add(new Post(content, numberOfLikes));
    }
}
