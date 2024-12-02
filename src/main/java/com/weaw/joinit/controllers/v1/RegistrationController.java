package com.weaw.joinit.controllers.v1;

import com.weaw.joinit.models.Registration;
import com.weaw.joinit.models.dtos.registrations.RegistrationResponseDTO;
import com.weaw.joinit.services.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/events/{eventId}")
    public ResponseEntity<RegistrationResponseDTO> registerToEvent(@PathVariable long eventId, HttpServletRequest request) {
        String token = request.getAttribute("token").toString();
        return ResponseEntity.ok(registrationService.createRegistration(eventId, token));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Page<RegistrationResponseDTO>> getEventRegistration(@PathVariable long eventId, Pageable pageable, HttpServletRequest request) {
        String token = request.getAttribute("token").toString();
        return ResponseEntity.ok(registrationService.getEventRegistration(eventId, pageable));
    }

    @GetMapping("/count/events/{eventId}")
    public ResponseEntity<Long> getRegistrationsEventCount(@PathVariable long eventId) {
        return ResponseEntity.ok(registrationService.getRegistrationEventCount(eventId));
    }

    @GetMapping
    public ResponseEntity<Page<RegistrationResponseDTO>> getUserRegistration(Pageable pageable, HttpServletRequest request) {
        String token = request.getAttribute("token").toString();
        return ResponseEntity.ok(registrationService.getUserRegistration(token, pageable));
    }

}
