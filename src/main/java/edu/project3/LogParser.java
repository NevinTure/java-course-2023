package edu.project3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class LogParser implements AutoCloseable {

    private final Pattern entryPattern = Pattern.compile("(.+)-(.+)- \\[(.+)] \"(.+)\" (\\d+) (\\d+) \"()\" ");


    public LogParser(Path path) {
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            FileChannel channel = file.getChannel();
            Files.lines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
