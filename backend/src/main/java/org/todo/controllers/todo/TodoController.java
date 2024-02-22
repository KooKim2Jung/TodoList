package org.todo.controllers.todo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.entities.TodoList;
import org.todo.models.todo.TodoRequest;
import org.todo.models.todo.TodoResponse;
import org.todo.models.todo.TodoService;

@Tag(name="TodoList API")
@CrossOrigin //CORS를 해결하기 위한 어노테이션
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService service;

    //할 일 추가
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoRequest request){
        if (ObjectUtils.isEmpty(request.getTitle()))//ObjectUtils.isEmpty:null 체크와 isEmpty() 를 동시에 수행
            return ResponseEntity.badRequest().build();//ResponseEntity:적절한 상태 코드와 응답 헤더 및 응답 본문을 생성해서 클라이언트에 전달

        request.setCompleted(false);

        service.add(request);

        return ResponseEntity.ok().build(); //ok():HTTP 상태코드 200을 가진 ResponseEntity 반환
    }

    //할 일 수정
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody TodoRequest request){
        service.updateById(id, request);

        return ResponseEntity.ok().build();
    }

    //삭제
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable int id){
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    //한 일 전체 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteAllCompleted(){
        service.deleteAllCompleted();
        return ResponseEntity.ok().build();
    }

    //할 일 불러오기

    //한 일 불러오기

}
