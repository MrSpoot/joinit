package com.weaw.joinit.services;

import com.weaw.joinit.exceptions.WrongCredentialsException;
import com.weaw.joinit.models.Event;
import com.weaw.joinit.models.Tag;
import com.weaw.joinit.models.User;
import com.weaw.joinit.models.dtos.events.EventCreationDTO;
import com.weaw.joinit.models.dtos.events.EventResponseDTO;
import com.weaw.joinit.repositories.EventRepository;
import com.weaw.joinit.repositories.TagRepository;
import com.weaw.joinit.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TagRepository tagRepository;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository eventRepository,TagRepository tagRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    public Page<EventResponseDTO> getAllEvents(List<String> tags, Pageable pageable) {
        if(tags != null && !tags.isEmpty()){
            return eventRepository.findByTags(tags,pageable).map(EventResponseDTO::new);
        }else{
            return eventRepository.findAll(pageable).map(EventResponseDTO::new);
        }
    }

    public EventResponseDTO getEventById(long id){
        return eventRepository.findById(id).map(EventResponseDTO::new).orElse(null);
    }

    public EventResponseDTO createEvent(EventCreationDTO eventCreationDTO, String token){
        long userId = AuthenticationUtils.extractUserId(token);
        User user = userService.findById(userId);
        if(user != null){
            Event event = new Event(eventCreationDTO,user);

            List<Tag> tags = new ArrayList<>();

            if(eventCreationDTO.getTags() != null){
                eventCreationDTO.getTags().forEach(tag -> {
                    Tag _tag = new Tag();
                    _tag.setName(tag);
                    tags.add(tagRepository.save(_tag));
                });
            }
            event.setTags(tags);
            return new EventResponseDTO(this.eventRepository.save(event));
        }else{
            throw new WrongCredentialsException();
        }
    }

    public Event findById(long id) {
        return eventRepository.findById(id).orElse(null);
    }
}
