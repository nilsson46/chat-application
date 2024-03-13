package com.example.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_owner")
    private User groupOwner;

    @ManyToOne
    @JoinColumn(name = "group_members")
    private User groupMembers;

    @Column(name = "group_name")
    private String groupName;
    
}
