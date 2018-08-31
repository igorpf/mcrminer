package com.mcrminer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeRestController
{
    @RequestMapping(method = RequestMethod.GET)
    public String hello()
    {
        return "hello";
    }
}
