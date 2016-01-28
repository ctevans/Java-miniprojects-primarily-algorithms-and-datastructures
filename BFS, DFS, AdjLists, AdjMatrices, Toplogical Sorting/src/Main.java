import javafx.util.Pair;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;

/**
 * Graph algorithm main function!
 */
public class Main {

    public static void main(String[] args) {

        //List<?> newList = new ArrayList<>();
        //List newList = new ArrayList<String>();
        //newList.add("Stringy");
        //System.out.println(newList.get(0));

        //Adjacency List representation through a dictionary. Allows fast O(1) lookup time.
        //Map<String,ArrayList<String>> adjList = new HashMap<String,ArrayList<String>>();

        AdjList adjList = new AdjList();


        //Adding values for Edmonton
        adjList.addNode("Edmonton");

        adjList.addEdge("Edmonton", "Vancouver");
        adjList.addEdge("Edmonton", "Newfoundland");
        adjList.addEdge("Edmonton", "Labrador");

        //Original conception before I went OO.
        // adjList.get("Edmonton").add("Vancouver");
        // adjList.get("Edmonton").add("Newfoundland");
        // adjList.get("Edmonton").add("Labrador");


        //Adding values for Vancouver
        adjList.addNode("Vancouver");
        adjList.addEdge("Vancouver", "Labrador");
        adjList.addEdge("Vancouver", "France");

            //Adding values for Vancouver
        adjList.addNode("France");
        adjList.addEdge("France", "Spain");
        adjList.addEdge("France", "Brazil");


        //Need to inesrt ALL nodes else there will be errors. Forget one? Prepare for null pointer exception!

        adjList.addNode("Newfoundland");
        adjList.addNode("Spain");
        adjList.addNode("Brazil");
        adjList.addNode("Labrador");

        //System.out.println(adjList.keySet() +" And Values " +  adjList.values());


        /** Commenting this out for now
         //Print out all edges. O(|V| + |E|) time.
         for (String listIdentifier: adjList.getInstance().keySet()){
         for (String neighbour: adjList.getInstance().get(listIdentifier)){
         System.out.println(listIdentifier + " : " + neighbour);
         }
         }

         **/




        AdjMatrix adjMatrix = new AdjMatrix(5);

        // Goal:
        //    1 -> 2 - > 3 -> 4
        //    3 -> 1 (So a cycle).
        // Cycle is 1 -> 2 -> 3 -> 1.
        //
        //
        adjMatrix.addEdge(1,2);
        adjMatrix.addEdge(2,3);
        adjMatrix.addEdge(3,4);
        adjMatrix.addEdge(3,1);

        //System.out.println(adjMatrix.outgoingEdges(3));


        //DFS here.
        HashMap<String,Boolean> visited = new HashMap<String,Boolean>();

        String startNode = "Edmonton";
        ArrayList<String> goo = new ArrayList<String>();
        dfsFunction(adjList.getInstance(), startNode, visited);

        boolean isItDAG = DAGCheck();

        topologicalSort();

        //reverseGraph(adjList);

        bfsFunction(adjList.getInstance(),startNode);


    }


    //Clock being used during DFS traversal.
    private static int clock = 0;
    private static HashMap<String, Integer> previsitDict = new HashMap<>();
    private static HashMap<String, Integer> postvisitDict = new HashMap<>();

    //For BFS function.
    private static HashMap<String,Integer> distances = new HashMap<>();

    /**
     * BFS. This function is the one called by the main program, it comes with a support function that this one
     * calls. Primarily concerned with the setup of a queue and the distances dictionary.
     *
     * @param adjList Takes in the dictionary component of the adjList object. Use adjList.getInstance() to obtain it.
     * @param startNode The starting point for BFS to run on.
     */
    private static void bfsFunction(Map<String,ArrayList<String>> adjList, String startNode){

        //Initialize the dictionary for each node to be the maximum integer value for now.
        for (String node: adjList.keySet()){
            distances.put(node, Integer.MAX_VALUE);
        }
        System.out.println(distances);

        System.out.println("Starting BFS from: " + startNode);

        Queue<String> queue = new LinkedList<String>();

        queue.add(startNode);
        distances.put(startNode,0);
        System.out.println(distances);
        bfsSupportFunction(adjList, queue);
    }

    /**
     * BFS supportive function. Takes in the queue and dictionary of a graph and runs standard BFS.
     * Overall runtime = O(|V|+|E|).
     *
     * @param adjList Takes in the dictionary component of the adjList supplied by the previous BFS function.
     * @param queue The queue set up by the bfsFunction() will be core to the functioning of BFS.
     */
    private static void bfsSupportFunction(Map<String,ArrayList<String>> adjList, Queue queue){
        //Continue looping until the queue is empty!
        while (queue.size() > 0){
            String node = (String) queue.remove();
            //System.out.println(node);
            int nextDistance = distances.get(node) + 1;
            //System.out.println(nextDistance);
            for(String neighbour: adjList.get(node)){
                if(distances.get(neighbour).equals(Integer.MAX_VALUE)){
                    queue.add(neighbour);
                    distances.put(neighbour,nextDistance);
                }
            }
        }

        //Fancy printout of the BFS results.
        System.out.println("Results of BFS:");
        for(Map.Entry<String,Integer> entry: distances.entrySet()){
            System.out.println(entry.getKey() + " has a distance of " + entry.getValue());
        }
    }

