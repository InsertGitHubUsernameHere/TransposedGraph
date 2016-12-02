/**
 * Code for Assignment 5 in UNCG's CSC 330 (Fall 2016).
 * 
 * This is a modified version of the Graph implementation from the Weiss
 * textbook. Since Assignment 5 doesn't need any shortest path implementations,
 * I took all of those (and supporting classes) out to reduce clutter and
 * simplify things. There are just stubs in place for the printGraph() and
 * getTransposedGraph() methods - you are to fill these in!
 */

package assign5;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

// Represents an edge in the graph.
class Edge {

    public Vertex dest;   // Second vertex in Edge
    public double cost;   // Edge cost

    public Edge(Vertex d, double c) {
        dest = d;
        cost = c;
    }
}

// Represents an entry in the priority queue for Dijkstra's algorithm.
class Path implements Comparable<Path> {

    public Vertex dest;   // w
    public double cost;   // d(w)

    public Path(Vertex d, double c) {
        dest = d;
        cost = c;
    }

    public int compareTo(Path rhs) {
        double otherCost = rhs.cost;

        return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
    }
}

// Represents a vertex in the graph.
class Vertex {

    public String name;   // Vertex name
    public List<Edge> adj;    // Adjacent vertices
    public double dist;   // Cost
    public Vertex prev;   // Previous vertex on shortest path
    public int scratch;// Extra variable used in algorithm

    public Vertex(String nm) {
        name = nm;
        adj = new LinkedList<Edge>();
        reset();
    }

    public void reset() {
        dist = Graph.INFINITY;
        prev = null;
        scratch = 0;
    }
}

// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w, double cvw )
//                              --> Add additional edge
// void printPath( String w )   --> Print path after alg is run
// void unweighted( String s )  --> Single-source unweighted
// void dijkstra( String s )    --> Single-source weighted
// void negative( String s )    --> Single-source negative weighted
// void acyclic( String s )     --> Single-source acyclic
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class Graph {
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
   
    /**
     * If vertexName is not present, add it to vertexMap. In either case, return
     * the Vertex.
     */
    private Vertex getVertex(String vertexName) {
        Vertex v = vertexMap.get(vertexName);
        if (v == null) {
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }

    /**
     * Add a new edge to the graph.
     */
    public void addEdge(String sourceName, String destName, double cost) {
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        v.adj.add(new Edge(w, cost));
    }

    /**
     * Get the number of vertices in this graph.
     * @return the number of vertices
     */
    public int getNumVertices() {
        return vertexMap.size();
    }
    
    /**
     * printGraph prints the graph to standard output in a readable format.
     */
    public void printGraph() {
        for (Vertex v : vertexMap.values()) {
            System.out.print(v.name+" -- ");
            
            if (v.adj.size() == 0) {
                System.out.println("no edges out");
            } else if (v.adj.size() == 1) {
                System.out.println("edge out to:");
            } else {
                System.out.println("edges out to:");
            }
            
            for (Edge e : v.adj) {
                System.out.println("    "+e.dest.name);
            }            
        }
    }

    /**
     * getTransposed() graph creates the transpose of the graph and returns
     * it as a new graph. The transpose contains all of the vertices of the
     * original graph, but edges are in the opposite direction (all other
     * properties, such as edge cost, are preserved).
     * @return the transposed graph
     */
    public Graph getTransposedGraph() {
        Graph result = new Graph();
        for (Vertex v : vertexMap.values()) {
            result.getVertex(v.name);
            for (Edge e : v.adj)
                result.addEdge(e.dest.name, v.name, e.cost);
        }
        
        return result;
    }

    /**
     * Method to read a graph from a file - basically extracted from the "main"
     * method in Weiss's code.
     * 
     * @param fileName - name of the file to read
     * @return the graph, or null if there was an error reading (no file, etc.)
     */
    public static Graph readGraph(String fileName) {
        Graph g = new Graph();

        try {
            FileReader fin = new FileReader(fileName);
            Scanner graphFile = new Scanner(fin);

            // Read the edges and insert
            String line;
            while (graphFile.hasNextLine()) {
                line = graphFile.nextLine();
                StringTokenizer st = new StringTokenizer(line);

                try {
                    if (st.countTokens() != 3) {
                        System.err.println("Skipping ill-formatted line " + line);
                        continue;
                    }
                    String source = st.nextToken();
                    String dest = st.nextToken();
                    int cost = Integer.parseInt(st.nextToken());
                    g.addEdge(source, dest, cost);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping ill-formatted line " + line);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }

        return g;
    }

}
