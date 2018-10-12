package com.mcrminer.controller;

import com.mcrminer.model.Project;
import com.mcrminer.service.CodeReviewMiningService;
import com.mcrminer.service.DefaultAuthenticationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeRestController {

    private CodeReviewMiningService gerritCodeReviewMiningService;

    @Autowired
    public HomeRestController(@Qualifier("gerritCodeReviewMiningService") CodeReviewMiningService gerritCodeReviewMiningService) {
        this.gerritCodeReviewMiningService = gerritCodeReviewMiningService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String fetchProject(@RequestParam String projectId, DefaultAuthenticationData authData) {
        Project p = gerritCodeReviewMiningService.fetchProject(projectId, authData);
        return "hello";
    }
}
