package com.pustovalov.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {

    public static final String DEFAULT_VALUE = "0";

    private final PointUnits type;

    private final String value;

    public Point(String value, PointUnits type) {
        this.value = value;
        this.type = type;
    }

    public Point(PointUnits type) {
        this.value = DEFAULT_VALUE;
        this.type = type;
    }
}
