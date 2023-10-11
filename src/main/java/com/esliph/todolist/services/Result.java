package com.esliph.todolist.services;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class Result<T> {
    private boolean ok;
    @Getter
    private HttpStatus status;
    @Getter
    private T value;
    @Getter
    private ErrorResult error;

    private Result(boolean ok, HttpStatus status, T value, ErrorResult error) {
        this.ok = ok;
        this.status = status;
        this.value = value;
        this.error = error;
    }

    private Result(boolean ok, HttpStatus status, T value) {
        this.ok = ok;
        this.status = status;
        this.value = value;
        this.error = null;
    }

    private Result(boolean ok, HttpStatus status, ErrorResult error) {
        this.ok = ok;
        this.status = status;
        this.value = null;
        this.error = error;
    }

    public static <T> Result<T> success(T value, HttpStatus status) {
        return new Result<T>(true, status, value);
    }

    public static <T> Result<T> success(T value) {
        return new Result<T>(true, HttpStatus.OK, value);
    }

    public static <T> Result<T> failure(ErrorResult error, HttpStatus status) {
        return new Result<T>(true, status, error);
    }

    public static <T> Result<T> failure(ErrorResult error) {
        return new Result<T>(true, HttpStatus.BAD_REQUEST, error);
    }

    public boolean isSuccess() {
        return ok;
    }

    public Object getResponse() {
        return this.value != null ? this.value : this.error;
    }
}
