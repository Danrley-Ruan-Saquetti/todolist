package com.esliph.todolist.services;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class ErrorResult {
    private String title;
    private String message;
    private String description;
    private String stack;
    private ArrayList<String> causes;

    public ErrorResult(String title, String message, String description, String cause, String stack) {
        this.title = title;
        this.message = message;
        this.description = description;
        this.causes = new ArrayList<>();
        this.causes.add(cause);
        this.stack = stack;
    }

    public ErrorResult(String title, String message, String description, ArrayList<String> causes, String stack) {
        this.title = title;
        this.message = message;
        this.description = description;
        this.causes = causes;
        this.stack = stack;
    }

    public ErrorResult(String title, String message, String description) {
        this.title = title;
        this.message = message;
        this.description = description;
        this.causes = new ArrayList<>();
        this.stack = "";
    }

    public ErrorResult(String title, String message) {
        this.title = title;
        this.message = message;
        this.description = "";
        this.causes = new ArrayList<>();
        this.stack = "";
    }

    public ErrorResult(String message) {
        this.title = "Error";
        this.message = message;
        this.description = "";
        this.causes = new ArrayList<>();
        this.stack = "";
    }

    public ErrorResult() {
        this.title = "Error";
        this.message = "";
        this.description = "";
        this.causes = new ArrayList<>();
        this.stack = "";
    }

    public ErrorResult title(String title) {
        this.title = title;

        return this;
    }

    public ErrorResult message(String message) {
        this.message = message;

        return this;
    }

    public ErrorResult stack(String stack) {
        this.stack = stack;

        return this;
    }

    public ErrorResult causes(ArrayList<String> causes) {
        this.causes = causes;

        return this;
    }

    public ErrorResult causes(String cause) {
        this.causes.add(cause);

        return this;
    }

    public ErrorResult description(String description) {
        this.description = description;

        return this;
    }

    public String getCausesJoin() {
        var causesInString = this.causes.toString();

        return causesInString.substring(1, causesInString.length() - 1);
    }

    public String toString() {
        return ""
                + this.title + ": " + this.message + "\n"
                + this.description + "\n"
                + this.getCausesJoin() + "\n"
                + this.stack + "\n"
                + "";
    }
}
