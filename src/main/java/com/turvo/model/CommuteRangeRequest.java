package com.turvo.model;

import lombok.Data;

@Data
public class CommuteRangeRequest {
    int startItemId;
    double rangeLimit;
}
