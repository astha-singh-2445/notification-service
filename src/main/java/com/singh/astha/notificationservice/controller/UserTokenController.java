package com.singh.astha.notificationservice.controller;

import com.singh.astha.notificationservice.dtos.kafka.NotificationRequest;
import com.singh.astha.notificationservice.dtos.request.UserTokenRequestDto;
import com.singh.astha.notificationservice.dtos.response.UserTokenResponseDto;
import com.singh.astha.notificationservice.service.FCMService;
import com.singh.astha.notificationservice.service.UserTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class UserTokenController {

    private final UserTokenService userTokenService;
    private final FCMService fcmService;

    public UserTokenController(UserTokenService userTokenService,
                               FCMService fcmService) {
        this.userTokenService = userTokenService;
        this.fcmService = fcmService;
    }

    @PostMapping()
    public ResponseEntity<UserTokenResponseDto> getUserToken(@Valid @RequestBody UserTokenRequestDto userTokenDto) {
        return ResponseEntity.ok().body(userTokenService.saveUserToken(userTokenDto));
    }

    @GetMapping()
    public String handleWebclient() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(13771888056598551L);
        notificationRequest.setTemplateId("62580ca3b352db798c4ec8ca");
        HashMap<String, String> values = new HashMap<>();
        values.put("medicine-name", "Paracetamol");
        notificationRequest.setPlaceHolder(values);
        fcmService.sendNotification(notificationRequest);
        return "Success";
    }

}
