package com.example.BasicClasses;

public class student extends user{

    public student(){
        super();
    }

    public String getStatement(String choice){
        //Switch
        switch (choice){
            case "solve_test":
                return "SELECT * FROM questions";
            case "submit_answers":
                return "UPDATE test_history SET grade = ?, date = ? WHERE test_id = ?";
            case "get_tests":
                return "SELECT * FROM test_history WHERE username=? AND grade=''";
            default:
                return "";
        }
    }

}
