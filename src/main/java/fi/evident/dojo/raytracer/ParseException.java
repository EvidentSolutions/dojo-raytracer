package fi.evident.dojo.raytracer;

import org.jetbrains.annotations.NotNull;

class ParseException extends RuntimeException {
    public ParseException(int pos, @NotNull String message) {
        super(pos + ": " + message);
    }
}
