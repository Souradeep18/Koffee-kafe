package com.kafe.koffee.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafe.koffee.entities.ChatMessage;
import com.kafe.koffee.entities.User;
import com.kafe.koffee.enums.ChatSender;
import com.kafe.koffee.exception.BadRequestException;
import com.kafe.koffee.exception.UserNotFoundException;
import com.kafe.koffee.repository.ChatMessageRepository;
import com.kafe.koffee.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
	
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    
    //Get user by Email
    
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
    }
    
    //Generate Bot response
    
    private String generateResponse(String message) {

        String input = message.toLowerCase().trim();

        // Order tracking
        if (input.contains("track")
                || input.contains("order status")
                || input.contains("where is my order")) {

            return "Please provide your order ID so I can help track your order.";
        }

        // Order cancellation
        if (input.contains("cancel")
                || input.contains("cancel my order")) {

            return "Please provide your order ID to check whether the order can be cancelled.";
        }

        // Coffee information
        if (input.contains("coffee")
                || input.contains("espresso")
                || input.contains("latte")
                || input.contains("cappuccino")
                || input.contains("mocha")
                || input.contains("americano")) {

            return "We have Espresso, Americano, Latte, Cappuccino, Mocha, Macchiato, Flat White and Cold Brew.";
        }

        // Greeting
        if (input.contains("hello")
                || input.contains("hi")
                || input.contains("hey")) {

            return "Hello! Welcome to KafeKoffee. How can I help you today?";
        }

        return "I can help you with orders, cancellations, coffee information and KafeKoffee queries.";
    }
    
    //Chat
    
    public ChatMessage chat(String email,String message) {

        if (message == null || message.isBlank()) {
            throw new BadRequestException(
                    "Message cannot be empty"
            );
        }
        
        User user = getUser(email);

        // Save user's message
        ChatMessage userMessage = ChatMessage.builder()
                .user(user)
                .sender(ChatSender.USER)
                .message(message)
                .intent("GENERAL")
                .build();

        chatMessageRepository.save(userMessage);
        
     // Generate bot response
        String response = generateResponse(message);

        // Save bot response
        ChatMessage botMessage = ChatMessage.builder()
                .user(user)
                .sender(ChatSender.BOT)
                .message(response)
                .intent("GENERAL")
                .build();

        return chatMessageRepository.save(botMessage);
    }
    
    //Get Chat History
    
    @Transactional(readOnly = true)
    public List<ChatMessage> getHistory(String email) {

        User user = getUser(email);

        return chatMessageRepository.findByUserIdOrderByCreatedAtAsc(user.getId());
    }
    
    //Get last 50 messages
    
    @Transactional(readOnly = true)
    public List<ChatMessage> getRecentHistory(String email) {

        User user = getUser(email);

        return chatMessageRepository.findTop50ByUserIdOrderByCreatedAtDesc(user.getId());
        
    }
    
    
}
