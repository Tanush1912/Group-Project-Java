import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Class responsible for the functionality of the social network
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
    private final static String FILE_NAME = "network-data.txt";

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
            Map<String, Set<String>> friendsMap = new HashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String username = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String password = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
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
                    Set<String> interests = new HashSet<>();
                    String interestsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    if (!interestsCollection.isEmpty()) {
                        for (String interest : interestsCollection.split(", ")) {
                            String interestName = interest.substring(1, interest.length()-1);
                            interests.add(interestName);
                        }
                    }

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
                    User user = new User(username, password, fullName, bio, email, workplace, city, phoneNumber, posts, interests);
                    usersData.put(username, user);
                }
            }

            for (String user : friendsMap.keySet()) {
                for (String friend : friendsMap.get(user)) {
                    User userToAdd = usersData.get(friend);
                    usersData.get(user).addFriend(userToAdd);
                }
            }
        } catch (IOException e) {
            System.out.println("### Error: Unable to load the network data!");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("### Error: Unable to close the network data file!");
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

            for (User user : usersData.values()) {
                writeToFile(writer, user);
            }
            System.out.println("*** Network data has been saved successfully! ***");
        } catch (IOException e) {
            System.out.println("### Error: Unable to save the network data!");
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
        writer.printf("\"Password\": \"%s\"\n", user.getPassword());
        writer.printf("\"Full name\": \"%s\"\n", user.getFullName());
        writer.printf("\"Email\": \"%s\"\n", user.getEmail());
        writer.printf("\"Bio\": \"%s\"\n", user.getBio());
        writer.printf("\"Workplace\": \"%s\"\n", user.getWorkplace());
        writer.printf("\"City\": \"%s\"\n", user.getCity());
        writer.printf("\"Phone number\": \"%s\"\n", user.getPhoneNumber());

        String interests = "";
        for (String interest : user.getInterests()) {
            interests += "\"" + interest + "\", ";
        }
        if (!interests.isEmpty()) {
            interests = interests.substring(0, interests.length() - 2);
        }
        writer.printf("\"Interests\": [%s]\n", interests);

        String posts = "";
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Post post : user.getPosts()) {
            posts += String.format("\"%s\"/%d/%s/%s; ", post.getContent(), post.getNumberOfLikes(), formatter.format(post.getDate()), post.getHashtags());
        }
        if (!posts.isEmpty()) {
            posts = posts.substring(0, posts.length() - 2);
        }
        writer.printf("\"Posts\": [%s]\n", posts);

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
     * Method to allow the main user to edit their profile
     * 
     * @param choice The choice of the user on what to edit
     */
    public void editProfile(int choice, String input) {
        User userToUpdate = usersData.get(mainUser.getUsername());
        switch (choice) {
            case 1:
                userToUpdate.setPassword(input);
                break;
            case 2:
                userToUpdate.setFullName(input);
                break;
            case 3:
                userToUpdate.setEmail(input);
                break;
            case 4:
                userToUpdate.setBio(input);
                break;
            case 5:
                userToUpdate.setWorkplace(input);
                break;
            case 6:
                userToUpdate.setCity(input);
                break;
            case 7:
                userToUpdate.setPhoneNumber(input);
                break;
            case 8:
                Set<String> newInterests = generateInterestsFromString(input);
                userToUpdate.setInterests(newInterests);
                break;
        }

        usersData.put(userToUpdate.getUsername(), userToUpdate);
        mainUser = new MainUser(userToUpdate);
    }

    /**
     * Method to generate a set of interests from a string entered by user
     * 
     * @param interests String of interests entered by user
     * @return Set of interests
     */
    public Set<String> generateInterestsFromString(String interests) {
        Set<String> interestsSet = new HashSet<>();
        try {
            for (String interest : interests.split(" ")) {
                interestsSet.add(interest);
            }
        } catch (Exception e) {
            System.out.println("### Error: Interests must be separated by space!");
        }

        return interestsSet;
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
        System.out.println("\n*** Friends you have in common: ");
        if (commonFriends.isEmpty()) {
            System.out.println(" -> No common friends!");
        } else {
            for (User friend : commonFriends) {
                System.out.println(" -> " + friend.getUsername());
            }
        }

        System.out.println("\n*** People you may want to add to your friends: ");
        if (unknownFriends.isEmpty()) {
            System.out.println(" -> No unknown friends!");
        } else {
            for (User friend : unknownFriends) {
                System.out.printf(" -> %s (%d common friends)\n", friend.getUsername(), calculateNumberOfCommonFriends(friend));
            }
        }
        return unknownFriends;
    }

    /**
     * Method to manage the logic of adding a friend
     * 
     * @param inputValidator Object to validate the user's input
     * @param users Collection of users to choose from
     */
    public void addNewFriend(InputValidator inputValidator, Set<User> users) {
        boolean isValid = true;
        do {
            if (users.size() > 1) {
                System.out.println("\n -> Do you want to add anyone to your friend list? (Y/N)");
            } else {
                System.out.printf("\n -> Do you want to add %s to your friend list? (Y/N)\n", users.iterator().next());
            }
            String addFriendChoice = inputValidator.processStringInput();
            if (addFriendChoice.equalsIgnoreCase("Y")) {
                if (users.size() > 1) {
                    System.out.println(" -> Please, enter the username of whom you want to add as a friend: ");
                    String usernameToAdd = inputValidator.processUsernameInput();
                    if (!usernameToAdd.trim().isEmpty()) {
                        User userToAdd = usersData.get(usernameToAdd);
                        if (mainUser.getFriends().contains(userToAdd)) {
                            System.out.println("### Error: You are already friends with " + userToAdd + "!");
                            isValid = false;
                        } else if (users.contains(userToAdd)) {
                            mainUser.addFriend(userToAdd);
                            userToAdd.addFriend(usersData.get(mainUser.getUsername()));
                            System.out.printf("*** You have successfully added %s (%s) to your friends list! ***\n", userToAdd, userToAdd.getFullName());
                            isValid = true;
                        } else {
                            System.out.println("### Error: Invalid username. Please, try again.");
                            isValid = false;
                        }
                    }
                } else {
                    User userToAdd = users.iterator().next();
                    mainUser.addFriend(userToAdd);
                    userToAdd.addFriend(usersData.get(mainUser.getUsername()));
                    System.out.printf("*** You have successfully added %s (%s) to your friends list! ***\n", userToAdd, userToAdd.getFullName());
                    isValid = true;
                }
            } else if (!addFriendChoice.equalsIgnoreCase("N") && !addFriendChoice.trim().isEmpty()) {
                System.out.println("### Error: Invalid choice. Please, try again.");
                isValid = false;
            } else {
                isValid = true;
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
            System.out.println(" -> Do you want to like any post? (Y/N)");
            String likeChoice = inputValidator.processStringInput();
            if (likeChoice.equalsIgnoreCase("Y")) {
                System.out.println("\n -> Please, enter the number of the post you want to like: ");
                System.out.println("\n -> Press \"Enter\" to skip.");
                int inputChoice = inputValidator.processChoiceInput(postsList.size());
                if (inputChoice != -1) {
                    int postNumber = inputChoice - 1;
                    try {
                        postsList.get(postNumber).addLike();
                        isValid = true;
                        System.out.println("*** You have successfully liked this post! ***");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("### Error: This post does not exist! Please, try again.");
                        isValid = false;
                    }
                }
            } else if (!likeChoice.equalsIgnoreCase("N") && !likeChoice.trim().isEmpty()) {
                System.out.println("### Error: Invalid choice. Please, try again.");
                isValid = false;
            } else {
                isValid = true;
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
                        userToInteract.removeFriend(usersData.get(mainUser.getUsername()));
                        System.out.printf("*** You have successfully removed %s (%s) from your friend list! ***\n", userToInteract, userToInteract.getFullName());
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
                        System.out.printf("### Error: You are not friends with %s. Please, try again.\n", userToInteract);
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
        if (hashtags.size() == 0) {
            System.out.println("### Error: No hashtags have been entered!");
        } else {
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
     *  Method to search for a user by username and interact with the found user
     * 
     * @param inputValidator Object to validate the input of the main user
     */
    public void searchUserByUsername(InputValidator inputValidator) {
        System.out.println("\n -> Please, enter the username of the user you want to search: ");
        String username = inputValidator.processUsernameInput();
        if (username.trim().isEmpty()) {
            return;
        }

        if (usersData.containsKey(username)) {
            User user = usersData.get(username);
            System.out.printf("*** The user %s (%s) has been found! ***\n", user, user.getFullName());
            System.out.println("\n -> Do you want to interact with this user? (Y/N)");
            String choice = inputValidator.processStringInput();
            if (choice.equalsIgnoreCase("Y")) {
                int inputChoice;
                do {
                    System.out.printf("\n -> Please, select the option you want to perform with %s: \n", user);
                    System.out.println(" - 1. View Profile");
                    System.out.println(" - 2. View Friends");
                    if (!mainUser.getFriends().contains(user) && !mainUser.getUsername().equals(username)) {
                        System.out.println(" - 3. Add to Friends");
                    }
                    System.out.println(" - 0. Back to Main Menu");
                    int maxChoice = (mainUser.getFriends().contains(user) || mainUser.getUsername().equals(username)) ? 2 : 3;
                    inputChoice = inputValidator.processChoiceInput(maxChoice);
                    
                    switch (inputChoice) {
                        case 1:
                            user.viewProfile();
                            likePost(inputValidator, user.getPosts());
                            break;
                        case 2:
                            user.viewFriends();
                            if (!mainUser.getUsername().equals(username)) {
                                Set<User> newFriends = compareFriends(user);
                                addNewFriend(inputValidator, newFriends);
                            }
                            break;
                        case 3:
                            mainUser.addFriend(user);
                            user.addFriend(usersData.get(mainUser.getUsername()));
                            System.out.printf("*** You have successfully added %s (%s) to your friend list! ***\n", user, user.getFullName());
                    }
                } while (inputChoice != 0);
            } else if (!choice.equalsIgnoreCase("N") && !choice.trim().isEmpty()) {
                System.out.println("### Error: Invalid choice. Please, try again.");
            }
        } else {
            System.out.printf("### Error: The user with username %s does not exist! Please, try again.\n", username);
        }
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

        System.out.println("\n*** Sorted by the first name ***");
        for (int i = 0; i < sortedFriendsList.size(); i++) {
            User friend = sortedFriendsList.get(i);
            System.out.printf("%2d. %s (%s)\n", i + 1, friend, friend.getFullName());
        }
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

        System.out.println("\n*** Sorted by the last name ***");
        for (int i = 0; i < sortedFriendsList.size(); i++) {
            User friend = sortedFriendsList.get(i);
            System.out.printf("%2d. %s (%s)\n", i + 1, friend, friend.getFullName());
        }    
    }

    /**
     * Method to sort friends list by total number of friends and print the list after
     */
    public void sortFriendsListByTotalNumberOfFriends() {
        List<User> sortedFriendsList = new ArrayList<>(mainUser.getFriends());
        Collections.sort(sortedFriendsList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user2.getFriends().size() - user1.getFriends().size();
            }
        });

        System.out.println("\n*** Sorted by the total number of friends ***");
        for (int i = 0; i < sortedFriendsList.size(); i++) {
            User friend = sortedFriendsList.get(i);
            System.out.printf("%2d. %s has %d friends\n", i + 1, friend, friend.getFriends().size());
        }
    }

    /**
     * Method to filter friend list by city and print the list after filtering
     * 
     * @param inputValidator Validator object to validate the input of the user
     */
    public void filterFriendsListByCity(InputValidator inputValidator) {
        System.out.println("\n -> Please, enter the city you want to filter: ");
        String city = inputValidator.processStringInput();
        if (city.trim().isEmpty()) {
            return;
        }

        List<User> filteredFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            if (friend.getCity().equals(city)) {
                filteredFriendsList.add(friend);
            }
        }

        filteredFriendsList = sortByNumberOfCommonFriends(filteredFriendsList);
        if (filteredFriendsList.size() > 0) {
            System.out.printf("\n*** Friends who live in %s ***\n", city);
            for (int i = 0; i < filteredFriendsList.size(); i++) {
                User friend = filteredFriendsList.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, friend, calculateNumberOfCommonFriends(friend));
            }
        } else {
            System.out.printf("\n*** Unfortunately, no friends have been found living in %s ***\n", city);
        }
    }

    /**
     * Method to filter friend list by workplace and print the list after filtering
     * 
     * @param inputValidator Validator object to validate the input of the user
     */
    public void filterFriendsListByWorkplace(InputValidator inputValidator) {
        System.out.println("\n -> Please, enter the workplace you want to filter: ");
        String workplace = inputValidator.processStringInput();
        if (workplace.trim().isEmpty()) {
            return;
        }

        List<User> filteredFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            if (friend.getWorkplace().equals(workplace)) {
                filteredFriendsList.add(friend);
            }
        }

        filteredFriendsList = sortByNumberOfCommonFriends(filteredFriendsList);
        if (filteredFriendsList.size() > 0) {
            System.out.printf("\n*** Friends who work in %s ***\n", workplace);
            for (int i = 0; i < filteredFriendsList.size(); i++) {
                User friend = filteredFriendsList.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, friend, calculateNumberOfCommonFriends(friend));
            }
        } else {
            System.out.printf("\n*** Unfortunately, no friends have been found working in %s ***\n", workplace);
        }
    }

    /**
     * Method to filter friend list by interests and print the list after filtering
     * 
     * @param inputValidator Validator object to validate the input of the user
     */
    public void filterFriendsListByInterests(InputValidator inputValidator) {
        System.out.println("\n -> Please, enter the interests you want to filter: ");
        List<String> interests = inputValidator.processInterestsInput();
        if (interests.size() == 0) {
            return;
        }

        List<User> filteredFriendsList = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            for (String interest : interests) {
                if (friend.getInterests().contains(interest)) {
                    filteredFriendsList.add(friend);
                    break;
                }
            }
        }

        filteredFriendsList = sortByNumberOfCommonFriends(filteredFriendsList);
        if (filteredFriendsList.size() > 0) {
            System.out.printf("\n*** Friends who are interested in %s ***\n", interests);
            for (int i = 0; i < filteredFriendsList.size(); i++) {
                User friend = filteredFriendsList.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, friend, calculateNumberOfCommonFriends(friend));
            }
        } else {
            System.out.printf("\n*** Unfortunately, no friends have been found having %s as interests ***\n", interests);
        }
    }

    /**
     * Method to get the recommended friends list
     * 
     * @return List of recommended friends
     */
    public List<User> getRecommendedFriendsList() {
        List<User> recommendedFriends = new ArrayList<>();
        for (User friend : mainUser.getFriends()) {
            for (User friendOfFriend : friend.getFriends()) {
                if (friendOfFriend != null && !mainUser.getFriends().contains(friendOfFriend)
                        && !friendOfFriend.getUsername().equals(mainUser.getUsername())
                        && !recommendedFriends.contains(friendOfFriend)) {
                    recommendedFriends.add(friendOfFriend);
                }
            }
        }
        return recommendedFriends;
    }

    /**
     * Method to recommend friends to the main user based on common friends
     */
    public void recommendFriends() {
        List<User> recommendedFriends = getRecommendedFriendsList();
        if (!recommendedFriends.isEmpty()) {
            recommendedFriends = sortByNumberOfCommonFriends(recommendedFriends);
            System.out.println("\n*** People you may know ***");
            for (int i = 0; i < recommendedFriends.size(); i++) {
                User recommendedFriend = recommendedFriends.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, recommendedFriend, calculateNumberOfCommonFriends(recommendedFriend));
            }
        } else {
            System.out.println("\n*** No recommended friends yet ***");
        }
    }

    /**
     * Method to recommend friends to the main user based on common friends and city 
     * Strangers will not be recommended even if users share same city
     */
    public void recommendFriendsByCity() {
        List<User> recommendedFriends = getRecommendedFriendsList();
        List<User> recommendedFriendsWithSameCity = new ArrayList<>();
        for (User recommendedFriend : recommendedFriends) {
            if (recommendedFriend.getCity().equals(mainUser.getCity())) {
                recommendedFriendsWithSameCity.add(recommendedFriend);
            }
        }

        if (recommendedFriendsWithSameCity.isEmpty()) {
            System.out.printf("\n*** No recommended friends who live in %s ***\n", mainUser.getCity());
        } else {
            System.out.printf("\n*** People who also live in %s ***\n", mainUser.getCity());
            for (int i = 0; i < recommendedFriendsWithSameCity.size(); i++) {
                User recommendedFriend = recommendedFriendsWithSameCity.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, recommendedFriend, calculateNumberOfCommonFriends(recommendedFriend));
            }
        }
    }

    /**
     * Method to recommend friends to the main user based on common friends and workplace. 
     * Strangers will not be recommended even if users share same workplace
     */
    public void recommendFriendsByWorkplace() {
        List<User> recommendedFriends = getRecommendedFriendsList();
        List<User> recommendedFriendsWithSameWorkplace = new ArrayList<>();
        for (User recommendedFriend : recommendedFriends) {
            if (recommendedFriend.getWorkplace().equals(mainUser.getWorkplace())) {
                recommendedFriendsWithSameWorkplace.add(recommendedFriend);
            }
        }

        if (recommendedFriendsWithSameWorkplace.isEmpty()) {
            System.out.printf("\n*** No recommended friends who work in %s ***\n", mainUser.getWorkplace());
        } else {
            System.out.printf("\n*** People who also work in %s ***\n", mainUser.getWorkplace());
            for (int i = 0; i < recommendedFriendsWithSameWorkplace.size(); i++) {
                User recommendedFriend = recommendedFriendsWithSameWorkplace.get(i);
                System.out.printf("%2d. %s (%d common friends)\n", i + 1, recommendedFriend,
                        calculateNumberOfCommonFriends(recommendedFriend));
            }
        }
    }

    /**
     * Method to recommend friends to the main user based on common friends and interests. 
     * Strangers will not be recommended even if users share same interests
     */ 
    public void recommendFriendsByInterests() {
        List<User> recommendedFriends = getRecommendedFriendsList();
        List<User> recommendedFriendsWithSameInterests = new ArrayList<>();
        Set<String> commonInterests = new HashSet<>();
        for (User recommendedFriend : recommendedFriends) {
            for (String interest : recommendedFriend.getInterests()) {
                if (mainUser.getInterests().contains(interest)) {
                    commonInterests.add(interest);
                    if (!recommendedFriendsWithSameInterests.contains(recommendedFriend)) {
                        recommendedFriendsWithSameInterests.add(recommendedFriend);
                    }
                }
            }
        }

        if (recommendedFriendsWithSameInterests.isEmpty()) {
            System.out.printf("\n*** No recommended friends who have the same interests ***\n");
        } else {
            System.out.printf("\n*** People who share the same interests ***\n");
            for (int i = 0; i < recommendedFriendsWithSameInterests.size(); i++) {
                User recommendedFriend = recommendedFriendsWithSameInterests.get(i);
                System.out.printf("%2d. %s (%d common friends) %s\n", i + 1, recommendedFriend,
                        calculateNumberOfCommonFriends(recommendedFriend), commonInterests);
            }
        }
    }

    /**
     * Method to calculate the number of common friends between the main user and the given user
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

    /**
     * Method to sort the list of friends by number of common friends between them and the main user
     * 
     * @param friendsList List of friends to sort
     * @return Sorted list of friends by number of common friends
     */
    public List<User> sortByNumberOfCommonFriends(List<User> friendsList) {
        Collections.sort(friendsList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return calculateNumberOfCommonFriends(user2) - calculateNumberOfCommonFriends(user1);
            }
        });
        return friendsList;
    }

    /**
     * Method to validate the user's credentials
     * 
     * @param username Username of the user
     * @param password Password of the user
     * @return Boolean representing whether the user's credentials are valid or not
     */
    public boolean validateUser(String username, String password) {
        if (usersData.containsKey(username)) {
            User user = usersData.get(username);
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to allow the user to sign in
     */
    public void signIn(InputValidator inputValidator) {
        System.out.println("\n -> Please, enter the username: ");
        String username = inputValidator.processUsernameInput();
        System.out.println("\n -> Please, enter the password: ");
        String password = inputValidator.processStringInput();
        if (validateUser(username, password)) {
            User user = usersData.get(username);
            mainUser = new MainUser(user);
            System.out.printf("\n*** Welcome, %s! You have successfully signed in! ***\n", mainUser.getUsername());
        } else {
            System.out.println("\n### Error: Incorrect username or password! Please, try again.");
        }
    }

    /**
     * Method to allow the user to sign out
     */
    public void signOut() {
        mainUser = null;
        saveNetwork();
        System.out.println("*** You have successfully signed out! ***");
    }
}
