package com.eventostec.api.domain.address;

import java.io.Serializable;
import java.util.UUID;

import com.eventostec.api.domain.event.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "addresses")
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String city;
	
	private String uf;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	
	public Address() {
		
	}

	public Address(UUID id, String city, String uf, Event event) {
		super();
		this.id = id;
		this.city = city;
		this.uf = uf;
		this.event = event;
	}
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
