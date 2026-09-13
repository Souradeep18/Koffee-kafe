package com.kafe.koffee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>  {
    List<PaymentMethod> findByUserIdAndActiveTrue(Long userId);

    Optional<PaymentMethod> findByIdAndUserId(Long id, Long userId);

    Optional<PaymentMethod> findByUserIdAndDefaultMethodTrue(Long userId);

}
