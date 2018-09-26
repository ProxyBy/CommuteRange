package com.logistics.algorithm.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    private int fromNodeIndex;
    private int toNodeIndex;
    private double length;
}

