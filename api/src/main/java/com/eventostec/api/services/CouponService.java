package com.eventostec.api.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.repositories.CouponRepository;
import com.eventostec.api.repositories.EventRepository;

@Service
public class CouponService {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	
	public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponData) {
		
		Event event = this.eventRepository.findById(eventId)
			.orElseThrow(() -> new IllegalArgumentException("Event not found"));
		
		Coupon coupon = new Coupon();
		
		coupon.setCode(couponData.code());
		coupon.setDiscount(couponData.discount());
		coupon.setValid(new Date(couponData.valid()));
		coupon.setEvent(event);
		
		return this.couponRepository.save(coupon);
		
	}
	
	public List<Coupon> consultCoupons(UUID eventId, Date currentDate){
		return this.couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
	}
	
}
