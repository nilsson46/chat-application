package com.example.application.model;

import com.example.application.model.MessageType;
import com.example.application.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "type")
    private MessageType type;

    @Column(name = "timestamp")
    private String timestamp;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    private Chat chat;
}