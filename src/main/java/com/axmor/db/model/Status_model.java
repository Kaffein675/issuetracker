package com.axmor.db.model;

public class Status_model {
    private int status_id;
    private String status;

    public int getStatus_id(){
        return this.status_id;
    }
    public String getStatus(){
        return this.status;
    }
    public int setStatus_id(int id){
        return this.status_id = id;
    }
    public String setStatus(String status){
        return this.status = status;
    }
}
