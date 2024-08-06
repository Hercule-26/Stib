package stib.model;

import stib.dto.PairStationDto;
import stib.dto.StationDto;
import stib.dto.StopDto;
import stib.exception.RepositoryException;
import stib.repository.FavoriteRepository;
import stib.repository.StopRepository;
import stib.util.Observable;
import stib.util.Observer;

import java.util.*;

public class Stib implements Observable {
    private final List<Observer> observers;
    private final Graph graph;
    private Node destination;
    private final FavoriteRepository favorite;
    private final List<PairStationDto> favoriteList;

    public Stib() throws RepositoryException {
        this.favorite = new FavoriteRepository();
        this.favoriteList = new ArrayList<>();
        observers = new LinkedList<>();
        StopRepository stopRepository = new StopRepository();
        List<StopDto> list = stopRepository.getAll();
        graph = new Graph();
        initializeGraph(list);
    }

    public void searchWay(Integer idBeginStation, Integer idEndStation) {
        graph.cleanGraph();
        Node source = graph.getNode(idBeginStation);
        Graph resultGraph = DijkstraAlgorithm.calculateShortestPathFromSource(graph, source);
        destination = resultGraph.getNode(idEndStation);
        notifyObservers();
    }
    public List<Node> getPath() {
        List <Node> list = new ArrayList<>();
        if (this.destination != null) {
            list = destination.getShortestPath();
            if (!list.isEmpty()) list.add(destination);
        }
        return list;
    }
    public void deleteFavorite(Integer key) {
        favorite.delete(key);
        for (int i = 0; i < favoriteList.size(); i++) {
            if (Objects.equals(favoriteList.get(i).getKey(), key)) {
                favoriteList.remove(i);
                break;
            }
        }
        notifyObservers();
    }
    public void addFavorite(StationDto begin, StationDto end) {
        int key = favorite.add(begin, end);
        PairStationDto newFavorite = new PairStationDto(key,
                                                begin.getKey(), begin.getStationName(),
                                                end.getKey(), end.getStationName());
        favoriteList.add(newFavorite);
        notifyObservers();
    }
    public void setFavorite() throws RepositoryException {
        this.favoriteList.addAll(favorite.getAll());
        notifyObservers();
    }
    public List<PairStationDto> getFavorites() {
        return this.favoriteList;
    }
    private void notifyObservers() {
        for (Observer obs : observers) obs.update();
    }
    @Override
    public void register(Observer obs) {
        this.observers.add(obs);
    }
    @Override
    public void unregister(Observer obs) {
        this.observers.remove(obs);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private void initializeGraph(List<StopDto> stops) {
        for (StopDto stop : stops) {
            Node graphStation = graph.getNode(stop.getStationId());
            if (graphStation == null) {
                Node station = new Node(stop.getStationName(), stop.getStationId());
                station.setLine(stop.getLineId());
                graph.addNode(station);
            } else {
                graphStation.setLine(stop.getLineId());
            }
        }

        for (int i = 0; i < stops.size() - 1; i++) {
            StopDto currentStop = stops.get(i);
            StopDto nextStop = stops.get(i + 1);

            if (Objects.equals(currentStop.getLineId(), nextStop.getLineId())) {
                Node currentNode = graph.getNode(currentStop.getStationId());
                Node nextNode = graph.getNode(nextStop.getStationId());

                if (currentNode != null && nextNode != null) {
                    currentNode.addDestination(nextNode, 1);
                    nextNode.addDestination(currentNode, 1);
                }
            }
        }

        // Lier Simonis à Elisabeth
        // distance = 0 car les stations sont l'un sur l'autre
        graph.getNode(8764).addDestination(graph.getNode(8472), 0);
        graph.getNode(8472).addDestination(graph.getNode(8764), 0);
    }
}