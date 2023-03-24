import java.util.*;

public class User {
    /**
     * Username
     */
    protected String username;
    /**
     * User's full name
     */
    protected String fullName;
    /**
     * User's bio
     */
    protected String bio;
    /**
     * User's email
     */
    protected String email;
    /**
     * User's workplace
     */
    protected String workplace;
    /**
     * User's phone number
     */
    protected String phoneNumber;
    /**
     * User's city
     */
    protected String city;
    /**
     * User's collection of posts
     */
    protected List<Post> posts;
    /**
     * User's collection of friends
     */
    protected Set<User> friends;

    /**
     * Constructor to create the user
     * 
     * @param username    Username
     * @param fullName    User's full name
     * @param email       User's email
     * @param bio         User's bio
     * @param workplace   User's workplace
     * @param city        User's city
     * @param phoneNumber User's phone number
     * @param posts       User's collection of posts
     * @param friends     User's collection of friends
     */
    public User(String username, String fullName, String email, String bio, String workplace, String city,
            String phoneNumber, List<Post> posts) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.workplace = workplace;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.posts = posts;
        this.friends = new HashSet<>();
    }

    /**
     * Method to get the username
     * 
     * @return String representing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to get the full name
     * 
     * @return String representing the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Method to get the bio
     * 
     * @return String representing the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Method to get the email
     * 
     * @return String representing the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get the workplace
     * 
     * @return String representing the workplace
     */
    public String getWorkplace() {
        return workplace;
    }

    /**
     * Method to get the phone number
     * 
     * @return String representing the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method to get the city
     * 
     * @return String representing the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Method to get the collection of posts
     * 
     * @return List of posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Method to get the collection of friends
     * 
     * @return Set of friends
     */
    public Set<User> getFriends() {
        return friends;
    }

    /**
     * Method to set the full name
     * 
     * @param fullName User's full name to be set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Method to set the bio
     * 
     * @param bio User's bio to be set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Method to set the email
     * 
     * @param email User's email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to set the workplace
     * 
     * @param workplace User's workplace to be set
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * Method to set the phone number
     * 
     * @param phoneNumber User's phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method to set the city
     * 
     * @param city User's city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method to set the collection of posts
     * 
     * @param friends User's collection of posts to be set
     */
    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    /**
     * Method to display the user's information
     */
    public void viewProfile() {
        System.out.println();
        System.out.printf(" *** %s's Profile ***\n", username);
        System.out.println(" - Full name: " + fullName);
        System.out.println(" - Bio: " + bio);
        System.out.println(" - Email: " + email);
        System.out.println(" - Workplace: " + workplace);
        System.out.println(" - Phone number: " + phoneNumber);
        System.out.println(" - City: " + city);
        System.out.printf(" - %s posted:\n", username);
        for (Post post : posts) {
            System.out.printf("*** \"%s\" - posted on %s, %d users liked it\n", post.getContent(), post.getDate(),
                    post.getNumberOfLikes());
        }
        System.out.println("******");
        System.out.println();
    }

    /**
     * Method to display the user's friends
     */
    public void viewFriends() {
        System.out.printf(" *** %s's friends ***\n", username);
        for (User friend : friends) {
            System.out.println(" - " + friend.getUsername());
        }
        System.out.println("***");
    }

    /**
     * Method to add a friend to the user's friends list
     * 
     * @param user The user to be added to friends list
     */
    public void addFriend(User user) {
        friends.add(user);
    }

    /**
     * Method to override the string representation of the user
     */
    @Override
    public String toString() {
        return username;
    }

    public Object getcity() {
        return city;
    }
}
