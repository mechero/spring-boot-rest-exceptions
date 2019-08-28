package io.tpd.superheroes.controller.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SuperHeroAppError {

    @JsonIgnore
    private final UUID uniqueId;
    private final String code;
    private final String message;
    private final List<Error> errors;

    public SuperHeroAppError(final String code, final String message, final String domain,
                             final String reason, final String errorMessage, final String errorReportUri) {
        this.code = code;
        this.message = message;
        this.uniqueId = UUID.randomUUID();
        this.errors = List.of(
                new Error(domain, reason, errorMessage, errorReportUri + "?id=" + uniqueId)
        );
    }

    private SuperHeroAppError(final UUID uniqueId, final String code, final String message, final List<Error> errors) {
        this.uniqueId = uniqueId;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static SuperHeroAppError copyWithMessage(final SuperHeroAppError s, final String message) {
        return new SuperHeroAppError(s.uniqueId, s.code, message, s.errors);
    }

    public static SuperHeroAppError fromAttributeMap(final Map<String, Object> defaultErrorAttributes,
                                                     final String sendReportBaseUri) {
        // original attribute values are documented in org.springframework.boot.web.servlet.error.DefaultErrorAttributes
        return new SuperHeroAppError(
                ((Integer) defaultErrorAttributes.get("status")).toString(),
                (String) defaultErrorAttributes.getOrDefault("message", "no message available"),
                (String) defaultErrorAttributes.getOrDefault("path", "no domain available"),
                (String) defaultErrorAttributes.getOrDefault("error", "no reason available"),
                (String) defaultErrorAttributes.get("message"),
                sendReportBaseUri
        );
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Error> getErrors() {
        return errors;
    }

    private static final class Error {
        private final String domain;
        private final String reason;
        private final String message;
        private final String errorReportUri;

        public Error(final String domain, final String reason, final String message, final String errorReportUri) {
            this.domain = domain;
            this.reason = reason;
            this.message = message;
            this.errorReportUri = errorReportUri;
        }

        public String getDomain() {
            return domain;
        }

        public String getReason() {
            return reason;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorReportUri() {
            return errorReportUri;
        }
    }
}
