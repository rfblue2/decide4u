package com.hackprinceton.decide4u.model;

/**
 * Created by sherr on 11/11/2016.
 */

public class Question {
    private String questionText;

    public Question(String s) {
            questionText = s;
        }

    public String toString() {
        return questionText;
    }
}
