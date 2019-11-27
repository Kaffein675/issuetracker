package com.axmor.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Issue {
    private int issueId;
    private String title;
    private String description;
    private Date publishingDate;
    private String status;
    private String author;
}
