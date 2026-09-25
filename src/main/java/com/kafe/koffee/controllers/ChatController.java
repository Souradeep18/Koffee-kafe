package com.kafe.koffee.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.koffee.entities.ChatMessage;
import com.kafe.koffee.services.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;
	
	//Send message
	
	 @PostMapping
	    public ResponseEntity<ChatMessage> chat(@RequestParam String message,Authentication authentication) {

	        String email = authentication.getName();

	        ChatMessage response =chatService.chat(email,message);

	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	 
	 //Get chat history
	 @GetMapping("/history")
	    public ResponseEntity<List<ChatMessage>> getHistory(Authentication authentication) {

	        String email = authentication.getName();

	        List<ChatMessage> history =chatService.getHistory(email);

	        if (history.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }

	        return ResponseEntity.status(HttpStatus.OK).body(history);
	    }
	 
	 //Get recent 50 chats
	 
	 @GetMapping("/recent")
	    public ResponseEntity<List<ChatMessage>> getRecentHistory(Authentication authentication) {

	        String email = authentication.getName();

	        List<ChatMessage> history =chatService.getRecentHistory(email);

	        if (history.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }

	        return ResponseEntity.status(HttpStatus.OK).body(history);
	        
	    }

}
