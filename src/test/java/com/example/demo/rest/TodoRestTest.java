package com.example.demo.rest;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.SavedToDoItem;
import com.example.demo.model.TodoItem;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class TodoRestTest extends JerseyTest
{

    @Override
    protected Application configure()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        return new JerseyResourceConfig().property("contextConfig", context);
    }

    @Test
    public void getAll()
    {

    }

    @Test
    public void get()
    {
    }

    @Test
    public void post() throws IOException
    {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Unload the dishwasher");

        Entity<TodoItem> request = Entity.entity(todoItem, MediaType.APPLICATION_JSON);

        Response response = target("/todo").request().post(request, Response.class);
        Assert.assertEquals(201, response.getStatus());
        String jsonString = response.readEntity(String.class);
        SavedToDoItem savedToDoItem = new ObjectMapper().readValue(jsonString, SavedToDoItem.class);
        Assert.assertFalse(savedToDoItem.getCompleted());
        Assert.assertEquals("Unload the dishwasher", savedToDoItem.getTitle());
    }

    @Test
    public void setCompletedFlag()
    {
    }

    @Test
    public void delete()
    {
    }
}