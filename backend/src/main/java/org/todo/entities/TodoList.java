package org.todo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="Task")
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //값을 따로 설정하지 않아도 1부터 1씩 자동으로 증가하며 저장
    private int listId;

    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false)
    private boolean iscompleted;
}
