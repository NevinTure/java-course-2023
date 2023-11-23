package edu.hw7;

import edu.hw7.task1.CounterRunner;
import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.PersonInMemoryDatabase;
import edu.hw7.task3.PersonRWLockDatabase;
import edu.hw7.task4.PiApproximator;
import edu.hw7.task4.PiApproximatorMultiThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import static edu.hw7.Task2.factorial;
import static org.assertj.core.api.Assertions.assertThat;

public class HW7Test {

    @Test
    public void testConcurrentCounter() {
        //given
        int iterAmount = 100000;
        int threadsNum = 4;
        int runsNum = 10;

        //when
        List<CounterRunner> list = new ArrayList<>();
        for (int i = 0; i < runsNum; i++) {
            list.add(new CounterRunner(iterAmount, threadsNum));
            list.get(i).run();
        }

        //then
        int expectedValue = iterAmount * threadsNum;
        list.forEach(v -> assertThat(v.getCounterValue()).isEqualTo(expectedValue));
    }

    @Test
    public void testParallelFactorial() {
        //given
        long base = 15;

        //when
        long result = factorial(base);

        //then
        long expectedResult = 1307674368000L;
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testPersonInMemoryDatabase() {
        int threadsAmount = Runtime.getRuntime().availableProcessors() - 1;
        try (ExecutorService service = Executors.newFixedThreadPool(threadsAmount)) {
            //given
            Person person1 = new Person(1, "name1", "address1", "phone1");
            Person person2 = new Person(2, "name2", "address2", "phone2");
            Person person3 = new Person(3, "name3", "address3", "phone3");
            PersonDatabase db = new PersonInMemoryDatabase();

            //when
            service.submit(() -> {
                try {
                    for (int i = 4; i < 10000; i++) {
                        db.add(new Person(i, "some" + i, "addrsome" + i, "phone" + i));
                        wait(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            service.submit(() -> {
                db.add(person1);
                db.add(person2);
                db.add(person3);
            });
            service.submit(() -> {
                try {
                    for (int i = 10000; i < 50000; i++) {
                        db.add(new Person(i, "some" + i, "addrsome" + i, "phone" + i));
                        wait(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            db.delete(1);
            Future<List<Person>> findByNameResult = service.submit(() -> db.findByName("name2"));
            Future<List<Person>> findByAddressResult = service.submit(() -> db.findByAddress("address2"));
            Future<List<Person>> findByPhoneResult = service.submit(() -> db.findByPhone("phone3"));
            service.shutdown();
            boolean terminated = service.awaitTermination(1, TimeUnit.MINUTES);

            //then
            assertThat(terminated).isTrue();
            assertThat(findByNameResult.get()).contains(person2);
            assertThat(findByAddressResult.get()).contains(person2);
            assertThat(findByPhoneResult.get()).contains(person3);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPersonRWLockDatabase() {
        int threadsAmount = Runtime.getRuntime().availableProcessors() - 1;
        try (ExecutorService service = Executors.newFixedThreadPool(threadsAmount)) {
            //given
            Person person1 = new Person(1, "name1", "address1", "phone1");
            Person person2 = new Person(2, "name2", "address2", "phone2");
            Person person3 = new Person(3, "name3", "address3", "phone3");
            PersonDatabase db = new PersonRWLockDatabase();

            //when
            service.submit(() -> {
                try {
                    for (int i = 4; i < 10000; i++) {
                        db.add(new Person(i, "some" + i, "addrsome" + i, "phone" + i));
                        wait(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            service.submit(() -> {
                db.add(person1);
                db.add(person2);
                db.add(person3);
            });
            service.submit(() -> {
                try {
                    for (int i = 10000; i < 50000; i++) {
                        db.add(new Person(i, "some" + i, "addrsome" + i, "phone" + i));
                        wait(10);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            db.delete(1);
            Future<List<Person>> findByNameResult = service.submit(() -> db.findByName("name2"));
            Future<List<Person>> findByAddressResult = service.submit(() -> db.findByAddress("address2"));
            Future<List<Person>> findByPhoneResult = service.submit(() -> db.findByPhone("phone3"));
            service.shutdown();
            boolean terminated = service.awaitTermination(1, TimeUnit.MINUTES);

            //then
            assertThat(terminated).isTrue();
            assertThat(findByNameResult.get()).contains(person2);
            assertThat(findByAddressResult.get()).contains(person2);
            assertThat(findByPhoneResult.get()).contains(person3);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
      кол-во точек | время один поток | кол-во потоков/время
      10 млн                465 мсек            2/77 мсек
      10 млн                465 мсек            4/50 мсек
      10 млн                465 мсек            8/42 мсек
      100 млн               4.59 сек            2/1.35 сек
      100 млн               4.59 сек            4/760 мсек
      100 млн               4.59 сек            8/450 мсек
      1 млрд                45.7 сек            2/13 сек
      1 млрд                45.7 сек            4/7.1 сек
      1 млрд                45.7 сек            8/3.8 сек
    */
    @Test
    public void testPiApproximator() {
        //given
        int n = 1000000;

        //when
        PiApproximator app = new PiApproximator();
        double result = app.approximate(n);

        //then
        assertThat(result).isBetween(3.13, 3.15);
    }

    @Test
    public void testPiApproximatorMultiThread() {
        //given
        int n = 10000000;
        int threadsAmount = Runtime.getRuntime().availableProcessors() - 1;

        //when
        PiApproximatorMultiThread appMulti = new PiApproximatorMultiThread();
        double result = appMulti.approximate(n, threadsAmount);

        //then
        assertThat(result).isBetween(3.14, 3.143);
    }
}
