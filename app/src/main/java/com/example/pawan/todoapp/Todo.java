package com.example.pawan.todoapp;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Pawan on 09-09-2017.
 */

public class Todo implements Serializable {
    private Long id;
    private String todo;
    private String Details;
    private Long time;
    public Todo(String todo, String Details, Long id,Long time){
        this.id=id;
        this.todo=todo;
        this.Details = Details;
        this.time=time;
    }

    public Long getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return Details;
    }

    public String getTodo() {
        return todo;
    }

    public void setDetails(String details) {
        this.Details = details;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
