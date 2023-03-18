import java.util.*;

public class User {
    /**
     * Username
     */
    private String username;
    /**
     * User's full name
     */
    private String fullName;
    /**
     * User's bio
     */
    private String bio;
    /**
     * User's email
     */
    private String email;
    /**
     * User's workplace
     */
    private String workplace;
    /**
     * User's phone number
     */
    private String phoneNumber;
    /**
     * User's city
     */
    private String city;
    /**
     * User's collection of posts
     */
    private List<Post> posts;
    /**
     * User's collection of friends
     */
    private Set<User> friends;
    
    /**
     * Constructor to create the user
     * @param username Username
     * @param fullName User's full name
     * @param email User's email
     * @param bio User's bio
     * @param workplace User's workplace
     * @param city User's city
     * @param phoneNumber User's phone number
     * @param posts User's collection of posts
     * @param friends User's collection of friends
     */
    public User(String username, String fullName, String email, String bio, String workplace, String city, String phoneNumber, List<Post> posts, Set<User> friends) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.workplace = workplace;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.posts = posts;
        this.friends = friends;
    }

    /**
     * Method to get the username
     * @return String representing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to get the collection of friends
     * @return Set of friends
     */
    public Set<User> getFriends() {
        return friends;
    }

    /**
     * Method to set the full name
     * @param fullName User's full name to be set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Method to set the bio
     * @param bio User's bio to be set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Method to set the email
     * @param email User's email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to set the workplace
     * @param workplace User's workplace to be set
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * Method to set the phone number
     * @param phoneNumber User's phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method to set the city
     * @param city User's city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method to display the user's information
     */
    public void viewProfile() {
        // Display all the fields of the user in pretty format
    }
}