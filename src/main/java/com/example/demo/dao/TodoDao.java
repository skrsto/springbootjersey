package com.example.demo.dao;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TodoDao
{

    public static Map<String, TodoItemRecord> savedToDoItemMap = new ConcurrentHashMap<>();

    public String add(TodoItemRecord todoItemRecord)
    {
        String uuid = UUID.randomUUID().toString();
        savedToDoItemMap.put(uuid, todoItemRecord);
        return uuid;
    }

    public TodoItemRecord get(String uuid)
    {
        return savedToDoItemMap.get(uuid);
    }

    public TodoItemRecord setStatus(String uuid, boolean completed)
    {
        TodoItemRecord storedSavedToDoItem = savedToDoItemMap.get(uuid);
        if (storedSavedToDoItem != null)
            storedSavedToDoItem.setCompleted(completed);
        return storedSavedToDoItem;
    }

    public boolean delete(String uuid)
    {
        return (savedToDoItemMap.remove(uuid) != null);
    }

    public Map<String, TodoItemRecord> getAll()
    {
        return savedToDoItemMap;
    }
}
