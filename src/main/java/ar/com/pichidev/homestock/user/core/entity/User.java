package ar.com.pichidev.homestock.user.core.entity;

import ar.com.pichidev.homestock.common.exception.FieldError;
import ar.com.pichidev.homestock.common.exception.FieldErrorCodeValues;
import ar.com.pichidev.homestock.common.exception.ValidationException;
import ar.com.pichidev.homestock.common.helper.GeneralValidation;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.regex.Pattern;

@Getter
public class User {
    private final UUID id;
    private final String name;
    private final String lastName;
    private final String email;
    private final Set<Roles> roles;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    @Builder
    private User(UUID id, String name, String lastName, String email, Set<Roles> roles) {
        validateFields(name,lastName,email);
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.lastName = lastName;
        this.email = email;

        Set<Roles> rolesWithUser = new HashSet<>();
        if (roles != null) {
            rolesWithUser.addAll(roles);
        }
        rolesWithUser.add(Roles.USER);

        this.roles = rolesWithUser;
    }

    private void validateFields(String name, String lastName, String email){
         List<FieldError> fieldErrors = new ArrayList<>();

        GeneralValidation.validateRequiredField(name,"name").ifPresent(fieldErrors::add);
        GeneralValidation.validateRequiredField(lastName,"lastName").ifPresent(fieldErrors::add);
        GeneralValidation.validateRequiredField(email,"email").ifPresent(fieldErrors::add);
        GeneralValidation.validateRequiredField(name,"name").ifPresent(fieldErrors::add);

        Optional<FieldError> emailError = GeneralValidation.validateRequiredField(email, "email");
        if (emailError.isPresent()) {
            fieldErrors.add(emailError.get());
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            fieldErrors.add(new FieldError(
                    "email",
                    "The field 'email' must be a valid email address.",
                    FieldErrorCodeValues.INVALID_FORMAT
            ));
        }

        if(!fieldErrors.isEmpty()) {
            throw new ValidationException(fieldErrors);
        }
    }


}
