package org.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.todo.entities.Test;
import org.todo.models.TestService;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping
    public Test createTest(@RequestBody Test test) {
        return testService.saveTest(test);
    }

    @GetMapping("/{id}")
    public Test getTest(@PathVariable Long id) {
        return testService.getTestById(id);
    }
}