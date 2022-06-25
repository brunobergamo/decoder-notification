package com.ead.notification.controllers;

import com.ead.notification.configs.models.NotificationModel;
import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationsByUser(@PathVariable(value="userId")UUID userId,
                                                                             @PageableDefault(page = 0,size = 10, sort = "notificationId", direction = Sort.Direction.ASC)
                                                                             Pageable page){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUser(userId,page));
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value="userId")UUID userId,
                                                     @PathVariable(value="notificationId")UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto){

        Optional<NotificationModel> notificationModelOptional = notificationService.findByNotificationIdAndUserId(notificationId,userId);

        if(notificationModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }

        NotificationModel notificationModel = notificationModelOptional.get();
        notificationModel.setNotificationStatus(notificationDto.getNotificationStatus());
        notificationService.saveNotification(notificationModel);
        return ResponseEntity.status(HttpStatus.OK).body(notificationModel);
    }
}
