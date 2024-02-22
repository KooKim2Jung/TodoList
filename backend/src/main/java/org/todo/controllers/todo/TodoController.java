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

    /**
     *  대윤 피드백
     *  할 일 추가 부분처럼 에러 처리를 하는 부분이 수정, 삭제, 조회에도 있으면 좋을 것 같음
     *  에러 처리를 하는 과정에서도 상태 코드만 반환해주는 것이 아닌 에러 메세지를 출력하는 것이 좋음
     *  이해와 수정 후 해당 주석 삭제해도 됨
     */

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
    @PutMapping("{listId}")
    public ResponseEntity<?> update(@PathVariable Long listId, @RequestBody TodoRequest request){
        service.updateById(listId, request);

        return ResponseEntity.ok().build();
    }

    //삭제
    @DeleteMapping("{listId}")
    public ResponseEntity<?> deleteOne(@PathVariable Long listId){
        service.deleteById(listId);

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
