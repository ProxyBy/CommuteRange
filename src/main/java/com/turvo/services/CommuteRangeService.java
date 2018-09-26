package com.turvo.services;

import com.turvo.algorithm.CommuteRangeAlgorithm;
import com.turvo.algorithm.dijkstra.DijkstraGraph;
import com.turvo.algorithm.Entity;
import com.turvo.entity.Route;
import com.turvo.repository.CityRepository;
import com.turvo.repository.RouteRepository;
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
