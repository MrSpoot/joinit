package com.weaw.joinit.models.dtos.users;

import com.weaw.joinit.models.User;
import lombok.Data;

@Data
public class Profile {

    private String username;
    private String email;
    private String firstname;
    private String lastname;

    public Profile(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
    }
}
