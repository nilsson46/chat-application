package com.example.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "`group`")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;


    @Column(name = "group_owner")
    private String groupOwner;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> groupMembers;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "private_group")
    private boolean privateGroup;

    public List<User> getMembers() {
        return groupMembers;
    }
}
