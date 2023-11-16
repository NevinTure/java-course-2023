package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class FileCloner {

    private FileCloner() {
    }

    public static void cloneFile(Path path) {
        String origin = String.valueOf(path.getFileName());
        FileAttributes originAttrs = FileAttributes.parse(origin);
        Pattern fileCopyPattern = Pattern
            .compile("^%s — копия( \\(\\d+\\))?%s$".formatted(originAttrs.name(), originAttrs.extension()));
        int copyNum = 0;
        Path parent = path.getParent();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(parent)) {
            for (Path value : dirStream) {
                String current = String.valueOf(value.getFileName());
                if (fileCopyPattern.matcher(current).find()) {
                    copyNum++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder copyFileName = new StringBuilder();
        if (copyNum == 0) {
            copyFileName
                .append(originAttrs
                    .name())
                .append(" — копия")
                .append(originAttrs.extension());
        } else {
            copyFileName
                .append(originAttrs.name())
                .append(" — копия (")
                .append(copyNum + 1)
                .append(")")
                .append(originAttrs.extension());
        }
        try {
            Files.copy(path, Paths.get(parent.toString(), copyFileName.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
