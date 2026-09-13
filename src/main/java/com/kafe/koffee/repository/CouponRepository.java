package com.kafe.koffee.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafe.koffee.entities.Coupon;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
	/*This is a Spring Data JPA derived,built-in method provided out of the box by JpaRepository. 
	 * a typo in the method name (e.g., spelling it LessThanEquals), Spring Boot will fail to start and throw a "PropertyReferenceException".
	Spring will automatically parse this method signature and generate the appropriate SQL query
	
	This method queries a database table to find records that meet three specific criteria simultaneously (AND logic):
	1) ActiveTrue: The active column must be true (boolean).
	2)ValidFromLessThanEqual: The validFrom date/time must be less than or equal to a provided timestamp (i.e., the record has already started).
	3) ValidUntilGreaterThanEqual: The validUntil date/time must be greater than or equal to a provided timestamp (i.e., the record has not expired yet).
	
	The generated SQL query is 
	SELECT * FROM coupon 
    WHERE active = true 
    AND valid_from <= ? 
    AND valid_until >= ?;

	*/
	
	List<Coupon>findByActiveTrueAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
			LocalDateTime from,
			LocalDateTime until);
	
	/* To shorten the method name, to prevent typos and exception we can use @Query, to find current active coupons just by sending the param "now" once
	 * @Query("SELECT c FROM Coupon WHERE c.active = true AND c.validFrom <= :now AND c.validUntil >= :now")
       List<Coupon> findCurrentActiveCoupons(@Param("now") LocalDateTime now);
 */
    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);

}
