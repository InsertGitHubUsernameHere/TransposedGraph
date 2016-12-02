package assign5;

/**
 * Simple assignment 5 testing function - basically just loads in a simple
 * graph and prints both the graph and its transpose.
 * 
 * @author srtate
 */
public class A5Test {
    public static void main(String[] argv) {
        Graph g = Graph.readGraph("test.txt");
        if (g == null) {
            System.out.println("Failure trying to read graph from file.");
            System.exit(1);
        }
        
        System.out.println("Original graph - testing print:");
        g.printGraph();
        
        System.out.println("\n================================\nTranspose:\n");
        g.getTransposedGraph().printGraph();
    }
}
