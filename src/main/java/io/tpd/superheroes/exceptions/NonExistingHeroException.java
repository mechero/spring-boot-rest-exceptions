package io.tpd.superheroes.exceptions;

import io.tpd.superheroes.controller.errors.ErrorCode;

/**
 * This exception is thrown when the SuperHero can't be found in the application if searching by ID.
 *
 * @author moises.macero
 */
public class NonExistingHeroException extends RuntimeException implements ErrorCode {

    public NonExistingHeroException(final String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "NE-001";
    }
}
