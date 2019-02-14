package com.axmor.db.model;

public class Status_model {
    private int status_id;
    private String status;
    private int issue_id;

    public int getStatus_id(){
        return this.status_id;
    }
    public String getStatus(){
        return this.status;
    }
    public int getIssue_id(){
        return this.issue_id;
    }
    public int setStatus_id(int id){
        return this.status_id = id;
    }
    public String setStatus(String status){
        return this.status = status;
    }
    public int setIssue_id(int id){
        return this.issue_id = id;
    }
}
