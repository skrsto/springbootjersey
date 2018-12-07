package com.example.demo.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.TodoDao;
import com.example.demo.dao.TodoItemRecord;
import com.example.demo.model.SavedToDoItem;
import com.example.demo.model.TodoItem;

@Path(TodoRest.TODO)
public class TodoRest
{

    public static final String TODO = "/todo";

    @Autowired
    private TodoDao todoDao;

    @GET
    @Produces("application/json")
    public List<SavedToDoItem> getAll()
    {
        return todoDao.getAll()
                .entrySet()
                .parallelStream()
                .map(p -> new SavedToDoItem(p.getValue().getTitle(), getUrl(p.getKey()), p.getValue().isCompleted()))
                .collect(Collectors.toList());
    }

    private String getUrl(String key)
    {
        return TODO + "/" + key;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") String id)
    {
        TodoItemRecord todoItemRecord = todoDao.get(id);
        if (todoItemRecord == null)
        {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        }
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(new SavedToDoItem(todoItemRecord.getTitle(), getUrl(id), todoItemRecord.isCompleted()))
                .build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response post(TodoItem todoItem)
    {
        TodoItemRecord todoItemRecord = new TodoItemRecord(todoItem.getTitle(), false);
        String id = todoDao.add(todoItemRecord);
        SavedToDoItem savedToDoItem = new SavedToDoItem(todoItemRecord.getTitle(), getUrl(id), todoItemRecord.isCompleted());
        return Response.status(Response.Status.CREATED).entity(savedToDoItem).build();
    }

    @PUT
    @Path("/{id}/completed")
    @Produces("application/json")
    public Response setCompletedFlag(@PathParam("id") String id, Boolean completed)
    {
        TodoItemRecord todoItemRecord = todoDao.setStatus(id, completed);
        if (todoItemRecord == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        else
            return Response.status(Response.Status.OK.getStatusCode())
                    .entity(new SavedToDoItem(todoItemRecord.getTitle(), getUrl(id), todoItemRecord.isCompleted()))
                    .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id)
    {
        if (!todoDao.delete(id))
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        else
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

}
