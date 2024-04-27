package com.project.stone.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/api/hello")
public class HelloController {

    @GetMapping("api/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("healthstatus")
    public String healthStatus() {
        return "I have ashtma";
    }
}