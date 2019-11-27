package com.axmor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    int commentId;
    int postId;
    String author;
    String content;
    Date submissionDate;
}
