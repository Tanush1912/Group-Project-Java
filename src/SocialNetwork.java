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
     * Main user of the social network
     */
    private MainUser mainUser;
    /**
     * File name to store the social network data
     */
    private final static String FILE_NAME = "../network-data.txt";

    /**
     * Default constructor to initiate the social network
     */
    public SocialNetwork() {
        usersData = new HashMap<>();
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
                    if (!postsCollection.isEmpty()) {
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
                    }
                    
                    line = bufferedReader.readLine();
                    Set<String> friends = new HashSet<>();
                    String friendsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    if (!friendsCollection.isEmpty()) {
                        for (String friend : friendsCollection.split(", ")) {
                            String friendUsername = friend.substring(1, friend.length()-1);
                            friends.add(friendUsername);
                        }
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
     * @param user User whose data to write to the file
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
            posts += String.format("\"%s\"/%d/%s/%s; ", post.getContent(), post.getNumberOfLikes(), formatter.format(post.getDate()), post.getHashtags());
        }
        writer.printf("\"Posts\": [%s]\n", posts.substring(0, posts.length() - 2));

        String friends = "";
        for (User friend : user.getFriends()) {
            friends += "\"" + friend.getUsername() + "\", ";
        }
        if (!friends.isEmpty()) {
            friends = friends.substring(0, friends.length() - 2);
        }
        writer.printf("\"Friends\": [%s]\n", friends);
        writer.println();
    }

    /**
     * Method to compare the friends of the main user with the friends of another user
     * 
     * @param userToCompare User whose friends to compare with the main user's friends
     * @return Collection of friends who are previously unknown to the main user
     */
    public Set<User> compareFriends(User userToCompare) {
        Set<User> commonFriends = new HashSet<>();
        Set<User> unknownFriends = new HashSet<>();
        for (User friend : userToCompare.getFriends()) {
            if (mainUser.getFriends().contains(friend)) {
                commonFriends.add(friend);
            } else if (!friend.getUsername().equals(mainUser.getUsername())) {
                unknownFriends.add(friend);
            }
        }
        System.out.println(" - Friends you have in common: " + commonFriends);
        System.out.println(" - Users you may want to add to your friends: " + unknownFriends);
        return unknownFriends;
    }

    /**
     * Method to add a friend
     * 
     * @param inputValidator Object to validate the user's input
     * @param users Collection of users to choose from
     */
    public void addNewFriend(InputValidator inputValidator, Set<User> users) {
        boolean isValid = true;
        do {
            System.out.println("\n - Do you want anyone to your friend list? (Y/N)");
            String addFriendChoice = inputValidator.processStringInput();
            if (addFriendChoice.equalsIgnoreCase("Y")) {
                if (users.size() > 1) {
                    System.out.println(" - Enter the username whom you want to add as a friend: ");
                    String usernameToAdd = inputValidator.processUsernameInput();
                    User userToAdd = usersData.get(usernameToAdd);
                    if (mainUser.getFriends().contains(userToAdd)) {
                        System.out.println("### Error: You are already friends with " + userToAdd + "!");
                        isValid = false;
                    } else if (users.contains(userToAdd)) {
                        mainUser.addFriend(userToAdd);
                        System.out.println(" - You have successfully added " + userToAdd + " to your friends list!");
                        isValid = true;
                    } else {
                        System.out.println("### Error: Invalid username. Please, try again.");
                        isValid = false;
                    }
                } else {
                    User userToAdd = users.iterator().next();
                    mainUser.addFriend(userToAdd);
                    isValid = true;
                }
            } else if (!addFriendChoice.equalsIgnoreCase("N")) {
                System.out.println("### Error: Invalid choice. Please, try again.");
                isValid = false;
            }
        } while (!isValid);
    }

    /**
     * Method to like the post of another user
     * 
     * @param inputValidator Object to validate the user's input
     * @param postsList List of posts to choose from
     */
    public void likePost(InputValidator inputValidator, List<Post> postsList) {
        boolean isValid = true;
        do {
            System.out.println("\n - Do you want to like any post of this user? (Y/N)");
            String likeChoice = inputValidator.processStringInput();
            if (likeChoice.equalsIgnoreCase("Y")) {
                System.out.println(">>> Enter the number of the post you want to like");
                int postNumber = inputValidator.processChoiceInput() - 1;
                try {
                    postsList.get(postNumber).addLike();
                    isValid = true;
                    System.out.println("*** You have successfully liked this post! ***");
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

    /**
     * Method to interact with the friends of the main user and run queries on them
     * 
     * @param choice Choice of the user
     * @param username Username of the user to interact with
     * @param inputValidator Object to validate the user's input
     */
    public void manageFriends(int choice, String username, InputValidator inputValidator) {
            User userToInteract = usersData.get(username);
            switch (choice) {
                case 1:
                    if (mainUser.getFriends().contains(userToInteract)) {
                        userToInteract.viewProfile();
                        likePost(inputValidator, userToInteract.getPosts());
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
                case 2:
                    if (mainUser.getFriends().contains(userToInteract)) {
                        mainUser.removeFriend(userToInteract);
                        System.out.println("### You have successfully removed this friend! ###");
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
                case 3:
                    if (mainUser.getFriends().contains(userToInteract)) {
                        userToInteract.viewFriends();
                        Set<User> newFriends = compareFriends(userToInteract);
                        addNewFriend(inputValidator, newFriends);
                    } else {
                        System.out.println("### Error: You are not friends with this user. Please, try again.");
                    }
                    break;
            }
    }


    /**
     * Method to search for the posts by hashtag
     * 
     * @param inputValidator Validator object to validate the input of the user
     */
    public void searchPostByHashtag(InputValidator inputValidator) {
        List<String> hashtags = inputValidator.processHashtagsInput();
        List<Post> posts = filterPosts(hashtags);
        if (posts.size() > 0) {
            System.out.println("*** The following posts have been found: ");
            for (int i = 0; i < posts.size(); i++) {
                System.out.printf("%2d. %s\n", i + 1, posts.get(i));
            }
            System.out.println();
            likePost(inputValidator, posts);
        } else {
            System.out.printf("### Error: No posts found with the hashtag: %s!\n", hashtags);
        }
    }

    /**
     * Method to filter the posts of the users by the given hashtag
     * 
     * @param hashtagList List of hashtags to filter the posts
     * @return List of posts with the given hashtag
     */
    public List<Post> filterPosts(List<String> hashtagList) {
        List<Post> posts = new ArrayList<>();
        for (User user : usersData.values()) {
            for (Post post : user.getPosts()) {
                if (post.getHashtags().containsAll(hashtagList)) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    /**
     * Method to sort friends list by first name and print the list after sorting
     */
    public void sortFriendsListByFirstName() {
        List<User> sortedFriendsList = new ArrayList<>(mainUser.getFriends());
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getFullName().compareTo(user2.getFullName());
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);

    }

    /**
     * Method to sort friends list by last name and print the list after sorting
     */
    public void sortFriendsListByLastName() {
        List<User> sortedFriendsList = new ArrayList<>(mainUser.getFriends());
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getFullName().split(" ")[1].compareTo(user2.getFullName().split(" ")[1]);
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);
    }

    /**
     * Method to sort friends list by number of friends and print the list after
     */
    public void sortFriendsListByNumberOfFriends() {
        List<User> sortedFriendsList = new ArrayList<>(mainUser.getFriends());
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getFriends().size() - user2.getFriends().size();
            }
        });
        System.out.println(" - Sorted friends list: " + sortedFriendsList);

    }

    /**
     * Method to filter friend list by city and print the list after filtering
     * 
     * @param inputValidator Validator object to validate the input of the user
     */
    public void filterFriendsListByCity(InputValidator inputValidator) {
        String city = inputValidator.processStringInput();
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
     * @param inputValidator Validator object to validate the input of the user
     */
    public void filterFriendsListByWorkplace(InputValidator inputValidator) {
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
     * Method to recommend friends to the main user based on common friends, city,
     * and workplace. Strangers will not be recommeneded even if users share same city
     */
    public void recommendFriendsByCityAndWorkplace() {
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
     * Method to calculate the number of common friends between the main user and
     * the given user
     * 
     * @param user User to compare with the main user
     * @return Number of common friends
     */
    public int calculateNumberOfCommonFriends(User user) {
        int numberOfCommonFriends = 0;
        for (User friend : mainUser.getFriends()) {
            if (user.getFriends().contains(friend)) {
                numberOfCommonFriends++;
            }
        }
        return numberOfCommonFriends;
    }

    // /**
    //  * Method to sort the list of friends by number of common friends between them and the main user
    //  * 
    //  * @param friendsList List of friends to sort
    //  */
    // public List<User> sortFriendsListByNumberOfCommonFriends(List<User> friendsList) {
    //     Collections.sort(friendsList, new Comparator<User>() {
    //         @Override
    //         public int compare(User mainUser, User user) {
    //             return mainUser.getFriends().size() - user.getFriends().size();
    //         }
    //     });
    //     return friendsList;
    // }
}
