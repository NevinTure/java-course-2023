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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileDataResolver {

    private final static String DEFAULT_BASE_DIR = System.getProperty("java.io.tmpdir");
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

    public static List<String> resolvePathLines(String pathStr) {
        try {
            return Files.readAllLines(Path.of(pathStr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
