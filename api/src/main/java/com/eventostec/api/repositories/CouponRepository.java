package com.eventostec.api.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventostec.api.domain.coupon.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID>{
	
	List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
	
}
