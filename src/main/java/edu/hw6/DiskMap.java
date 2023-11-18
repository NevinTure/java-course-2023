package edu.hw6;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {

    private final Path parentDir;
    private final Map<String, String> storage;

    public DiskMap(Path parentDir) {
        this.parentDir = parentDir;
        createDirIfAbsent(parentDir);
        storage = new HashMap<>();
        init();
    }

    private void createDirIfAbsent(Path dir) {
        if (!Files.exists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void init() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(parentDir)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    String name = String.valueOf(path.getFileName());
                    try (Stream<String> lines = Files.lines(path)) {
                        storage.put(name, lines.collect(Collectors.joining()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return storage.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return storage.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return storage.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        String oldValue = storage.getOrDefault(key, null);
        storage.put(key, value);
        putOnDisk(key, value);
        return oldValue;
    }

    private void putOnDisk(String key, String value) {
        Path path = Paths.get(parentDir.toString(), key);
        try {
            Files.createFile(path);
            InputStream in = new ByteArrayInputStream(value.getBytes());
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String remove(Object key) {
        if (storage.containsKey((String) key)) {
            String oldValue = storage.get(key);
            removeFromDisk(key);
            return oldValue;
        }
        return null;
    }

    private void removeFromDisk(Object key) {
        Path path = Paths.get(parentDir.toString(), (String) key);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (String key : storage.keySet()) {
            removeFromDisk(key);
        }
        storage.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return storage.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return storage.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return storage.entrySet();
    }

    public void download(Path path) {
        if (!Objects.equals(path.getParent(), parentDir)) {
            throw new IllegalArgumentException("Files only from " + parentDir + " supported!");
        }
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("Directories download is not supported!");
        }
        try (Stream<String> lines = Files.lines(path)) {
            String key = String.valueOf(path.getFileName());
            String value = lines.collect(Collectors.joining());
            storage.put(key, value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadAll(Collection<Path> paths) {
        for (Path path : paths) {
            download(path);
        }
    }

    @Override public String toString() {
        return "["
        + storage
            .entrySet()
            .stream()
            .map((v) -> v.getKey() + "=" + v.getValue())
            .collect(Collectors.joining(","))
        + "]";
    }
}