    /**
     *  DFS done in a recursive style, utilizes the natural stack within the computer to process the nodes.
     *  Keeping it simple and clean.
     *  Runtime: O(|V| + |E|)
     *
     * @param adjList Takes in an AdjacencyList's instance of Map<String, ArrayList<String>>. Use getInstance()
     * @param currentNode Start node.
     * @param visited A dictionary used to determine if a node has been visited yet or not by DFS.
     */
    private static void dfsFunction(Map<String, ArrayList<String>> adjList, String currentNode, HashMap<String, Boolean> visited){
        visited.put(currentNode, true); //Keep a record that this current node has been visited. In a dict for fast lookup.
        previsit(currentNode);
        for (String neighbour: adjList.get(currentNode)){
            //System.out.println(neighbour);
            if (!visited.containsKey(neighbour)){

                //System.out.println(neighbour);
                dfsFunction(adjList, neighbour, visited);

            }
        }
        postvisit(currentNode);

    }

    /**
     * Topological sorting of a graph is based upon the post visit numbers collected during the DFS.
     * Higher post visit numbers are indicative of a source in a DAG. Continuously removing the source and
     * appending the value to a new list allows an O(n) topological sorting of the graph. (Assuming we have already
     * obtained the postorder numbers from the DFS of O(|E|+|V|) running time.
     *
     * @return An ArrayList of Strings indicating the graph topologically sorted.
     */
    private static ArrayList<String> topologicalSort(){
        //The new arraylist that will be returned with the topologically sorted nodes.
        ArrayList<String> topologicalSortedGraph = new ArrayList<String>();


        //Until we have gone through all items within the dictionary.
        while(postvisitDict.size() > 0){
            Map.Entry<String, Integer> topEntry = null; //Initialize this as being nothing.

            for(Map.Entry<String,Integer> entry : postvisitDict.entrySet()){
                //System.out.println(entry);
                if (topEntry == null || entry.getValue() > topEntry.getValue()){
                    //System.out.println("The greatest value at present is" + entry.getKey() + " At: " + entry.getValue());
                    topEntry = entry;
                }
            }

            //System.out.println(topEntry);
            postvisitDict.remove(topEntry.getKey());
            //System.out.println(postvisitDict.keySet());
            topologicalSortedGraph.add(topEntry.getKey());
        }


        System.out.println(topologicalSortedGraph);
        return topologicalSortedGraph;

    }

    /**
     * Still needs work done to it. Supposed to take in a graph and reverse it and return the reversed graph.
     * @param originalGraph Takes in a graph
     * @return The reverse of the original graph passed into the function.
     */
    private static AdjList reverseGraph(AdjList originalGraph){

        System.out.println("Begin reversal process!");
        AdjList reverseGraph = new AdjList();
        System.out.println("Original: " + originalGraph.getInstance());

        for (Map.Entry<String,ArrayList<String>> entry: originalGraph.getInstance().entrySet()){

            for(String neighbor: entry.getValue()){
                //System.out.println(neighbor);

                if (!reverseGraph.getInstance().containsKey(neighbor)){
                    reverseGraph.addNode(neighbor);
                }

                if (!reverseGraph.getInstance().containsValue(entry.getKey())){
                    reverseGraph.addEdge(neighbor,entry.getKey());
                }

            }

        }

        System.out.println("Reversed: " +reverseGraph.getInstance());
        return reverseGraph;
    }


    /**
     * Checks if the current graph is a DAG or not. Simply looks for backedges. If there is a backedge then
     * the current graph is NOT a DAG.
     */
    private static boolean DAGCheck(){

        return true;
    }

    //Having fun with pre/post visit functions here:
    //Forward edge = If the pre and post values of a particular vertex are between the pre and post values of a vertex.
    //Back edge = If the pre and post values of the current vertex are between the pre and post values of another vertex
    //Cross edge = If the pre and post values of the current vertex are disjoint from pre and post of another vertex.
    /**
     * Previsit function that is evoked when the DFS initially gets to the node.
     */
    private static void previsit(String node){
        //System.out.println("Previsit!" + node +  clock);
        Main.clock ++;
        previsitDict.put(node, clock);

    }

    /**
     * Postvisit function that is evoked when the DFS is recursively returning through the stack.
     */
    private static void postvisit(String node){
        //System.out.println("Postvisit!" + node + clock);
        Main.clock ++;
        postvisitDict.put(node,clock);

    }
}
