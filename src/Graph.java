import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class to represent the graph of users and their friends
 */
public class Graph {
    /**
     * Graph of users and their friends
     */
    private Map<User, Set<User>> graph;

    /**
     * Default constructor to initiate the graph
     */
    public Graph() {
        graph = new HashMap<>();
    }

    // /**
    //  * Method to get the graph
    //  * @return Graph of users and their friends
    //  */
    // public Map<User, Set<User>> getGraph() {
    //     return graph;
    // }

    /**
     * Add a user to the graph
     * @param user User to be added
     * @param friends Set of friends of the user
     */
    public void addUser(User user) {
        graph.put(user, user.getFriends());
    }

    // /**
    //  * Display the list of common friends with the user selected by username
    //  * 
    //  * @param username Username of the user whose common friends are to be displayed
    //  */
    // public void displayCommonFriends(String username) {
    //     Set<User> friends = graph.get(username);
    //     for (User friend : friends) {
    //         if (friends.contains(...)) {  // list of main user's friends
    //             System.out.println(friend.getUsername());
    //         }
    //     }
    // }
}
