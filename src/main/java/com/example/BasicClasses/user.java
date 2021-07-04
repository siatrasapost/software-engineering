package com.example.BasicClasses;

public abstract class user {
    private String username, role;

    public user(){
        username="";
        role="";
    }

    public void login(String usr, String usr_role){
        username= usr;
        role=usr_role;
    }

    public void logout(){

    }

    protected String getStatement(){
        return "";
    }

    public String getUsername(){
        return username;
    }

}
