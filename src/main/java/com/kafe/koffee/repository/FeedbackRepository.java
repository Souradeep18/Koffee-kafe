package com.kafe.koffee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Feedback> findByReviewedFalseOrderByCreatedAtAsc();

}
