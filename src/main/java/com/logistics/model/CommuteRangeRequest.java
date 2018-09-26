package com.logistics.model;

import lombok.Data;

@Data
public class CommuteRangeRequest {
    int startItemId;
    double rangeLimit;
}
