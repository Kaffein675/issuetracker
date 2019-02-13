package com.axmor.db.model;


import java.sql.Date;
import java.util.List;

public class Issue_model {
    private int issue_id;
    private String title;
    private String discription;
    private Date publishing_date;
    private List<String> status;
}
