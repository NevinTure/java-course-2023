package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMessage(String message) {
        String reply;
        try {
            writer.println(message);
            reply = reader.readLine();
        } catch (IOException e) {
            close();
            throw new RuntimeException(e);
        }
        return reply;
    }

    @Override
    public void close() {
        try {
            if (socket != null) {
                writer.close();
                reader.close();
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
