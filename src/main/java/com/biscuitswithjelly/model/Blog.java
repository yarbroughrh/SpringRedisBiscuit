package com.biscuitswithjelly.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Blog implements Serializable {

    private Long id;
    private String title;
    private String content;
    // getters and setters
}