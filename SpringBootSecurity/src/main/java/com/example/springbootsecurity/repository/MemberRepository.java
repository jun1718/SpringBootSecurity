package com.example.springbootsecurity.repository;

import com.example.springbootsecurity.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Override
    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findById(String s);
}
