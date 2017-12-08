package map.pathfinder;

import java.util.ArrayList;

/**
 *
 * @author Ben Michalowicz
 */
public class Graph {

    private final Node[] nodes;
    private final int noOfNodes;
    private final Edge[] edges;
    private final int noOfEdges;

    public Graph(Edge[] edges) {
        this.edges = edges;
        // create all nodes ready to be updated with the edges
        this.noOfNodes = calculateNoOfNodes(edges);
        this.nodes = new Node[this.noOfNodes];
        for (int n = 0; n < this.noOfNodes; n++) {
            this.nodes[n] = new Node();
        }
        // add all the edges to the nodes, each edge added to two nodes (to and from)
        this.noOfEdges = edges.length;
        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            this.nodes[edges[edgeToAdd].getFromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }
    }

    private int calculateNoOfNodes(Edge[] edges) {
        int numNodes = 0;
        for (Edge e : edges) {
            if (e.getToNodeIndex() > numNodes) {
                numNodes = e.getToNodeIndex();
            }
            if (e.getFromNodeIndex() > numNodes) {
                numNodes = e.getFromNodeIndex();
            }
        }
        numNodes++;
        return numNodes;
    }
    

    public void calculateShortestDistances() {
        // node 0 as source
        this.nodes[0].setDistanceFromSource(0);
        int nextNode = 0;
        // visit every node
        for (Node node : this.nodes) {
            // loop around the edges of current node
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();
            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);
                // only if not visited
                if (!this.nodes[neighbourIndex].isVisited()) {
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();
                    if (tentative < nodes[neighbourIndex].getDistanceFromSource()) {
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                    }
                }
            }
            // all neighbours checked so node visited
            nodes[nextNode].setVisited(true);
            // next node must be with shortest distance
            nextNode = getNodeShortestDistanced();
        }
    }
    

    private int getNodeShortestDistanced() {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;
        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getDistanceFromSource();
            if (!this.nodes[i].isVisited() && currentDist < storedDist) {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }
        return storedNodeIndex;
    }
    // display result

    public void printResult() {
        String output = "Number of nodes = " + this.noOfNodes;
        output += "\nNumber of edges = " + this.noOfEdges;
        for (int i = 0; i <this.nodes.length; i++) {
            if(nodes[i].getDistanceFromSource()==Integer.MAX_VALUE){
                continue;
            }
            output += ("\nThe shortest distance from node 0 to node " + i + " is " + nodes[i].getDistanceFromSource());
        }
        System.out.println(output);
    }

    public Node[] getNodes() {
        return nodes;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }
}
