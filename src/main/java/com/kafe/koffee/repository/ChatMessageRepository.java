package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	
    List<ChatMessage> findByUserIdOrderByCreatedAtAsc(Long userId);

    List<ChatMessage> findTop50ByUserIdOrderByCreatedAtDesc(Long userId);

}
