package com.logistics.entity;

import com.logistics.algorithm.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;


@javax.persistence.Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Entity {
    @Id
    int id;
    String name;
}
