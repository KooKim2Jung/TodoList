package org.todo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.todo.entities.Test;
import org.todo.models.TestService;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("api/v1/tests")
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