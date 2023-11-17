package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {

    private final static Pattern TITLE_PATTERN = Pattern.compile(".*\"title\":\"(?<title>.*)\",\"type\".*");

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        String body = getBody(URI.create("https://hacker-news.firebaseio.com/v0/topstories.json"));
        if (body.isEmpty()) {
            return new long[0];
        } else {
            return parseTopNews(body);
        }
    }

    public static String news(long id) {
        String rawURI = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        String body = getBody(URI.create(rawURI));
        Matcher matcher = TITLE_PATTERN.matcher(body);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Unidentified ID!");
        }
    }

    private static String getBody(URI uri) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }

    private static long[] parseTopNews(String body) {
        String[] rawBody = body.split(",");
        rawBody[0] = rawBody[0].substring(1);
        rawBody[rawBody.length - 1] = rawBody[rawBody.length - 1].substring(1);
        return Arrays.stream(rawBody).mapToLong(Long::parseLong).toArray();
    }
}
