package com.eventostec.api.domain.event;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.eventostec.api.domain.address.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "events")
@Entity
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String title;
	
	private String description;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@Column(name = "event_url")
	private String eventUrl;
	
	private Boolean remote;
	
	private Date date;
	
	@OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
	private Address address;
	
	public Event() {
		
	}

	public Event(UUID id, String title, String description, String imgUrl, String eventUrl, Boolean remote, Date date, Address address) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imgUrl = imgUrl;
		this.eventUrl = eventUrl;
		this.remote = remote;
		this.date = date;
		this.address = address;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public Boolean getRemote() {
		return remote;
	}

	public void setRemote(Boolean remote) {
		this.remote = remote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
