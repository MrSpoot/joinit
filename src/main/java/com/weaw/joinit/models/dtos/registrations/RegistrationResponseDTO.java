package com.weaw.joinit.models.dtos.registrations;

import com.weaw.joinit.models.Registration;
import com.weaw.joinit.models.dtos.events.EventResponseDTO;
import com.weaw.joinit.models.dtos.users.Profile;
import com.weaw.joinit.models.enumerations.RegistrationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationResponseDTO {

    private long id;
    private EventResponseDTO event;
    private Profile user;
    private RegistrationStatus status;

    private LocalDateTime createdAt;

    public RegistrationResponseDTO(Registration registration) {
        this.id = registration.getId();
        this.event = new EventResponseDTO(registration.getEvent());
        this.user = new Profile(registration.getUser());
        this.status = registration.getStatus();
        this.createdAt = registration.getCreatedAt();
    }
}
