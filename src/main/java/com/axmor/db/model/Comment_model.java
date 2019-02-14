package com.axmor.db.model;

import java.sql.Date;


public class Comment_model {
    int comment_id;
    int post_id;
    String author;
    String content;
    Date submission_date;

    public int setId(int id){
        return this.comment_id = id;
    }
    public int setPost_id(int post_id){
        return this.post_id = post_id;
    }
    public String setAuthor(String author){
        return this.author = author;
    }
    public String setContent(String content){
        return this.content = content;
    }
    public Date setDate(Date date){
        return this.submission_date = date;
    }
    public int getId(){
        return this.comment_id;
    }
    public int getPost_id(){
        return this.post_id;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getContent(){
        return this.content;
    }
    public Date getDate(){
        return this.submission_date;
    }
}
