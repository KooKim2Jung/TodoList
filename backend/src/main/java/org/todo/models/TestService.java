package org.todo.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.todo.entities.Test;
import org.todo.repositories.TestRepository;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }
}
