package edu.hw6;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters {

    private Filters() {
    }

    public static AbstractFilter regularFile = Files::isRegularFile;
    public static AbstractFilter readable = Files::isReadable;
    public static AbstractFilter writable = Files::isWritable;
    public static AbstractFilter symbolicLink = Files::isSymbolicLink;

    public static AbstractFilter largerThan(long size) {
        return (t) -> {
            try (FileChannel channel = FileChannel.open(t)) {
                return channel.size() > size;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static AbstractFilter magicNumber(int... nums) {
        return (t) -> {
            try (FileChannel channel = FileChannel.open(t)) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(nums.length);
                channel.read(byteBuffer);
                byte[] bytes = byteBuffer.array();
                int[] array = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    array[i] = Byte.toUnsignedInt(bytes[i]);
                }
                return Arrays.equals(nums, array);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return (t) -> {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
            return matcher.matches(t.getFileName());
        };
    }

    public static AbstractFilter extensionMatches(String extensionStr) {
        return (t) -> {
            String name = String.valueOf(t.getFileName());
            if (name.contains(".")) {
                Pattern pattern = Pattern.compile(".*\\.(.*)");
                Matcher matcher = pattern.matcher(name);
                matcher.find();
                return Objects.equals(extensionStr, matcher.group(1));
            } else {
                return extensionStr.isEmpty();
            }
        };
    }

    public static AbstractFilter regexContains(String regex) {
        return (t) -> {
            String name = String.valueOf(t.getFileName());
            Pattern pattern = Pattern.compile(".*" + regex + ".*");
            return pattern.matcher(name).find();
        };
    }
}
