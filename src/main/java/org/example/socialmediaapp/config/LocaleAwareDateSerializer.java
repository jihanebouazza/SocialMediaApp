package org.example.socialmediaapp.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocaleAwareDateSerializer extends JsonSerializer<LocalDateTime> {
    private final MessageSource messageSource;

    public LocaleAwareDateSerializer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider sp) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 1. Get current request locale (from Accept-Language)
        Locale locale = LocaleContextHolder.getLocale();

        // 2. Read pattern from messages.properties or messages_fr.properties
        String pattern = messageSource.getMessage("date.format", null, locale);

        // 3. Format using the locale & pattern
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern, locale);

        gen.writeString(value.format(fmt));
    }
}
