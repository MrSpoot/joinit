package com.weaw.joinit.models;

import com.weaw.joinit.models.dtos.events.EventCreationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;
    private String description;
    private Integer capacity;
    @Column(nullable = false)
    @Min(value = 0)
    private LocalDateTime date;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Event(EventCreationDTO eventCreationDTO, User user) {
        this.title = eventCreationDTO.getTitle();
        this.description = eventCreationDTO.getDescription();
        this.capacity = eventCreationDTO.getCapacity();
        this.date = eventCreationDTO.getDate();
        this.owner = user;
    }
}
