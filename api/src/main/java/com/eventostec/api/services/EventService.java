package com.eventostec.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private AmazonS3 s3Client;
	
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
		
		return newEvent;
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
			return null;
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
