package com.turvo.Utils;

import com.turvo.algorithm.dijkstra.Node;

import java.util.Comparator;

public class NodeDistanceComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return (int)(o1.getDistanceFromSource() - o2.getDistanceFromSource());
    }
}

