package org.todo.models.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todo.entities.TodoList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    private int ListId;
    private String title;
    private boolean completed;
    private String url;

    public TodoResponse(TodoList todoList){
        this.ListId = todoList.getListId();
        this.title = todoList.getTitle();
        this.completed = todoList.isCompleted();

        this.url = "http://localhost:8081/" + this.ListId;
    }
}
