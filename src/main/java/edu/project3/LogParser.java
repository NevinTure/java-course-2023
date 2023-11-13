package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Pattern ENTRY_PATTERN = Pattern
        .compile("(?<ip>\\S+)" +
            " - (?<remoteUser>\\S+) " +
            "\\[(?<dateTime>[\\s\\S]+)] " +
            "\"(?<method>[A-Z]+) " +
            "(?<uri>\\S*) " +
            "(?<protocol>\\S+)\" " +
            "(?<status>\\d+) " +
            "(?<bytes>\\d+) " +
            "\"(?<referer>\\S+)\" " +
            "\"(?<userAgent>[\\S\\s]+)\"");

    public static NginxLogEntry parse(String logStr) {
        Matcher matcher = ENTRY_PATTERN.matcher(logStr);
        if (matcher.find()) {
            return new LogBuilder()
                .remoteAddress(matcher.group("ip"))
                .remoteUser(matcher.group("remoteUser"))
                .timeLocal(matcher.group("dateTime"))
                .request(
                    matcher.group("method"),
                    matcher.group("uri"),
                    matcher.group("protocol"))
                .status(matcher.group("status"))
                .bodyBytesSend(matcher.group("bytes"))
                .httpReferer(matcher.group("referer"))
                .httpUserAgent(matcher.group("userAgent"))
                .build();
        } else {
            throw new RuntimeException("Invalid log entry: " + logStr);
        }
    }

    public static List<NginxLogEntry> parseAll(List<String> logStrs) {
        return logStrs
            .stream()
            .map(LogParser::parse)
            .toList();
    }

    private static class LogBuilder {
        private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        //Z ZZ XX
        private String remoteAddr;
        private String remoteUser;
        private OffsetDateTime timeLocal;
        private Request request;
        private HttpStatus status;
        private long bodyBytesSend;
        private String httpReferer;
        private String httpUserAgent;

        LogBuilder remoteAddress(String remoteAddr) {
            this.remoteAddr = remoteAddr;
            return this;
        }

        LogBuilder remoteUser(String remoteUser) {
            this.remoteUser = remoteUser;
            return this;
        }

        LogBuilder timeLocal(String timeLocalStr) {
            this.timeLocal = OffsetDateTime.parse(timeLocalStr, TIME_FORMAT);
            return this;
        }

        LogBuilder request(String method, String uri, String protocol) {
            this.request = new Request(HttpMethod.valueOf(method), uri, protocol);
            return this;
        }

        LogBuilder status(String status) {
            this.status = new HttpStatus(Integer.parseInt(status));
            return this;
        }

        LogBuilder bodyBytesSend(String bytesStr) {
            this.bodyBytesSend = Long.parseLong(bytesStr);
            return this;
        }

        LogBuilder httpReferer(String httpReferer) {
            this.httpReferer = httpReferer;
            return this;
        }

        LogBuilder httpUserAgent(String userAgent) {
            this.httpUserAgent = userAgent;
            return this;
        }

        NginxLogEntry build() {
            return new NginxLogEntry(remoteAddr,
                remoteUser,
                timeLocal,
                request,
                status,
                bodyBytesSend,
                httpReferer,
                httpUserAgent);
        }
    }
}
