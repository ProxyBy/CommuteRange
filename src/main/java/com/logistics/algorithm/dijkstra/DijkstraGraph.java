package com.logistics.algorithm.dijkstra;

import com.logistics.algorithm.CommuteRangeAlgorithm;
import com.logistics.algorithm.Entity;
import com.logistics.entity.Route;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private int getNodeShortestDistanced(Map<Integer, Node> processedNodes, double rangeLimit) {
        int storedNodeIndex = -1;
        double storedDist = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Node> entry : processedNodes.entrySet()) {
            double currentDist = entry.getValue().getDistanceFromSource();
            if (!entry.getValue().isVisited() && currentDist < storedDist && currentDist <= rangeLimit) {
                storedDist = currentDist;
                storedNodeIndex = entry.getKey();
            }
        }
        return storedNodeIndex;
    }

    private List<Node> calculateDistances(int startItemId, double rangeLimit) {
        if (nodes.get(startItemId) == null) {
            log.error("There is no startItemId = {} in system", startItemId);
            throw new IllegalArgumentException("Not existed node");
        }

        List<Node> visitedNodes = new ArrayList<>();

        Map<Integer, Node> processedNodes = nodes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    Node newNode = new Node(e.getValue().getEntity());
                    newNode.setEdges(new ArrayList<>(e.getValue().getEdges()));
                    return newNode;
                }));

        int nextNode = startItemId;
        processedNodes.get(nextNode).setDistanceFromSource(0);
        while (nextNode >= 0) {
            Node node = processedNodes.get(nextNode);
            node.getEdges().stream().filter((edge) -> edge.getFromNodeIndex() == node.getEntity().getId()).forEach(edge -> {
                        if (!processedNodes.get(edge.getToNodeIndex()).isVisited()) {
                            double tentative = node.getDistanceFromSource() + edge.getLength();

                            if (tentative < processedNodes.get(edge.getToNodeIndex()).getDistanceFromSource()) {
                                processedNodes.get(edge.getToNodeIndex()).setDistanceFromSource(tentative);
                            }
                        }
                    }
            );

            //all neighbours checked so node visited
            node.setVisited(true);
            if (nextNode != startItemId) {
                visitedNodes.add(node);
            }
            //next node must be with shortest distance
            nextNode = getNodeShortestDistanced(processedNodes, rangeLimit);
        }
        return visitedNodes;
    }

    @Override
    public List<Entity> getReachableEntities(int startItemId, double rangeLimit) {
        return calculateDistances(startItemId, rangeLimit).stream().map(Node::getEntity).collect(Collectors.toList());
    }
}
