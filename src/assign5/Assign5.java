/*
 * Code for Assignment 5 in UNCG's CSC 330 (Fall 2016).
 */
package assign5;

import static assign5.Graph.readGraph;

/**
 * This is just a basic testing class, calling read/print/transpose methods
 * from the Graph class.
 * 
 * @author srtate
 */
public class Assign5 {

    /**
     * A main routine that drives the graph algorithms for Assignment 5.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Graph g = readGraph("cscclasses.txt");
        if (g == null) {
            System.out.println("Failure trying to read graph from file.");
            System.exit(1);
        }
        
        System.out.println("File read...");
        System.out.println(g.getNumVertices() + " vertices");

        // To test just the graph printing function, use the first line. To
        // test the transpose method as well, use just the second line.
        
        g.printGraph();
//        g.getTransposedGraph().printGraph();
    }

}
