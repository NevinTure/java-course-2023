package edu.project3.log_util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("MagicNumber")
public class HttpStatusInfoUtil {

    private static final Map<Integer, String> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put(100, "Continue");
        INFO.put(101, "Switching Protocols");
        INFO.put(200, "OK");
        INFO.put(201, "Created");
        INFO.put(204, "No Content");
        INFO.put(206, "Partial Content");
        INFO.put(300, "Multiple Choices");
        INFO.put(304, "Not Modified");
        INFO.put(305, "Use Proxy");
        INFO.put(307, "Temporary Redirect");
        INFO.put(400, "Bad Request");
        INFO.put(404, "Not Found");
        INFO.put(403, "Forbidden");
        INFO.put(401, "Unauthorized");
        INFO.put(405, "Method Not Allowed");
        INFO.put(408, "Request Timeout");
        INFO.put(500, "Internal Server Error");
        INFO.put(502, "Bad Gateway");
    }

    private HttpStatusInfoUtil() {}

    public static String getByCode(int code) {
        return INFO.getOrDefault(code, "Unknown code");
    }

}
