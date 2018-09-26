package com.turvo.algorithm;

import java.util.List;

public interface CommuteRangeAlgorithm<T extends Entity> {
    List<T> getReachableEntities(int startItemId, double rangeLimit);
}
