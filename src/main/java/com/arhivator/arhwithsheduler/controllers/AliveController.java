package com.arhivator.arhwithsheduler.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alive")
public class AliveController {
    @GetMapping()
    public String alive() {
        return "I'm alive";
    }
}
