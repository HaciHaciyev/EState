package core.project.estate.domain.value_objects;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Phone(String phoneNumber) {

    public static final String PATTERN_FOR_AZERBAIJANI_PHONE = "^(\\+\\d{1,3}( )(\\d{2}[- ]?)(\\d{3}[- ]?)(\\d{2}[- ]?)(\\d{2}))$";
    public static final Pattern pattern = Pattern.compile(PATTERN_FOR_AZERBAIJANI_PHONE);

    public Phone {
        Objects.requireNonNull(phoneNumber);
        if (phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number should`t be blank.");
        }

        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid phone number. Phone number: %s".formatted(phoneNumber));
        }
    }
}
