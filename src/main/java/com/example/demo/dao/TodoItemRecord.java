package com.example.demo.dao;

public class TodoItemRecord
{
    private String title;
    private boolean completed;

    public TodoItemRecord(String title, boolean completed)
    {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }
}
