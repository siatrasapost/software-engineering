package com.example.BasicClasses;

public class teacher extends user {

    public teacher(){}

    protected String getStatement(String choice){
        switch (choice){
            case "create_exercise":
                return "INSERT INTO questions VALUES (?,?,?,?)";
            case "check_grades":
                return  "SELECT * FROM grades WHERE teachername = ?";
            default:
                return "";

                //Testing_Github
                //Working in Siatras gtxs
                //Axillea gamiesaiiiiiiiiiiiiii
        }
    }

}
