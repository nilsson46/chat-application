package com.example.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "group_request")
@Getter
@Setter
public class GroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_request_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "group_name")
    private String groupName;

}
