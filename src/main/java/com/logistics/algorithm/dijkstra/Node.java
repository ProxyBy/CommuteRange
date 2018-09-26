package com.logistics.algorithm.dijkstra;

import com.logistics.algorithm.Entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Node {
    private double distanceFromSource = Double.MAX_VALUE;
    private boolean visited;
    private List<Edge> edges;
    private Entity entity;

    public Node(Entity entity) {
        edges = new ArrayList<>();
        this.entity = entity;
    }
}
