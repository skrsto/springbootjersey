package com.example.demo.model;

public class SavedToDoItem
{
    private String title;
    private String url;
    private Boolean completed;

    public SavedToDoItem()
    {
    }

    public SavedToDoItem(String title, String url, Boolean completed)
    {
        this.title = title;
        this.url = url;
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

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Boolean getCompleted()
    {
        return completed;
    }

    public void setCompleted(Boolean completed)
    {
        this.completed = completed;
    }

    @Override
    public String toString()
    {
        return "SavedToDoItem{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", completed=" + completed +
                '}';
    }
}
