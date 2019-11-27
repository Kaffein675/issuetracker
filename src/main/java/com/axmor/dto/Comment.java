package com.axmor.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Comment {
    int commentId;
    int postId;
    String author;
    String content;
    Date submissionDate;
}
