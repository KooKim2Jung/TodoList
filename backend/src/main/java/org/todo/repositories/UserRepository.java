package org.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.entities.Member;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Integer> {
    // JpaRspository를 상속 받음으로써 기본적인 데이터접근 메소드를 사용할 수 있음
    // 추가적으로 필요한 메소드는 여기에 직접 구현
    Optional<Member> findByEmail(String email);
}
