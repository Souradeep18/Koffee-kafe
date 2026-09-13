package com.kafe.koffee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.UserPreference;

public interface UserPreferenceRepository extends JpaRepository<UserPreference,Long> {
	
    Optional<UserPreference> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

}
