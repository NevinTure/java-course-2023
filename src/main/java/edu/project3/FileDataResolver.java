package edu.project3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDataResolver {

    private final static String DEFAULT_BASE_DIR = System.getProperty("java.io.tmpdir");
    private final static String FILE_PREFIX = "tempLog_";
    private final static String FILE_SUFFIX = ".txt";
    private final static Pattern START_DIR_PATTERN = Pattern.compile("([^*]*)[/\\\\]");

    public static List<String> get(String rawPath) {
        if (rawPath.contains("://")) {
            return resolveUrlLines(rawPath);
        } else {
            List<Path> paths = getPaths(rawPath);
            return resolvePathLines(paths);
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
        File tempFile;
        try (ReadableByteChannel byteChannel = Channels.newChannel(new URL(urlStr).openStream());
            FileInputStream fis = new FileInputStream(
                (tempFile = (Files.createTempFile(Path.of(DEFAULT_BASE_DIR), FILE_PREFIX, FILE_SUFFIX)).toFile())
            )
        ) {
            tempFile.deleteOnExit();
            FileChannel fileChannel = fis.getChannel();
            fileChannel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
            return Files.readAllLines(tempFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Path> getPaths(String rawPath) {
        if (new File(rawPath).exists()) {
            return List.of(Path.of(rawPath));
        }
        List<Path> paths = new ArrayList<>();
        Path start = getStartLocation(rawPath);
        String pattern = "glob:" + rawPath.replaceAll("\\\\", "/");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(pattern);
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (matcher.matches(path)) {
                        paths.add(path);
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
    public static List<String> resolvePathLines(List<Path> paths) {
        List<String> data = new ArrayList<>();
        try {
            for (Path path : paths) {
                data.addAll(Files.readAllLines(path));
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
