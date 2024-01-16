package edu.project3.log_entry;

import java.util.Objects;

public class Request {

    private final HttpMethod method;
    private final String urn;
    private final String protocol;

    public Request(HttpMethod method, String urn, String protocol) {
        this.method = method;
        this.urn = urn;
        this.protocol = protocol;
    }

    public String getUrn() {
        return urn;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return method == request.method && Objects.equals(urn, request.urn)
            && Objects.equals(protocol, request.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urn, protocol);
    }
}
