package edu.project3;

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
        return switch (exact % 100) {
            case 1 -> StatusType.INFO;
            case 2 -> StatusType.SUCCESS;
            case 3 -> StatusType.REDIRECT;
            case 4 -> StatusType.CLIENT_ERROR;
            case 5 -> StatusType.SERVER_ERROR;
            default -> throw new IllegalStateException("Unexpected value: " + exact % 100);
        };
    }
}
