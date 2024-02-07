package com.dark.online.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class MessageForChatDto {
    private String name;
    private byte[] avatar;
    private LocalDateTime localDateTime;
    private String message;
}