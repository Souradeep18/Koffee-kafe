package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Notification;
import com.kafe.koffee.enums.NotificationEvent;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    // All notifications of a user
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Notifications by event
    List<Notification> findByUserIdAndEventOrderByCreatedAtDesc(Long userId,NotificationEvent event);
    
 // Unread notifications
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);

    // Unsent email notifications
    List<Notification> findBySentFalseOrderByCreatedAtAsc();

    // Unsent notifications for a particular user
    List<Notification> findByUserIdAndSentFalseOrderByCreatedAtAsc(Long userId);
    
    // Check whether a particular event notification exists
    boolean existsByUserIdAndEvent(
            Long userId,
            NotificationEvent event
    );


}
