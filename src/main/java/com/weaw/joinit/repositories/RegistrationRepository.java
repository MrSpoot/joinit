package com.weaw.joinit.repositories;

import com.weaw.joinit.models.Event;
import com.weaw.joinit.models.Registration;
import com.weaw.joinit.models.User;
import com.weaw.joinit.models.enumerations.RegistrationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Registration findByUserAndEvent(User user, Event event);
    List<Registration> findByEventAndStatus(Event event, RegistrationStatus status);
    Page<Registration> findByEvent(Event event, Pageable pageable);
    Page<Registration> findByUser(User user, Pageable pageable);
    long countByEventAndStatus(Event event, RegistrationStatus status);

}
