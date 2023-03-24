import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Class to represent the functionality of the social network
 * 
 * @version 1.0
 * @author D.Kecha, T.Govind
 */
public class SocialNetwork {
    /*
     * Hash map of users and their data
     */
    private Map<String, User> usersData;
    /*
     * Graph of users and their friends
     */
    private Graph connectionsGraph;
    /*
     * Main user of the social network
     */
    private static MainUser mainUser;
    /**
     * File name to store the social network data
     */
    final static String FILE_NAME = "network-data.txt";

    /**
     * Default constructor to initiate the social network
     */
    public SocialNetwork() {
        usersData = new HashMap<>();
        connectionsGraph = new Graph();
    }

    /**
     * Method to get the main user
     * 
     * @return Main user of the social network
     */
    public MainUser getMainUser() {
        return mainUser;
    }

    /**
     * Method to get the data of all the users in the social network
     * 
     * @return Hash map of users and their data
     */
    public Map<String, User> getUsersData() {
        return usersData;
    }

    /**
     * Method to load the social network data from the file
     */
    public void loadNetwork() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(FILE_NAME);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            boolean isMainUser = true;
            Map<String, Set<String>> friendsMap = new HashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String username = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String fullName = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String email = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String bio = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String workplace = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String city = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String phoneNumber = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);

                    line = bufferedReader.readLine();
                    List<Post> posts = new ArrayList<>();
                    String postsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    for (String post : postsCollection.split("; ")) {
                        String[] postInfo = post.split("/");
                        String content = postInfo[0].substring(1, postInfo[0].length()-1);
                        int likesNumber = Integer.parseInt(postInfo[1]);
                        String date = postInfo[2];
                        List<String> hashtags = new ArrayList<>();
                        String hashtagsString = postInfo[3].substring(1, postInfo[3].length() - 1);
                        for (String hashtag : hashtagsString.split(", ")) {
                            hashtags.add(hashtag);
                        }
                        Post postObj = new Post(content, likesNumber, date, hashtags);
                        posts.add(postObj);
                    }

                    line = bufferedReader.readLine();
                    Set<String> friends = new HashSet<>();
                    String friendsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    for (String friend : friendsCollection.split(", ")) {
                        String friendUsername = friend.substring(1, friend.length() - 1);
                        friends.add(friendUsername);
                    }
                    friendsMap.put(username, friends);

                    if (isMainUser) {
                        mainUser = new MainUser(username, fullName, bio, email, workplace, city, phoneNumber, posts);
                        isMainUser = false;
                    }
                    User user = new User(username, fullName, bio, email, workplace, city, phoneNumber, posts);
                    usersData.put(username, user);
                }
            }

            for (String user : friendsMap.keySet()) {
                for (String friend : friendsMap.get(user)) {
                    User userToAdd = usersData.get(friend);
                    usersData.get(user).addFriend(userToAdd);
                    if (user.equals(mainUser.getUsername())) {
                        mainUser.addFriend(userToAdd);
                    }
                }
            }

            for (User user : usersData.values()) {
                connectionsGraph.addUser(user);
            }
        } catch (IOException e) {
            System.out.println("### Error! Unable to load the network data!");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("### Error! Unable to close the network data file!");
                }
            }
        }
    }

    /**
     * Method to save the social network data to the file
     */
    public void saveNetwork() {
        FileOutputStream fileWriter = null;
        PrintWriter writer = null;
        try {
            fileWriter = new FileOutputStream(FILE_NAME);
            writer = new PrintWriter(fileWriter);

            writeToFile(writer, mainUser);
            for (User user : usersData.values()) {
                if (!user.getUsername().equals(mainUser.getUsername())) {
                    writeToFile(writer, user);
                }
            }
        } catch (IOException e) {
            System.out.println("### Error! Unable to save the network data!");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Method to write the entry with user data to the file
     * 
     * @param writer Reference to PrintWriter object to write to the file
     * @param user   User whose data to write to the file
     */
    public void writeToFile(PrintWriter writer, User user) {
        writer.printf("\"Username\": \"%s\"\n", user.getUsername());
        writer.printf("\"Full name\": \"%s\"\n", user.getFullName());
        writer.printf("\"Email\": \"%s\"\n", user.getEmail());
        writer.printf("\"Bio\": \"%s\"\n", user.getBio());
        writer.printf("\"Workplace\": \"%s\"\n", user.getWorkplace());
        writer.printf("\"City\": \"%s\"\n", user.getCity());
        writer.printf("\"Phone number\": \"%s\"\n", user.getPhoneNumber());
        String posts = "";
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Post post : user.getPosts()) {
            posts += "\"" + post.getContent() + "\"/" + post.getNumberOfLikes() + "/" + formatter.format(post.getDate())
                    + "; ";
        }
        writer.printf("\"Posts\": [%s]\n", posts.substring(0, posts.length() - 2));

        String friends = "";
        for (User friend : user.getFriends()) {
            friends += "\"" + friend.getUsername() + "\", ";
        }
        writer.printf("\"Friends\": [%s]\n", friends.substring(0, friends.length() - 2));
        writer.println();
    }

    /**
     * Method to compare the friends of the main user with the friends of another
     * user
     * 
     * @param userToCompare User whose friends to compare with the main user's
     *                      friends
     */
    public void compareFriends(User userToCompare) {
        Set<User> commonFriends = new HashSet<>();
        Set<User> uncommonFriends = new HashSet<>();
        for (User friend : userToCompare.getFriends()) {
            if (mainUser.getFriends().contains(friend)) {
                commonFriends.add(friend);
            } else if (!friend.getUsername().equals(mainUser.getUsername())) {
                uncommonFriends.add(friend);
            }
        }
        System.out.println(" - Friends you have in common: " + commonFriends);
        System.out.println(" - Users you may want to add to your friends: " + uncommonFriends);
    }

    /**
     * Method to search for the post of the users by the given hashtag
     * 
     * @param hashtag Hashtag to search for
     * @return List of posts with the given hashtag
     */
    public List<Post> searchByHashtag(String hashtag) {
        List<Post> posts = new ArrayList<>();
        for (User user : usersData.values()) {
            for (Post post : user.getPosts()) {
                if (post.getHashtags().contains(hashtag)) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    /**
     * Method to sort friends list by first name and print the list after sorting.
     * 
     * @param mainUser Main user whose friends list to sort
     */
    public void sortFriendsListByFirstName() {
        List<User> sortedFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            sortedFriendsList.add(friend);
        }
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User mainUser, User user2) {
                return mainUser.getFullName().compareTo(user2.getFullName());
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);

    }

    /**
     * Method to sort friends list by last name and print the list after sorting.
     * 
     * @param mainUser Main user whose friends list to sort
     */
    public void sortFriendsListByLastName() {
        List<User> sortedFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            sortedFriendsList.add(friend);
        }
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User mainUser, User user2) {
                return mainUser.getFullName().split(" ")[1].compareTo(user2.getFullName().split(" ")[1]);
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);
    }

    /**
     * Method to sort friends list by number of friends and print the list after
     * 
     * @param mainUser Main user whose friends list to sort
    */ 
    public void sortFriendsListByNumberOfFriends() {
        List<User> sortedFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            sortedFriendsList.add(friend);
        }
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User mainUser, User user2) {
                return mainUser.getFriends().size() - user2.getFriends().size();
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);

    }

    /**
     * Method to filter friend list by city and print the list after filtering
     * 
     * @param mainUser Main user whose friends list to filter
     */
    public void filterFriendsListByCity() {
        InputValidator inputValidator = new InputValidator();
        String city = inputValidator.getCityName(new Scanner(System.in));
        List<User> filteredFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            if (friend.getCity().equals(city)) {
                filteredFriendsList.add(friend);
            }
        }
        System.out.println(" - Filtered friends list: \n" + filteredFriendsList);
    }

    /**
     * Method to filter friend list by workplace and print the list after filtering
     * 
     * @param mainUser Main user whose friends list to filter
     */
    public void filterFriendsListByWorkplace() {
        InputValidator inputValidator = new InputValidator();
        String workplace = inputValidator.processStringInput();
        List<User> filteredFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            if (friend.getWorkplace().equals(workplace)) {
                filteredFriendsList.add(friend);
            }
        }
        System.out.println(" - Filtered friends list: " + filteredFriendsList);
    }

    /**
     * Method to recommend friends to the main user based on common friends 
     * 
     * @param mainUser friend's friend list to recommend friends to the main user
     */
    public void recommendFriends() {
        Set<User> recommendedFriends = new HashSet<>();
        for (User friend : mainUser.getFriends()) {
            for (User friendOfFriend : friend.getFriends()) {
                if (friendOfFriend != null && !mainUser.getFriends().contains(friendOfFriend)
                        && !friendOfFriend.getUsername().equals(mainUser.getUsername())) {
                    recommendedFriends.add(friendOfFriend);
                }
            }
        }
        if (!recommendedFriends.isEmpty()) {
            System.out.println(" - Recommended friends: " + recommendedFriends);
        }
    }

    /**
     * Method to recommend friends to the main user based on common friends, city and workplace. Strangers will
     * not be recommeneded even if users share same city
     * 
     * @param mainUser friend's friend list to recommend friends to the main user, compare mainUser city and workplace to recommened list of friends 
     */
    public void recommendFriendsBasedOnCityAndWorkplace() {
        Set<User> recommendedFriends = new HashSet<>();
        for (User friend : mainUser.getFriends()) {
            for (User friendOfFriend : friend.getFriends()) {
                if (friendOfFriend != null && !mainUser.getFriends().contains(friendOfFriend)
                        && !friendOfFriend.getUsername().equals(mainUser.getUsername())) {
                    recommendedFriends.add(friendOfFriend);
                }
            }
        }
        Set<User> recommendedFriendsWithSameCity = new HashSet<>();
        Set<User> recommendedFriendsWithSameWorkplace = new HashSet<>();
        for (User recommendedFriend : recommendedFriends) {
            if (recommendedFriend.getCity().equals(mainUser.getCity())) {
                recommendedFriendsWithSameCity.add(recommendedFriend);
            }
            if (recommendedFriend.getWorkplace().equals(mainUser.getWorkplace())) {
                recommendedFriendsWithSameWorkplace.add(recommendedFriend);
            }
        }
        System.out.println(" - Recommended friends with the same city: " + recommendedFriendsWithSameCity);
        System.out.println(" - Recommended friends with the same workplace: " + recommendedFriendsWithSameWorkplace);

    }

    /**
     * 
    public void editPost() {
        InputValidator inputValidator = new InputValidator();
        System.out.println("Enter the post id you want to edit: ");
        int postId = inputValidator.processIntInput();
        System.out.println("Enter the new text: ");
        String newText = inputValidator.processStringInput();
        for (Post post : mainUser.getPosts()) {
            if (post.getPostId() == postId) {
                post.setText(newText);
            }
        }
    }
    */
}
