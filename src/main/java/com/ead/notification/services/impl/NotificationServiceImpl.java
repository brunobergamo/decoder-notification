package com.ead.notification.services.impl;

import com.ead.notification.configs.enums.NotificationStatus;
import com.ead.notification.configs.models.NotificationModel;
import com.ead.notification.reposiories.NotificationRepository;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationModel saveNotification(NotificationModel notificationModel) {
        return notificationRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable page) {

        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED,page);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRepository.findByNotificationIdAndUserId(notificationId,userId);
    }
}
