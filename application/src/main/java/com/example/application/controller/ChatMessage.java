package com.example.application.controller;


//Should change folder later on.

import com.example.application.model.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String content;
    private String sender;

    private MessageType type;
}
