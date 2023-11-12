package edu.project3;

import java.time.OffsetDateTime;

public record NginxLogEntry(String remoteAddr,
                            String remoteUser,
                            OffsetDateTime timeLocal,
                            Request request,
                            HttpStatus status,
                            long bodyBytesSend,
                            String httpReferer,
                            String httpUserAgent) {

}
