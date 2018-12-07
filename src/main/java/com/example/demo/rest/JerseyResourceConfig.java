package com.example.demo.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyResourceConfig extends ResourceConfig
{
    public JerseyResourceConfig()
    {
        register(TodoRest.class);
    }
}
