package com.singh.astha.notification.service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("notification_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationToken {

    @Id
    private String id;

    @Indexed(unique = true, background = true)
    private Long userId;

    private List<Token> tokens;

}
