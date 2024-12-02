package com.weaw.joinit.models.dtos.events;

import com.weaw.joinit.models.Event;
import com.weaw.joinit.models.Tag;
import com.weaw.joinit.models.dtos.users.Profile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventResponseDTO {

    private long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private Integer capacity;

    private List<String> tags;
    private Profile owner;

    public EventResponseDTO(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.date = event.getDate();
        this.owner = new Profile(event.getOwner());
        this.capacity = event.getCapacity();
        this.tags = event.getTags().stream().map(Tag::getName).collect(Collectors.toList());
    }
}
