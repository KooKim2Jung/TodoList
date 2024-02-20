package org.todo.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity // 이 클래스가 엔티티임을 나타냄
@Data // getter, setter 등의 메소드를 자동 생성
@NoArgsConstructor // 매개변수가 없는 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자
@Builder // 객체 생성할 때 유용하게 쓰이는데 아직 set 메소드만 이용했을 때와 비교해서 이점을 잘 모르겠음
@Table(name="User") // 테이블 이름 지정
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;
}