package com.example.cnpm.entity.enums;

public enum Placement {
    WHITE(1, "white"),
    YELLOW(2, "yellow"),
    GREEN(3, "green"),
    BLUE(4, "blue"),
    RED(5, "red"),
    BLACK(6, "black");


    private Integer code;
    private String name;

    Placement(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
