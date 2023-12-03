package edu.hw8;

import edu.hw8.task1.Client;
import edu.hw8.task1.Server;
import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HW8Test {

    @Test
    public void testClientServer() throws InterruptedException {
        //given
        ExecutorService service = Executors.newFixedThreadPool(4);

        //when
        List<String> result = Collections.synchronizedList(new ArrayList<>());
        service.submit(() -> {
            Server server = new Server(17890, 2);
            server.start();
        });
        Thread.sleep(100);
        service.submit(() -> {
            try (Client client = new Client("127.0.0.1", 17890)) {
                result.add(client.sendMessage("личности"));

                Thread.sleep(10);
                client.sendMessage("exit");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(100);
        service.submit(() -> {
            try (Client client = new Client("127.0.0.1", 17890)) {
                result.add(client.sendMessage("что-то"));

                Thread.sleep(10);
                client.sendMessage("exit");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(100);
        service.submit(() -> {
            try (Client client = new Client("127.0.0.1", 17890)) {
                result.add(client.sendMessage("интеллект"));

                Thread.sleep(10);
                client.sendMessage("exit");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.shutdown();
        service.awaitTermination(2, TimeUnit.SECONDS);

        //then
        List<String> expectedResult = List.of("...", "Не переходи на личности там, где их нет",
            "Чем ниже интеллект, тем громче оскорбления"
        );
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    public void testFixedThreadPool() {
        //given
        AtomicLong fib1 = new AtomicLong();
        AtomicLong fib2 = new AtomicLong();
        AtomicLong fib3 = new AtomicLong();

        try (ThreadPool pool = FixedThreadPool.create(2)) {
            //when
            pool.start();
            pool.execute(() -> fib1.addAndGet(fibonacci(14)));
            pool.execute(() -> fib2.addAndGet(fibonacci(20)));
            pool.execute(() -> fib3.addAndGet(fibonacci(33)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //then
        long fib1Expected = 377; //14
        long fib2Expected = 6765; //20
        long fib3Expected = 3524578; //33
        assertThat(fib1.get()).isEqualTo(fib1Expected);
        assertThat(fib2.get()).isEqualTo(fib2Expected);
        assertThat(fib3.get()).isEqualTo(fib3Expected);

    }

    public long fibonacci(long n) {
        long a = 1;
        long b = 1;
        long temp = 1;
        for (int i = 2; i < n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        return temp;
    }
}

