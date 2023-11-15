package edu.project3.log_entry;

import java.time.OffsetDateTime;
import java.util.Objects;

public record NginxLogEntry(String remoteAddr,
                            String remoteUser,
                            OffsetDateTime timeLocal,
                            Request request,
                            HttpStatus status,
                            long bodyBytesSend,
                            String httpReferer,
                            String httpUserAgent) {

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NginxLogEntry entry = (NginxLogEntry) o;
        return bodyBytesSend == entry.bodyBytesSend && Objects.equals(remoteAddr, entry.remoteAddr)
            && Objects.equals(remoteUser, entry.remoteUser) && Objects.equals(timeLocal, entry.timeLocal)
            && Objects.equals(request, entry.request) && Objects.equals(status, entry.status)
            && Objects.equals(httpReferer, entry.httpReferer) && Objects.equals(httpUserAgent, entry.httpUserAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            remoteAddr,
            remoteUser,
            timeLocal,
            request,
            status,
            bodyBytesSend,
            httpReferer,
            httpUserAgent
        );
    }
}
