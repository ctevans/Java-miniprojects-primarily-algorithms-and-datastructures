import java.util.ArrayList;

/**
 * An Adjacency Matrix implementation using booleans.
 *
 * If the node, i, has an edge going to node j then there will be a "true" at the index [i][j] of the matrix.
 *
 * Inserting and deleting nodes is a simple flip of the boolean. O(1)
 * Checking if a particular edge [i][j] exist is in constant time. O(1)
 *
 * However checking the entire matrix for values is extremely costly O(n^2).
 * Memory use is also extreme here, at O(n^2).
 *
 */
public class AdjMatrix {
    private boolean[][] adjMatrix;
    int sizeOfMatrix;

    /**
     * Simple constructor, takes in only the value of how large the matrix is to be.
     * Such as a 5x5 matrix will be created if the constructor is passed in the parameter 5.
     *
     * @param sizeOfMatrix The size of the matrix to be produced. Eg: 5 will produce a 5x5 matrix.
     */
    AdjMatrix(int sizeOfMatrix){
        this.sizeOfMatrix = sizeOfMatrix;

        adjMatrix = new boolean[sizeOfMatrix][sizeOfMatrix];

    }

    /**
     * Checks to see if a given edge[i][j] exists, where the edge is i -> j.
     * @param i The first node (where the edge begins).
     * @param j The second node (where the edge ends).
     * @return A boolean stating if the edge exists (true) or if it does not exist (false).
     */
    public boolean hasEdge(int i, int j){
        return adjMatrix[i][j];

    }

    /**
     * Adds an edge from i -> j into the AdjMatrix.
     * @param i The first node (where the edge begins).
     * @param j The second node (where the edge ends).
     */
    public void addEdge(int i, int j){
        adjMatrix[i][j] = true;
    }

    /**
     * Removes an edge from i -> j into the AdjMatrix.
     * @param i The first node (where the edge begins).
     * @param j The second node (where the edge ends).
     */
    public void removeEdge(int i, int j){
        adjMatrix[i][j] =false;
    }


    /**
     * Compiles a list of all of the outgoing edges, given a particular node.
     * @param i A node where the outgoing edges are desired to be known.
     * @return A list of outgoing edges for a particular node.
     */

    public ArrayList<Integer> outgoingEdges(int i){
        ArrayList<Integer> outgoingEdgesList = new ArrayList<Integer>();

        for(int j = 0; j < sizeOfMatrix; j++){
            if(adjMatrix[i][j]){
                outgoingEdgesList.add(j);
            }
        }
        return outgoingEdgesList;
    }

    /**
     * Compiles a list of all of the incoming edges, given a particular node.
     * @param i A node where the incoming edges are desired to be known.
     * @return A list of incoming edges for a particular node.
     */
    public ArrayList<Integer> incomingEdges(int i){
        ArrayList<Integer> incomingEdgesList = new ArrayList<Integer>();

        for(int j = 0; j < sizeOfMatrix; j++){
            if (adjMatrix[i][j] == true){
                incomingEdgesList.add(i);
            }
        }

        return incomingEdgesList;
    }


}



