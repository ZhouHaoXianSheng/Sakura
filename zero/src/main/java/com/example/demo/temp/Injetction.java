package com.example.demo.temp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Mr.Zhou
 */
@Data
public class Injetction {
    public Injetction(String name) {
        this.name = name;
    }

    private String name;
}
