package org.todo.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.todo.entities.Member;

public class SecurityUser extends User {
    private Member member;

    public SecurityUser(Member member) {
        super(member.getId().toString(), member.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER")); // 기본 Role 설정
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}