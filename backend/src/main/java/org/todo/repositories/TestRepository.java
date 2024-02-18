package org.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.entities.Test;

public interface TestRepository  extends JpaRepository<Test, Long> {

}
