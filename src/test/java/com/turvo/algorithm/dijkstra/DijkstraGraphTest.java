package com.turvo.algorithm.dijkstra;

import com.turvo.algorithm.Entity;
import com.turvo.entity.City;
import com.turvo.entity.Route;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DijkstraGraphTest {
    private DijkstraGraph dijkstraGraph;

    @Before
    public void setUp() {
        List<Route> routes = new ArrayList<>();
        List<Entity> cities = new ArrayList<>();

        routes.add(new Route(0,0, 2, 4));
        routes.add(new Route(1,0, 4, 3));
        routes.add(new Route(2,0, 6, 6));

        routes.add(new Route(3,2, 5, 5));
        routes.add(new Route(4,2, 0, 4));
        routes.add(new Route(5,2, 4, 6));

        routes.add(new Route(6,4, 0, 3));
        routes.add(new Route(7,4, 2, 6));
        routes.add(new Route(8,4, 5, 11));
        routes.add(new Route(9,4, 6, 8));

        routes.add(new Route(10,5, 4, 11));
        routes.add(new Route(11,5, 2, 5));
        routes.add(new Route(12,5, 12, 10));
        routes.add(new Route(13,5, 6, 2));

        routes.add(new Route(14,6, 4, 8));
        routes.add(new Route(15,6, 5, 2));
        routes.add(new Route(16,6, 0, 6));
        routes.add(new Route(17,6, 12, 5));

        routes.add(new Route(18,12, 6, 5));
        routes.add(new Route(19,12, 5, 10));

        cities.add(new City(12, "New York"));
        cities.add(new City(0, "Washington"));
        cities.add(new City(2, "Boston"));
        cities.add(new City(4, "Minneapolis"));
        cities.add(new City(5, "Las Vegas"));
        cities.add(new City(6, "Denver"));

        dijkstraGraph = new DijkstraGraph(routes, cities);
    }

    @Test
    public void getReachableEntitiesAll() {
        List<Entity> results = dijkstraGraph.getReachableEntities(0, 1000);
        assertEquals(5, results.size());
    }

    @Test
    public void getReachableEntities() {
        List<Entity> results = dijkstraGraph.getReachableEntities(0, 8);
        assertEquals(4, results.size());
        assertEquals(4, results.get(0).getId());
        assertEquals(2, results.get(1).getId());
        assertEquals(6, results.get(2).getId());
        assertEquals(5, results.get(3).getId());

    }
}
