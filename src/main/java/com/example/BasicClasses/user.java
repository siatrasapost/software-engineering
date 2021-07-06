package com.example.BasicClasses;

public abstract class user {
    private String username, password, name, surname, role;
    private String unique_id;

    public user(){
        username="";
        role="";
    }

    public void login(String usr, String pas, String firstname, String lastname, String usr_role){
        username= usr;
        password = pas;
        name = firstname;
        surname = lastname;
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

    public String getPassword(){return password;}

    public String getFullName(){ return name + " " + surname;}

    public void setUniqueID(String id){
        unique_id = id;
    }

    public String getUniqueID(){
        return unique_id;
    }
}
