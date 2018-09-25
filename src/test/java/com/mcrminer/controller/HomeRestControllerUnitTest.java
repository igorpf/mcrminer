package com.mcrminer.controller;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class HomeRestControllerUnitTest
{

    private HomeRestController homeRestController;

    @Before
    public void setUp()
    {
        homeRestController = new HomeRestController();
    }

    @Test
    public void test()
    {
        assertThat(homeRestController.fetchProject(null, null), equalTo("hello"));
    }
}