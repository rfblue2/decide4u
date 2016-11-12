package com.hackprinceton.decide4u.model;


import java.io.Serializable;

/**
 * Created by sherr on 11/11/2016.
 */

public class Question implements Serializable {
    private String question;
    private String opt1;
    private String opt2;
    private String details;

    public Question(String question, String opt1, String opt2, String details) {
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.details = details;
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

    public String toString() {
        return question;
    }
}
