package ar.com.pichidev.homestock.user.core.entity;

import lombok.Getter;

@Getter
public class User {
    String name;
    String lastName;
    String email;
    Roles[] roles;
}
