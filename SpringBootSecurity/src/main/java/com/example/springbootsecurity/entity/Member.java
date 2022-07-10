package com.example.springbootsecurity.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "roles")
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @ElementCollection
    @Builder.Default
    private Set<MemberRole> roles = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roles.add(memberRole);
    }
}
