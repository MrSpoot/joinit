package com.weaw.joinit.services;

import com.weaw.joinit.exceptions.AlreadyRegisterException;
import com.weaw.joinit.exceptions.EventFullException;
import com.weaw.joinit.exceptions.UnknownEventException;
import com.weaw.joinit.exceptions.WrongCredentialsException;
import com.weaw.joinit.models.Event;
import com.weaw.joinit.models.Registration;
import com.weaw.joinit.models.User;
import com.weaw.joinit.models.dtos.registrations.RegistrationResponseDTO;
import com.weaw.joinit.models.enumerations.RegistrationStatus;
import com.weaw.joinit.repositories.RegistrationRepository;
import com.weaw.joinit.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, UserService userService, EventService eventService) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    public RegistrationResponseDTO createRegistration(long eventId, String token) {
        long userId = AuthenticationUtils.extractUserId(token);
        User user = userService.findById(userId);
        Event event = eventService.findById(eventId);
        if(user != null && event != null) {
            if(registrationRepository.findByUserAndEvent(user,event) != null && !Objects.equals(event.getOwner().getId(), user.getId())) {
                if(event.getCapacity() == null || registrationRepository.findByEventAndStatus(event, RegistrationStatus.CONFIRMED).size() < event.getCapacity()) {
                    Registration registration = new Registration(event, user, RegistrationStatus.CONFIRMED);
                    return new RegistrationResponseDTO(registrationRepository.save(registration));
                }else{
                    throw new EventFullException();
                }
            }else{
                throw new AlreadyRegisterException();
            }
        }else{
            throw new WrongCredentialsException();
        }
    }

    public Page<RegistrationResponseDTO> getEventRegistration(long eventId, Pageable pageable) {
        Event event = eventService.findById(eventId);
        if(event != null) {
            return registrationRepository.findByEvent(event, pageable).map(RegistrationResponseDTO::new);
        }else{
            throw new UnknownEventException();
        }
    }

    public Page<RegistrationResponseDTO> getUserRegistration(String token, Pageable pageable) {
        long userId = AuthenticationUtils.extractUserId(token);
        User user = userService.findById(userId);
        if(user != null) {
            return registrationRepository.findByUser(user, pageable).map(RegistrationResponseDTO::new);
        }else{
            throw new WrongCredentialsException();
        }
    }

    public long getRegistrationEventCount(long eventId) {
        Event event = eventService.findById(eventId);
        if(event != null) {
            return registrationRepository.countByEventAndStatus(event,RegistrationStatus.CONFIRMED);
        }else{
            throw new UnknownEventException();
        }
    }

}
