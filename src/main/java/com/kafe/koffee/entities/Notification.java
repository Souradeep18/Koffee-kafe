package com.kafe.koffee.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.kafe.koffee.enums.NotificationEvent;

//import com.kafe.koffee.notification.NotificationEvent;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @EqualsAndHashCode.Include
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private NotificationEvent event;

	    @Column(nullable = false)
	    private String title;

	    @Column(nullable = false, columnDefinition = "TEXT")
	    private String message;

	    @Column(nullable = false)
	    @Builder.Default
	    private boolean sent = false;
	    
	    @Column(nullable = false)
	    @Builder.Default
	    private boolean read = false;

	    @Column(nullable = false)
	    @Builder.Default
	    private LocalDateTime createdAt = LocalDateTime.now();

	    private LocalDateTime sentAt;

	    private LocalDateTime readAt;
	

}
