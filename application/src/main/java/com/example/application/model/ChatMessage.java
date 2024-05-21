package com.example.application.model;


//Should change folder later on.

import com.example.application.model.MessageType;
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
    private Long chatId;


    //private String groupName;

    @Column(name = "content")
    private String content;

    @Column(name = "sender")
    private String sender;

    @Column(name = "type")
    private MessageType type;

    @Column(name = "timestamp")
    private String timestamp;


}
