package com.eventostec.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Autowired 
	private AddressService addressService;
	
	@Autowired
	private CouponService couponService;
	
	@Value("${aws.bucket.name}")
	private String bucketName;
	
	
	public Event createEvent(EventRequestDTO data) {
		String imgUrl = null;
		
		if(data.image() != null) {
			imgUrl = this.uploadImage(data.image());
		}
		
		Event newEvent = new Event();
		
		newEvent.setTitle(data.title());
		newEvent.setDescription(data.description());
		newEvent.setEventUrl(data.eventUrl());
		newEvent.setDate(new Date(data.date()));
		newEvent.setImgUrl(imgUrl);
		newEvent.setRemote(data.remote());
		
		this.eventRepository.save(newEvent);
		
		if(!data.remote()) {
			this.addressService.createAddress(data, newEvent);
		}
		
		return newEvent;
	}
	
	public EventDetailsDTO getEventDetails(UUID eventId) {
		Event event = this.eventRepository.findById(eventId)
			.orElseThrow(() -> new IllegalArgumentException("Event Not Found"));
		
		List<Coupon> coupons = this.couponService.consultCoupons(eventId, new Date());
		
		List<EventDetailsDTO.CouponDTO> couponsDTO = coupons.stream()
			.map(coupon -> new EventDetailsDTO.CouponDTO(
				coupon.getCode(),
				coupon.getDiscount(),
				coupon.getValid()
				)
			).collect(Collectors.toList());
		
		return new EventDetailsDTO(
			event.getId(),
			event.getTitle(),
			event.getDescription(),
			event.getDate(),
			event.getAddress() != null ? event.getAddress().getCity() : "",
			event.getAddress() != null ? event.getAddress().getUf() : "",
			event.getImgUrl(),
			event.getEventUrl(),
			couponsDTO
		);
			
	}
	
	public List<EventResponseDTO> getEvents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Event> eventsPage = this.eventRepository.findAll(pageable);
		return eventsPage.map(
			event -> new EventResponseDTO(event.getId(), 
				event.getTitle(), 
				event.getDescription(),
				event.getDate(),
				event.getAddress() != null ? event.getAddress().getCity() : "",
				event.getAddress() != null ? event.getAddress().getUf() : "",
				event.getRemote(),
				event.getEventUrl(),
				event.getImgUrl()
			)
		).stream().toList();
	}
	
	public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Event> eventsPage = this.eventRepository.findUpcomingEvents(new Date(), pageable);
		return eventsPage.map(
			event -> new EventResponseDTO(event.getId(), 
				event.getTitle(), 
				event.getDescription(),
				event.getDate(),
				event.getAddress() != null ? event.getAddress().getCity() : "",
				event.getAddress() != null ? event.getAddress().getUf() : "",
				event.getRemote(),
				event.getEventUrl(),
				event.getImgUrl()
			)
		).stream().toList();
	}
	
	public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
		
		title = title != null ? title : "";
		city = city != null ? city : "";
		uf = uf != null ? uf : "";
		startDate = startDate != null ? startDate : new Date(0);
		endDate = endDate != null ? endDate : new Date();
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Event> eventsPage = this.eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);
		return eventsPage.map(
			event -> new EventResponseDTO(event.getId(), 
				event.getTitle(), 
				event.getDescription(),
				event.getDate(),
				event.getAddress() != null ? event.getAddress().getCity() : "",
				event.getAddress() != null ? event.getAddress().getUf() : "",
				event.getRemote(),
				event.getEventUrl(),
				event.getImgUrl()
			)
		).stream().toList();
	}
	
	private String uploadImage(MultipartFile multipartFile) {
		
		String imgName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
		
		try {
			File file = this.convertMultipartToFile(multipartFile);
			s3Client.putObject(bucketName, imgName, file);
			file.delete();
			return s3Client.getUrl(bucketName, imgName).toString();
		}
		catch(Exception error) {
			System.out.println("Erro ao salvar arquivo no bucket " + error.getMessage());
			return "";
		}
		
	}
	
	private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
		
		File convFile = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}
}
