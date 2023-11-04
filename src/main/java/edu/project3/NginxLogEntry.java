package edu.project3;

import java.time.OffsetDateTime;

public class NginxLogEntry {

    private String remoteAddr;
    private String remoteUser;
    private OffsetDateTime timeLocal;
    private Request request;
    private HttpStatus status;
    private long bodyBytesSend;
    private String httpReferer;
    private String httpUserAgent;
}
