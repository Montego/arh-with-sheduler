package com.arhivator.arhwithsheduler.controllers;


import com.arhivator.arhwithsheduler.services.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zip")
public class Controller {
    @Autowired
    private ScheduledTasks scheduledTasks;

    @GetMapping("/check")
    public void zip() {
        scheduledTasks.doTasks();
    }

}
