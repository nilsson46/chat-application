package com.example.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
//POJO
@Table(name ="user")
@Getter
@Setter
public class User implements UserDetails {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "id")
    Integer id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Enumerated(value = EnumType.STRING)
    Role role;
    //SPRING USERDETAILS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
