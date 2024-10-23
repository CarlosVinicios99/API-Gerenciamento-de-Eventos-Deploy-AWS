package com.eventostec.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventostec.api.domain.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>{

}
