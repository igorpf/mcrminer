package com.mcrminer.controller;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class HomeControllerUnitTest
{

    private HomeController homeController;

    @Before
    public void setUp()
    {
        homeController = new HomeController();
    }

    @Test
    public void test()
    {
        assertThat(homeController.hello(), equalTo("hello"));
    }
}