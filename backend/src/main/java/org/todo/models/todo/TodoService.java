package org.todo.models.todo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.todo.entities.TodoList;
import org.todo.repositories.TodoRepository;
import java.util.List;

/**
 *  대윤 피드백
 *  JPA를 사용하면 CRUD하는 과정에서 특정 Exception이 발생할 경우 잘못된 정보가
 *  데이터베이스에 들어가게 될 수 있는데, 이를 방지하는 것이 @Transactional 어노테이션
 *  자세한 정보는 https://kafcamus.tistory.com/30 이 블로그 확인
 *  이해 후 해당 주석 삭제
 */
@Service
@Transactional
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    //TodoList 할 일 추가
    public TodoList add(TodoRequest request){
        TodoList todoList = new TodoList();
        todoList.setTitle(request.getTitle());
        todoList.setCompleted(request.getCompleted());
        return todoRepository.save(todoList);
    }

    //TodoList 전체 목록 불러오기
    /**
    public List<TodoList> searchAll(){
        return todoRepository.findAll();
    }
    */
    /**
     *  대윤 피드백
     *  완료된 항목과 미완료 항목을 별도로 볼 수 있도록 하는 메서드를 구현하기 위해서는
     *  두 개의 메서드를 추가해 주어야 함. -> 이 주석 밑에 만들어야 하는 메서드를 주석 처리하겠음
     *  TodoRepository에 completed가 true인지에 대한 메서드는 정의되어 있으나, false에 대한 메서드는
     *  정의되어 있지 않으므로 추가해 주어야 함
     *  해당 주석은 이해 후 구현 했다면 삭제해도 됨
     */
    // 미완료된 TodoList 항목만 조회

    // 완료된 TodoList 항목만 조회


    //TodoList 목록 중 특정 아이템을 조회
    //id에 해당하는 TodoList 엔티티가 존재하지 않으면 예외 발생
    public TodoList searchById(Long listId){
        return todoRepository.findById(listId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        /**
         *  대윤 피드백
         *  ResponseStatusException이 발생할 경우 NOTFOUND(404)에러를 표시해 주는데,
         *  개발 과정에서 단순 상태코드에 대한 오류를 표시해서 응답할 경우 프론트엔드 뿐 만 아니라
         *  백엔드 에서도 오류가 발생했을 때 어떤 부분이 잘못됐는지 찾기 힘들 수 있음
         *  그러므로 "해당 ID의 Todo 항목을 찾을 수 없습니다"와 같은 에러 메세지를 포함시켜주어야 함
         *  수정 후 해당 주석 삭제해도 됨
         */
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
}
