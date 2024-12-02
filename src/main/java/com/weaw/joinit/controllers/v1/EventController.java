package com.weaw.joinit.controllers.v1;

import com.weaw.joinit.models.Event;
import com.weaw.joinit.models.Tag;
import com.weaw.joinit.models.dtos.events.EventCreationDTO;
import com.weaw.joinit.models.dtos.events.EventResponseDTO;
import com.weaw.joinit.services.EventService;
import com.weaw.joinit.utils.annotations.Unsecured;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Unsecured
    public ResponseEntity<Page<EventResponseDTO>> getAllEvents(@RequestParam(required = false) List<String> tags, Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(tags,pageable));
    }

    @GetMapping("/{id}")
    @Unsecured
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventCreationDTO eventCreationDTO, HttpServletRequest request) {
        String token = request.getAttribute("token").toString();
        return ResponseEntity.ok(eventService.createEvent(eventCreationDTO, token));
    }

}
