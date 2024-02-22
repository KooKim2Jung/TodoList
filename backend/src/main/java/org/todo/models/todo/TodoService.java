package org.todo.models.todo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.todo.entities.TodoList;
import org.todo.repositories.TodoRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    //TodoList 할 일 추가
    public TodoList add(TodoRequest request){
        TodoList todoList = new TodoList();
        todoList.setTitle(request.getTitle());
        todoList.setCompleted(request.isCompleted());
        return todoRepository.save(todoList);
    }

    //TodoList 전체 목록 불러오기
    public List<TodoList> searchAll(){
        return todoRepository.findAll();
    }

    //TodoList 목록 중 특정 아이템을 조회
    //id에 해당하는 TodoList 엔티티가 존재하지 않으면 예외 발생
    public TodoList searchById(int id){
        return todoRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //TodoList 목록 중 특정 아이템을 수정
    public TodoList updateById(int id, TodoRequest request){
        TodoList todoList = this.searchById(id);

        todoList.setTitle(request.getTitle());
        todoList.setCompleted(request.isCompleted());

        return todoRepository.save(todoList);
    }

    //TodoList 목록 중 특정 아이템을 삭제
    public void deleteById(int id){
        todoRepository.deleteById(id);
    }

    //TodoList 한 일 전체 삭제
    public void deleteAllCompleted(){
        List<TodoList> completedTodos = todoRepository.findByCompletedTrue();
        todoRepository.deleteAll(completedTodos);
    }
}
