package com.singh.astha.notificationservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToken {

    private Long userId;
    private String userToken;


}
