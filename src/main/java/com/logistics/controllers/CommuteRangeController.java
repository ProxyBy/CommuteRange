package com.logistics.controllers;

import com.logistics.model.CommuteRangeRequest;
import com.logistics.services.CommuteRangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommuteRangeController {
    private CommuteRangeService commuteRangeService;

    @ResponseBody
    @PostMapping("/city")
    public ResponseEntity commuteRangeCalculation(@RequestBody CommuteRangeRequest commuteRangeRequest) {
        return ResponseEntity.ok(commuteRangeService.determineReachableCity(commuteRangeRequest.getStartItemId(), commuteRangeRequest.getRangeLimit()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> error(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
