package com.example.demo.rest;

import com.example.demo.model.SavedTodoItem;
import com.example.demo.model.TodoItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

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
    public void getAllIsSuccessful()
    {
        // we make sure there is at least one item in the database
        postTodoItem("Shopping");
        List<SavedTodoItem> savedTodoItemList = target("/todo").request().get(List.class);
        Assert.assertTrue(savedTodoItemList.size() >= 1);
    }

    @Test
    public void getRetrievesExistingTodoItemFromDb() throws IOException
    {
        //create new to do item and get url
        SavedTodoItem savedTodoItemPost = new ObjectMapper().readValue(
                postTodoItem("Wash car").readEntity(String.class), SavedTodoItem.class);

        Response getResponse = target(savedTodoItemPost.getUrl()).request().get(Response.class);
        Assert.assertEquals(200, getResponse.getStatus());

        SavedTodoItem savedTodoItemFromGet = new ObjectMapper().readValue(
                getResponse.readEntity(String.class), SavedTodoItem.class);
        Assert.assertEquals(savedTodoItemPost, savedTodoItemFromGet);
    }

    @Test
    public void getReturns404forNotExistingTodoItem() throws IOException
    {
        Response getResponse = target("/todo/12345").request().get(Response.class);
        Assert.assertEquals(404, getResponse.getStatus());
    }

    @Test
    public void postCreatesNewTodoItem() throws IOException
    {
        Response response = postTodoItem("Unload the dishwasher");
        Assert.assertEquals(201, response.getStatus());
        String jsonString = response.readEntity(String.class);
        SavedTodoItem savedToDoItem = new ObjectMapper().readValue(jsonString, SavedTodoItem.class);
        Assert.assertFalse(savedToDoItem.getCompleted());
        Assert.assertEquals("Unload the dishwasher", savedToDoItem.getTitle());
    }

    // helper function to creat new to do item, used by many tests
    private Response postTodoItem(String todoTitle)
    {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle(todoTitle);

        Entity<TodoItem> entity = Entity.entity(todoItem, MediaType.APPLICATION_JSON);

        return target("/todo").request().post(entity, Response.class);
    }

    @Test
    public void putSetsCompletedFlagOnExistingTodoItem() throws IOException
    {
        //create new to do item and get url
        SavedTodoItem savedTodoItemPost = new ObjectMapper().readValue(
                postTodoItem("Clean garage").readEntity(String.class), SavedTodoItem.class);


        Entity<Boolean> putEntity = Entity.entity(Boolean.TRUE, MediaType.APPLICATION_JSON);

        Response putResponse = target(savedTodoItemPost.getUrl() + "/completed").request().put(putEntity, Response.class);
        Assert.assertEquals(200, putResponse.getStatus());
        SavedTodoItem savedToDoItem = new ObjectMapper().readValue(putResponse.readEntity(String.class), SavedTodoItem.class);
        Assert.assertTrue(savedToDoItem.getCompleted());
    }

    @Test
    public void putOnNotExistingTodoReturnsStatus404()
    {
        Entity<Boolean> putEntity = Entity.entity(Boolean.TRUE, MediaType.APPLICATION_JSON);
        Response putResponse = target("/todo/1234/completed").request().put(putEntity, Response.class);
        Assert.assertEquals(404, putResponse.getStatus());
    }

    @Test
    public void deleteRemovesExistingTodoAndReturns204() throws IOException
    {
        //create new to do item and get url
        SavedTodoItem savedTodoItemPost = new ObjectMapper().readValue(
                postTodoItem("Load dishwasher").readEntity(String.class), SavedTodoItem.class);

        Response deleteResponse = target(savedTodoItemPost.getUrl()).request().delete(Response.class);
        Assert.assertEquals(204, deleteResponse.getStatus());
    }

    @Test
    public void deleteReturns404OnNonExistingItem() throws IOException
    {
        //create new to do item and get url
        SavedTodoItem savedTodoItemPost = new ObjectMapper().readValue(
                postTodoItem("Load dishwasher").readEntity(String.class), SavedTodoItem.class);


        // delete
        Response deleteResponse = target(savedTodoItemPost.getUrl()).request().delete(Response.class);

        // repeat the delete again on the same to do item
        deleteResponse = target(savedTodoItemPost.getUrl()).request().delete(Response.class);
        Assert.assertEquals(404, deleteResponse.getStatus());
    }
}