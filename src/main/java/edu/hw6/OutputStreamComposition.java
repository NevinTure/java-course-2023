package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class OutputStreamComposition {

    private OutputStreamComposition() {
    }

    //Task 4
    public static void writeToFile(String filePath, String text) {
        try (OutputStream out = new FileOutputStream(filePath);
             CheckedOutputStream checkedOut = new CheckedOutputStream(out, new CRC32());
             BufferedOutputStream bufferedCheckedOut = new BufferedOutputStream(checkedOut);
             OutputStreamWriter bufferedCheckedOutWriter = new OutputStreamWriter(bufferedCheckedOut);
             PrintWriter bufferedCheckerOutPrintWriter = new PrintWriter(bufferedCheckedOutWriter)) {

            bufferedCheckerOutPrintWriter.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
