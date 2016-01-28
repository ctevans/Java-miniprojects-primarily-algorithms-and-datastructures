import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adjacency list created through use of dictionaries and arrays.
 *
 * Utilized the singleton pattern here.
 */
public class AdjList {
    //The primary structure for this class.
    //Singleton pattern! :)
    private static final HashMap<String, ArrayList<String>> instance = new HashMap<String, ArrayList<String>>();


    /**
     * Inserts into the AdjList a node. Does not include edges.
     * @param node The node to be placed into the AdjList.
     */
    public void addNode(String node){
        instance.put(node, new ArrayList<String>());

    }

    /**
     * Adds an edge for a particular node into the AdjList.
     * @param node1 The node at the start of the edge.
     * @param node2 The node that the first node has an edge pointing to.
     */
    public void addEdge(String node1, String node2){
        instance.get(node1).add(node2);
    }

    /**
     * Classical returning of the instance of the adjList.
     * @return The Dictionary that IS the adjacency list!
     */
    public HashMap<String,ArrayList<String>> getInstance(){
        return instance;
    }

}
