package org.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todo.entities.TodoList;
import java.util.List;

// JpaRepository<대상으로 지정할 엔티티, 해당 엔티티 id의 필드 타입>
@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByUserIdAndCompletedFalse(int userId);
    List<TodoList> findByUserIdAndCompletedTrue(int userId);
}
