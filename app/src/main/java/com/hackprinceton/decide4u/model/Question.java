package com.hackprinceton.decide4u.model;


import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by sherr on 11/11/2016.
 */

public class Question implements Serializable {

    long id;
    String question = "", opt1 = "", opt2 = "", details = "", username = "";
    int opt1Votes, opt2Votes;

    public Question(String question, String opt1, String opt2, String details, String username) {
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.details = details;
        this.username = username;

        opt1Votes = 0;
        opt2Votes = 0;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOpt1() {
        return opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public String getDetails() {
        return details;
    }

    public String getUsername() {
        return username;
    }

    public int getOpt1Votes() { return opt1Votes; }

    public int getOpt2Votes() { return opt2Votes; }

    public String toString() {
        return (new Gson()).toJson(this);
    }
}
