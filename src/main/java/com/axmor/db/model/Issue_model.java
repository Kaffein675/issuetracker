package com.axmor.db.model;


import java.sql.Date;

public class Issue_model {
    private int issue_id;
    private String title;
    private String description;
    private Date publishing_date;
    private String status;
    private String author;

    public int setId(int id){
        return this.issue_id = id;
    }
    public String setTitle(String title){
        return this.title = title;
    }
    public String setDescription(String description){
        return this.description = description;
    }
    public Date setDate(Date publishing_date){
        return this.publishing_date = publishing_date;
    }
    public String setStatus(String status){
        return this.status = status;
    }
    public String setAuthor(String author){
        return this.author = author;
    }
    public int getId(){
        return this.issue_id;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getDate(){
        return this.publishing_date;
    }
    public String getStatus(){
        return this.status;
    }
    public String getAuthor(){
        return this.author;
    }
}
