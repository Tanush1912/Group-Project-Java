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

    /**
     * Add a user to the graph
     * 
     * @param user    User to be added
     * @param friends Set of friends of the user
     */
    public void addUser(User user) {
        graph.put(user, user.getFriends());
    }
}
