package org.todo.controllers.todo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.todo.entities.TodoList;
import org.todo.models.todo.TodoRequest;
import org.todo.models.todo.TodoResponse;
import org.todo.models.todo.TodoService;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name="TodoList API")
@CrossOrigin //CORS를 해결하기 위한 어노테이션
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService service;

    //할 일 추가
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoRequest request, HttpServletRequest servletRequest){
        if (ObjectUtils.isEmpty(request.getTitle())) {//ObjectUtils.isEmpty:null 체크와 isEmpty() 를 동시에 수행
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'title'필드가 비어있습니다.");
        }
        service.add(request, servletRequest);

        request.setCompleted(false);

        return ResponseEntity.ok().build(); //ok():HTTP 상태코드 200을 가진 ResponseEntity 반환
    }

    //할 일 수정
    @PutMapping("{listId}")
    public ResponseEntity<?> update(@PathVariable Long listId, @RequestBody TodoRequest request){
        try{
            service.updateById(listId, request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 Todo 항목을 찾을 수 없습니다.");
        }
    }

    //삭제
    @DeleteMapping("{listId}")
    public ResponseEntity<?> deleteOne(@PathVariable Long listId){
        try{
            service.deleteById(listId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 Todo 항목을 찾을 수 없습니다.");
        }
    }

    //한 일/할 일 전체 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteAll(@RequestParam(name = "completed", required = false) Boolean completed){
        if(completed != null){
            if(completed){
                service.deleteAllCompleted();
            }else{
                service.deleteAllNotCompleted();
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "completed 파라미터가 필요합니다.");
        }
        return ResponseEntity.ok().build();
    }

    //한 일 불러오기
    @GetMapping("/completed")
    public ResponseEntity<List<TodoResponse>> readCompleted(){
        List<TodoList> completedList = service.searchCompletedTrue();
        if (completedList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "완료된 Todo 항목이 없습니다.");
        }
        //데이터베이스 엔티티 객체를 API 응답 개체로 변환
        List<TodoResponse> response = completedList.stream() //TodoList 객체들의 리스트를 스림으로 변환
                                                    .map(TodoResponse::new) //TodoList 객체의 리스트를 TodoResponse객체의 리스트로 변환
                                                    .collect(Collectors.toList()); //스트림의 결과를 다시 리스트로 수집
        return ResponseEntity.ok(response); //HTTP 상태코드 200 OK 상태와 함께 response객체를 응답 본문에 포함하여 반환
    }

    //할 일 불러오기
    @GetMapping("/pending")
    public ResponseEntity<List<TodoResponse>> readNotCompleted() {
        List<TodoList> notCompletedList = service.searchCompletedFalse();
        if (notCompletedList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "미완료된 Todo 항목이 없습니다.");
        }
        List<TodoResponse> response = notCompletedList.stream()
                                                        .map(TodoResponse::new)
                                                        .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
