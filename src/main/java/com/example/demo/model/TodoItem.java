package com.example.demo.model;

public class TodoItem
{
    private String title;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "TodoItem{" +
                "title='" + title + '\'' +
                '}';
    }
}
