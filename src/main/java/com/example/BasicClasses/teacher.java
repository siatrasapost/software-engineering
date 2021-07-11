package com.example.BasicClasses;

public class teacher extends user {

    public teacher(){super();}

    public String getStatement(String choice){
        switch (choice){
            case "create_exercise":
                return "INSERT INTO questions VALUES (?,?,?,?)";
            case "check_grades":
                return  "SELECT * FROM test_history WHERE teachername = ?";
            case "get_tests":
                return "SELECT * FROM test_history WHERE teachersname = ?";
            default:
                return "";
        }
    }

}
