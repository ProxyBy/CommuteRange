package com.turvo.algorithm.dijkstra;

import com.turvo.Utils.NodeDistanceComparator;
import com.turvo.algorithm.CommuteRangeAlgorithm;
import com.turvo.algorithm.Entity;
import com.turvo.entity.Route;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
public class DijkstraGraph implements CommuteRangeAlgorithm {
    private Map<Integer, Node> nodes;

    private Edge routeToEdgeConvert(Route route) {
        return new Edge(route.getStartPoint(), route.getEndPoint(), route.getDistance());
    }

    private void addEgeToConnectedNodes(Edge edgeToAdd) {
        nodes.get(edgeToAdd.getFromNodeIndex()).getEdges().add(edgeToAdd);
        nodes.get(edgeToAdd.getToNodeIndex()).getEdges().add(edgeToAdd);
    }

    public DijkstraGraph(List<Route> routes, List<Entity> entities) {
        nodes = new HashMap<>();
        if (routes.size() != 0 && entities.size() != 0) {
            nodes = entities.stream().collect(Collectors.toMap(Entity::getId, Node::new));
            //adding every edge to nodes, which we have as fromNodeIndex and toNodeIndex
            routes.stream().map(this::routeToEdgeConvert).forEach(this::addEgeToConnectedNodes);
        }
    }

    private List<Node> calculateDistances(int startItemId, double rangeLimit) {
        if (nodes.get(startItemId) == null) {
            log.error("There is no startItemId = {} in system", startItemId);
            throw new IllegalArgumentException("Not existed node");
        }

        List<Node> visitedNodes = new ArrayList<>();
        Queue<Node> distanceCalculatedNodes = new PriorityQueue<>(10, new NodeDistanceComparator());

        Map<Integer, Node> processedNodes = nodes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    Node newNode = new Node(e.getValue().getEntity());
                    newNode.setEdges(new ArrayList<>(e.getValue().getEdges()));
                    return newNode;
                }));

        int nextNode = startItemId;
        processedNodes.get(nextNode).setDistanceFromSource(0);
        processedNodes.get(nextNode).setVisited(true);
        while(true) {
            Node node = processedNodes.get(nextNode);
            node.getEdges().stream().filter((edge) -> edge.getFromNodeIndex() == node.getEntity().getId()).forEach(edge -> {
                        if (!processedNodes.get(edge.getToNodeIndex()).isVisited()) {
                            double tentative = node.getDistanceFromSource() + edge.getLength();

                            if (tentative < processedNodes.get(edge.getToNodeIndex()).getDistanceFromSource()
                                    && tentative <= rangeLimit) {
                                processedNodes.get(edge.getToNodeIndex()).setDistanceFromSource(tentative);
                                distanceCalculatedNodes.add(processedNodes.get(edge.getToNodeIndex()));
                            }
                        }
                    }
            );

            //all neighbours checked so node visited
            if (!node.isVisited()) {
                node.setVisited(true);
                visitedNodes.add(node);
            }
            //next node must be with shortest distance
            if (!distanceCalculatedNodes.isEmpty()) {
                nextNode = distanceCalculatedNodes.poll().getEntity().getId();
            } else return visitedNodes;
        }
    }

    @Override
    public List<Entity> getReachableEntities(int startItemId, double rangeLimit) {
        return calculateDistances(startItemId, rangeLimit).stream().map(Node::getEntity).collect(Collectors.toList());
    }
}
