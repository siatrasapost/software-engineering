package com.example.BasicClasses;

public class teacher extends user {

    public teacher(){super();}

    public String getStatement(String choice){
        switch (choice){
            case "create_exercise":
                return "INSERT INTO questions VALUES (?,?,?,?,?)";
            case "check_grades":
                return  "SELECT * FROM test_history WHERE teachername = ?";
            case "get_tests":
                return "SELECT * FROM test_history WHERE teachersname = ? ORDER BY date DESC";
            case "get_students":
                return "SELECT username, firstname, lastname, age FROM users WHERE typeacc = '1' AND age = ?";
            case "assign_test":
                return "INSERT INTO test_history(username, grade, date, teachersname, questions, difficulty) VALUES (?,?,?,?,?,?)";
            case "delete_test":
                return "DELETE FROM test_history WHERE test_id= ? ";
            case "get_recent_tests":
                return "SELECT * FROM test_history WHERE grade!='' AND teachersname=? AND date<? AND date>?";
            default:
                return "";
        }
    }

}
