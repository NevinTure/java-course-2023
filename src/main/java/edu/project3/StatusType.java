package edu.project3;

public enum StatusType {

    INFO(100),
    SUCCESS(200),
    REDIRECT(300),
    CLIENT_ERROR(400),
    SERVER_ERROR(500);

    StatusType(int status) {
    }
}
