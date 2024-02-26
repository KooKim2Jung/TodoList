package org.todo.models.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todo.entities.TodoList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    private Long ListId;
    private String title;
    private Boolean completed;
    private String url;

    public TodoResponse(TodoList todoList){
        this.ListId = todoList.getListId();
        this.title = todoList.getTitle();
        this.completed = todoList.getCompleted();

        this.url = "http://localhost:8081/" + this.ListId;
    }
}
