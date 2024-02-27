package org.todo.controllers.todo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.todo.models.todo.TodoAddRequest;
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
    @Operation(summary = "할 일 추가", description = "새로운 할 일을 추가합니다.")
    @ApiResponse(responseCode = "200", description = "할 일 추가 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)))
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoAddRequest addRequest, HttpServletRequest Request){
        if (ObjectUtils.isEmpty(addRequest.getTitle())) {//ObjectUtils.isEmpty:null 체크와 isEmpty() 를 동시에 수행
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'title'필드가 비어있습니다.");
        }
        service.add(addRequest, Request);

        return ResponseEntity.ok().build(); //ok():HTTP 상태코드 200을 가진 ResponseEntity 반환
    }

    //할 일 수정
    @Operation(summary = "할 일 수정", description = "특정 할 일의 내용을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "할 일 수정 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)))
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
    @Operation(summary = "할 일 삭제", description = "특정 할 일을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "할 일 삭제 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)))
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
    @Operation(summary = "한 일/할 일 전체 삭제", description = "완료된 할 일 또는 미완료된 할 일을 전체 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "할 일 전체 삭제 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)))
    @DeleteMapping
    public ResponseEntity<?> deleteAll(@RequestParam(name = "completed", required = false) Boolean completed, HttpServletRequest request){
        if(completed != null){
            if(completed){
                service.deleteAllCompleted(request);
            }else{
                service.deleteAllNotCompleted(request);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "completed 파라미터가 필요합니다.");
        }
        return ResponseEntity.ok().build();
    }

    //한 일 불러오기
    @Operation(summary = "완료된 할 일 조회", description = "완료된 모든 할 일을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "완료된 할 일 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class)))
    @GetMapping("/completed")
    public ResponseEntity<List<TodoResponse>> readCompleted(HttpServletRequest request){
        List<TodoList> completedList = service.searchCompletedTrue(request);
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
    @Operation(summary = "미완료된 할 일 조회", description = "미완료된 모든 할 일을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "미완료된 할 일 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class)))
    @GetMapping("/pending")
    public ResponseEntity<List<TodoResponse>> readNotCompleted(HttpServletRequest request) {
        List<TodoList> notCompletedList = service.searchCompletedFalse(request);
        if (notCompletedList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "미완료된 Todo 항목이 없습니다.");
        }
        List<TodoResponse> response = notCompletedList.stream()
                                                        .map(TodoResponse::new)
                                                        .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
