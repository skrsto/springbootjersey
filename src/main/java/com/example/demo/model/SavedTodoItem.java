package com.example.demo.model;

public class SavedTodoItem
{
    private String title;
    private String url;
    private Boolean completed;

    public SavedTodoItem()
    {
    }

    public SavedTodoItem(String title, String url, Boolean completed)
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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedTodoItem that = (SavedTodoItem) o;

        if (!getTitle().equals(that.getTitle())) return false;
        if (!getUrl().equals(that.getUrl())) return false;
        return getCompleted().equals(that.getCompleted());
    }

    @Override
    public int hashCode()
    {
        int result = getTitle().hashCode();
        result = 31 * result + getUrl().hashCode();
        result = 31 * result + getCompleted().hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "SavedTodoItem{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", completed=" + completed +
                '}';
    }
}
