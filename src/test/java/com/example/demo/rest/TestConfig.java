package com.example.demo.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.example.demo",
        "com.example.demo.dao",
        "com.example.demo.model",
        "com.example.demo.rest"})
public class TestConfig
{
}
