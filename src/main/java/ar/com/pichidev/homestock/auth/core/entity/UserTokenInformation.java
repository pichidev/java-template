package ar.com.pichidev.homestock.auth.core.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserTokenInformation(
UUID id,
String email,
String name,
String lastname,
String[] roles
) {
}
