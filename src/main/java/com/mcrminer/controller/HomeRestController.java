package com.mcrminer.controller;

import com.mcrminer.model.Project;
import com.mcrminer.service.CodeReviewMiningService;
import com.mcrminer.service.DefaultAuthenticationData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeRestController {

    @Resource(name = "gerritCodeReviewMiningService")
    private CodeReviewMiningService gerritCodeReviewMiningService;

    @RequestMapping(method = RequestMethod.GET)
    public String fetchProject(@RequestParam String projectId, DefaultAuthenticationData authData) {
        Project p = gerritCodeReviewMiningService.fetchProject(projectId, authData);
        return "hello";
    }
}
