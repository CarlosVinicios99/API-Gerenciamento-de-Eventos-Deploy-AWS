package com.eventostec.api.domain.coupon;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.eventostec.api.domain.event.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "coupons")
@Entity
public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String code;
	
	private Integer discount;
	
	private Date valid;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	
	public Coupon() {
		
	}

	public Coupon(UUID id, String code, Integer discount, Date valid, Event event) {
		super();
		this.id = id;
		this.code = code;
		this.discount = discount;
		this.valid = valid;
		this.event = event;
	}
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Date getValid() {
		return valid;
	}

	public void setValid(Date valid) {
		this.valid = valid;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
