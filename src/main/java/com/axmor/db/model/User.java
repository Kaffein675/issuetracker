package com.axmor.db.model;

public class User {
    private int user_id;
    private String user_name;
    private String salt;
    private String hash_pass;

    public int getUser_id(){
        return this.user_id;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public String getSalt(){
        return this.salt;
    }
    public String getHash_pass(){
        return this.hash_pass;
    }
    public int setUser_id(int user_id){
        return this.user_id = user_id;
    }
    public String setUser_name(String user_name){
        return this.user_name = user_name;
    }
    public String setSalt(String salt){
        return this.salt = salt;
    }
    public String setHash_pass(String hash_pass){
        return this.hash_pass = hash_pass;
    }
}
