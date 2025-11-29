package com.example.hyperativa_back_end.dtos.beanio;

import lombok.Data;

@Data
public class HeaderRecord {
    private String name;
    private String date;
    private String batch;
    private String recordCount;
}
