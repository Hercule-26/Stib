package stib.model;


import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Graph {
    private final Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        this.nodes.add(nodeA);
    }
    public Node getNode(Integer idStation) {
        for(Node node : nodes) {
            if (Objects.equals(node.getIdStation(), idStation)) return node;
        }
        return null;
    }

    public Set<Node> getNodes() {
        return this.nodes;
    }
    public void cleanGraph() {
        for (Node node : nodes) {
            node.clearPath();
            node.setDistance(Integer.MAX_VALUE);
        }
    }
}
