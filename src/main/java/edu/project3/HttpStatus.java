package edu.project3;

import java.util.Objects;

public class HttpStatus {

    private int exact;
    private StatusType type;

    enum StatusType {
        INFO,
        SUCCESS,
        REDIRECT,
        CLIENT_ERROR,
        SERVER_ERROR
    }

    public HttpStatus(int exact) {
        this.exact = exact;
    }

    public int getExact() {
        return exact;
    }

    public StatusType getType() {
        if (type == null) {
            type = switch (exact % 100) {
                case 1 -> StatusType.INFO;
                case 2 -> StatusType.SUCCESS;
                case 3 -> StatusType.REDIRECT;
                case 4 -> StatusType.CLIENT_ERROR;
                case 5 -> StatusType.SERVER_ERROR;
                default -> throw new IllegalStateException("Unexpected value: " + exact % 100);
            };
        }
        return type;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpStatus that = (HttpStatus) o;
        return exact == that.exact && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exact, type);
    }
}
