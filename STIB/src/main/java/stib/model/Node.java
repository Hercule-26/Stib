package stib.model;

import java.util.*;

public class Node {
    private final String name;
    private final Integer idStation;
    private final Set<Integer> lines = new HashSet<>();
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private final Map<Node, Integer> adjacentNodes = new HashMap<>();
    public Node(String name, Integer idStation) {
        this.name = name;
        this.idStation = idStation;
    }

    public List<Node> getShortestPath() {
        return new ArrayList<Node>(shortestPath);
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addDestination(Node destination, int distance) {
        boolean contain = false;
        for (Map.Entry<Node, Integer> entry : adjacentNodes.entrySet()) {
            if (Objects.equals(entry.getKey().idStation, destination.getIdStation())) {
                contain = true;
                break;
            }
        }
        if (!contain) {
            adjacentNodes.put(destination, distance);
        }
    }

    protected void clearPath() {
        shortestPath.clear();
    }
    public String getName() {
        return name;
    }
    public Integer getIdStation() {return idStation;}
    public Set<Integer> getLines() {return lines;}
    public String getLinesToString() {
        return lines.toString();
    }
    public void setLine(Integer line) {
        this.lines.add(line);
    }
}
