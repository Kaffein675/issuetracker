package com.axmor.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Issue {
    int issueId;
    String title;
    String description;
    Date publishingDate;
    String status;
    String author;
}
