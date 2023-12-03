package edu.hw8.task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final int maxClients;

    public Server(int port, int maxClients) {
        this.port = port;
        this.maxClients = maxClients;
    }

    public void start() {
        try (
            ExecutorService service = Executors.newFixedThreadPool(maxClients);
            ServerSocket server = new ServerSocket(port)) {
            server.setReuseAddress(true);
            while (true) {
                service.submit(new ServerWorker(server.accept()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
