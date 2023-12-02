package edu.hw8;

import edu.hw8.task1.Client;
import edu.hw8.task1.Server;
import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class HW8Test {

    @Test
    public void test() {
        try (RandomAccessFile file = new RandomAccessFile("./src/test/java/edu/hw8/test", "r")) {
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            StringBuilder builder = new StringBuilder();
            while (channel.read(buffer) != -1) {
                buffer.flip();
            }
            System.out.println(buffer.position());
            System.out.println(new String(buffer.array()).trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.submit(() -> {
            Server server = new Server(17890, 2);
            server.start();
        });
        Thread.sleep(1000);
        service.submit(() -> {
            Client client = new Client("127.0.0.1", 17890);
            System.out.println(client.sendMessage("kek"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(client.sendMessage("exit"));
        });
        Thread.sleep(1000);
        service.submit(() -> {
            Client client = new Client("127.0.0.1", 17890);
            System.out.println(client.sendMessage("kek"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(client.sendMessage("exit"));
        });
        Thread.sleep(1000);
        service.submit(() -> {
            Client client = new Client("127.0.0.1", 17890);
            System.out.println(client.sendMessage("kek"));
        });
        service.shutdown();
        service.awaitTermination(20, TimeUnit.SECONDS);
    }

    @Test
    public void test3() {
        try (ThreadPool pool = FixedThreadPool.create(2)) {
            pool.start();
            for (int j = 0; j < 10; j++) {
                pool.execute(() -> {
                    for (int i = 0; i < 10; i++) {
                        if (i == 5) {
                            throw new RuntimeException();
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(i);
                    }
                });
            }
            System.out.println("end");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test4() {
        try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
            for (int j = 0; j < 10; j++) {
                pool.execute(() -> {
                    for (int i = 0; i < 10; i++) {
                        if (i == 5) {
                            throw new RuntimeException();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(i);
                    }
                    System.out.println("done " + Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
