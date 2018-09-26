package com.logistics.services;

import com.logistics.algorithm.CommuteRangeAlgorithm;
import com.logistics.algorithm.dijkstra.DijkstraGraph;
import com.logistics.algorithm.Entity;
import com.logistics.entity.Route;
import com.logistics.repository.CityRepository;
import com.logistics.repository.RouteRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommuteRangeService {
    private CommuteRangeAlgorithm commuteRangeAlgorithm;

    public CommuteRangeService(CityRepository cityRepository, RouteRepository routeRepository) {
        List<Route> routes = new ArrayList<>();
        List<Entity> cities = new ArrayList<>();
        cityRepository.findAll().forEach(cities::add);
        routeRepository.findAll().forEach(routes::add);
        commuteRangeAlgorithm = new DijkstraGraph(routes, cities);
    }

    public List determineReachableCity(int startItemId, double rangeLimit) {
        return commuteRangeAlgorithm.getReachableEntities(startItemId, rangeLimit);
    }
}
