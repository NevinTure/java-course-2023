package edu.project3;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathResolver {
    private final static Pattern START_DIR_PATTERN = Pattern.compile("([^*]*)[/\\\\]");

    public static List<String> get(String rawPath) {
        if (rawPath.contains("://")) {
            return List.of(rawPath);
        } else {
            return getPaths(rawPath);
        }
    }

    public static List<String> getAll(List<String> rawPaths) {
        List<String> paths = new ArrayList<>();
        for (String rawPath : rawPaths) {
            paths.addAll(get(rawPath));
        }
        return paths;
    }

    private static List<String> getPaths(String rawPath) {
        if (new File(rawPath).exists()) {
            return List.of(rawPath);
        }
        List<String> paths = new ArrayList<>();
        Path start = getStartLocation(rawPath);
        String pattern = "glob:" + rawPath.replaceAll("\\\\", "/");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (matcher.matches(path)) {
                        paths.add(path.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return paths;
    }

    private static Path getStartLocation(String pathStr) {
        Matcher matcher = START_DIR_PATTERN.matcher(pathStr);
        matcher.find();
        return Path.of(matcher.group(1));
    }
}
