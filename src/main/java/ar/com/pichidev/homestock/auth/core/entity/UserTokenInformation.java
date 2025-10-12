package ar.com.pichidev.homestock.auth.core.entity;

import java.util.UUID;


public record UserTokenInformation(
UUID id,
String email,
String name,
String lastname,
String[] roles
) {
}
