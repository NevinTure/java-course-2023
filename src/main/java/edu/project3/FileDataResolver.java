package edu.project3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDataResolver {

    private final static String DEFAULT_TEMP_DIR = System.getProperty("java.io.tmpdir");
    private final static String FILE_PREFIX = "tempLog_";
    private final static String FILE_SUFFIX = ".txt";

    public static List<String> get(String rawPath) {
        if (rawPath.contains("://")) {
            return resolveUrlLines(rawPath);
        } else {
            return resolvePathLines(rawPath);
        }
    }

    public static List<String> getAll(List<String> rawPaths) {
        List<String> data = new ArrayList<>();
        for (String path : rawPaths) {
            data.addAll(get(path));
        }
        return data;
    }

    public static List<String> resolveUrlLines(String urlStr) {
        Path tempFile;
        List<String> lines;
        try {
            tempFile = Files.createTempFile(Path.of(DEFAULT_TEMP_DIR), FILE_PREFIX, FILE_SUFFIX);
            Files.copy(new URL(urlStr).openStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            lines = Files.readAllLines(tempFile);
            Files.delete(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static List<String> resolvePathLines(String rawPath) {
        try {
            return Files.readAllLines(Path.of(rawPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
