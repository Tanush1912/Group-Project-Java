import java.util.*;
import java.io.*;

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
    private MainUser mainUser;

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
     * Method to load the social network data from the file
     */
    public void loadNetwork() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String fileName = "network-data.txt";
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            boolean isMainUser = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (line!="\n") {
                    line = bufferedReader.readLine();
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
                    String phoneNumber = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);
                    line = bufferedReader.readLine();
                    String city = line.split(":")[1].substring(2, line.split(":")[1].length() - 1);

                    line = bufferedReader.readLine();
                    List<Post> posts = new ArrayList<>();
                    String postsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    for (String post : postsCollection.split(";")) {
                        String[] postInfo = post.split("/");
                        String content = postInfo[0].substring(1, postInfo[0].length()-1);
                        int likesNumber = Integer.parseInt(postInfo[1]);
                        String date = postInfo[2];
                        Post postObj = new Post(content, likesNumber, date);
                        posts.add(postObj);
                    }

                    line = bufferedReader.readLine();
                    Set<User> friends = new HashSet<>();
                    String friendsCollection = line.split(": \\[")[1].substring(0, line.split(": \\[")[1].length() - 1);
                    for (String friend : friendsCollection.split(", ")) {
                        String friendUsername = friend.substring(1, friend.length()-1);
                        friends.add(usersData.get(friendUsername));
                    }

                    if (isMainUser) {
                        mainUser = new MainUser(username, fullName, bio, email, workplace, phoneNumber, city, posts, friends);
                        isMainUser = false;
                    }
                    User user = new User(username, fullName, bio, email, workplace, phoneNumber, city, posts, friends);
                    usersData.put(username, user);
                    connectionsGraph.addUser(user);
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
}
