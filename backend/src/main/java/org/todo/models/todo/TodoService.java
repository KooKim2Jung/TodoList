package org.todo.models.todo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.todo.config.security.JwtTokenProvider;
import org.todo.entities.Member;
import org.todo.entities.TodoList;
import org.todo.repositories.TodoRepository;
import org.todo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Member getMember(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        String userIdString = jwtTokenProvider.getUserPK(token);
        int userId = Integer.parseInt(userIdString);

        Optional<Member> member = userRepository.findById(userId);

        return member.get();
    }
    //TodoList 할 일 추가
    public TodoList add(TodoRequest todoRequest, HttpServletRequest request){
        Member member = getMember(request);

        TodoList todoList = TodoList.builder()
                .member(member)
                .title(todoRequest.getTitle())
                .completed(todoRequest.getCompleted())
                .build();

        return todoRepository.save(todoList);
    }

    // 미완료된 TodoList 항목만 조회
    public List<TodoList> searchCompletedFalse(){
        return todoRepository.findByCompletedFalse();
    }

    // 완료된 TodoList 항목만 조회
    public List<TodoList> searchCompletedTrue(){
        return todoRepository.findByCompletedTrue();
    }

    //TodoList 목록 중 특정 아이템을 조회
    //id에 해당하는 TodoList 엔티티가 존재하지 않으면 예외 발생
    public TodoList searchById(Long listId){
        return todoRepository.findById(listId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 Todo 항목을 찾을 수 없습니다."));
    }

    //TodoList 목록 중 특정 아이템을 수정
    public TodoList updateById(Long listId, TodoRequest request){
        TodoList todoList = this.searchById(listId);

        todoList.setTitle(request.getTitle());
        todoList.setCompleted(request.getCompleted());

        return todoRepository.save(todoList);
    }

    //TodoList 목록 중 특정 아이템을 삭제
    public void deleteById(Long listId){
        todoRepository.deleteById(listId);
    }

    //TodoList 한 일 전체 삭제
    public void deleteAllCompleted(){
        List<TodoList> completedTodos = todoRepository.findByCompletedTrue();
        todoRepository.deleteAll(completedTodos);
    }

    //TodoList 할 일 전체 삭제
    public void deleteAllNotCompleted(){
        List<TodoList> notCompletedTodos = todoRepository.findByCompletedFalse();
        todoRepository.deleteAll(notCompletedTodos);
    }
}
