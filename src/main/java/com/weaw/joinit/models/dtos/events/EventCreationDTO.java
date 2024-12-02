package com.weaw.joinit.models.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCreationDTO {

    private String title;
    private String description;
    private Integer capacity;
    private LocalDateTime date;
    private List<String> tags;

}
