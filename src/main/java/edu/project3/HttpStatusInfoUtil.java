package edu.project3;

import java.util.HashMap;
import java.util.Map;

public class HttpStatusInfoUtil {

    private static final Map<Integer, String> info;

    static {
        info = new HashMap<>();
        info.put(100, "Continue");
        info.put(101, "Switching Protocols");
        info.put(200, "OK");
        info.put(201, "Created");
        info.put(204, "No Content");
        info.put(206, "Partial Content");
        info.put(300, "Multiple Choices");
        info.put(304, "Not Modified");
        info.put(305, "Use Proxy");
        info.put(307, "Temporary Redirect");
        info.put(400, "Bad Request");
        info.put(404, "Not Found");
        info.put(403, "Forbidden");
        info.put(401, "Unauthorized");
        info.put(405, "Method Not Allowed");
        info.put(408, "Request Timeout");
        info.put(500, "Internal Server Error");
        info.put(502, "Bad Gateway");
    }

    private HttpStatusInfoUtil() {}

    public static String getByCode(int code) {
        return info.getOrDefault(code,"Unknown code");
    }

}
