package com.mcrminer.controller;

import com.mcrminer.service.CodeReviewMiningService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HomeRestControllerUnitTest {

    private HomeRestController homeRestController;
    private CodeReviewMiningService codeReviewMiningService = mock(CodeReviewMiningService.class);

    @Before
    public void setUp() {
        homeRestController = new HomeRestController(codeReviewMiningService);
    }

    @Test
    public void test()  {
    }
}