package edu.hw10;

import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task2.CacheInvocationHandler;
import edu.hw10.task2.CacheProxy;
import edu.hw10.task2.FibCalculator;
import edu.hw10.task2.LoopFibCalculator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class HW10Test {

    @Test
    public void testRandomObjectGeneratorWithPOJO_NoAnnotations() {
        //when
        RandomObjectGenerator rog = new RandomObjectGenerator();
        Person person = rog.nextObject(Person.class);

        //then
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
        assertThat(person.getAddress()).isNull();
        assertThat(person.getDog()).isNull();
        assertThat(person.getAge()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    public void testRandomObjectGeneratorWithPOJO_IncludeRecord_WithAnnotations() {
        //given
        String fabricMethod = "create";

        //when
        RandomObjectGenerator rog = new RandomObjectGenerator();
        Person person = rog.nextObject(Person.class, fabricMethod);

        //then
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNotNull();
        assertThat(person.getDog()).isNotNull();
        assertThat(person.getAddress()).isNull();
        assertThat(person.getAge()).isBetween(10, 40);
    }

    @Test
    public void testRandomObjectGeneratorWithRecord_WithAnnotations() {
        //when
        RandomObjectGenerator rog = new RandomObjectGenerator();
        Dog dog = rog.nextObject(Dog.class);

        //then
        assertThat(dog.age()).isBetween((short) 1, Short.MAX_VALUE);
        assertThat(dog.name()).isNotNull();
        assertThat(dog.weight()).isBetween((byte) 1, Byte.MAX_VALUE);
    }

    @Test
    public void testCacheProxyWithInMemoryStorage() {
        //given
        FibCalculator c = new LoopFibCalculator();

        //when
        CacheInvocationHandler invocationHandler = new CacheInvocationHandler(c);
        FibCalculator proxy = CacheProxy.create(c.getClass(), invocationHandler);
        proxy.fibAsLong(5);
        proxy.fibAsLong(10);
        proxy.fibAsLong(20);
        List<Object> result = invocationHandler.getInMemoryStorage().get("fibAsLong");

        //then
        List<Object> expectedResult = List.of(5L, 55L, 6765L);
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @SneakyThrows
    @Test
    public void testCacheProxyWithCacheFile(@TempDir(cleanup = CleanupMode.ALWAYS) Path tempDir) {
        //given
        FibCalculator c = new LoopFibCalculator();
        Path cache = Files.createTempFile(tempDir, "cache", ".txt");

        //when
        CacheInvocationHandler invocationHandler = new CacheInvocationHandler(c, cache);
        FibCalculator proxy = CacheProxy.create(c.getClass(), invocationHandler);
        proxy.fibAsArray(5);
        proxy.fibAsString(10);
        proxy.fibAsString(20);
        List<String> result;
        try (Stream<String> lines = Files.lines(cache)) {
            result = lines.toList();
        }

        //then
        String expectedLine1 =
            "fibAsArray:%s".formatted(Arrays.toString(new long[] {1, 1, 2, 3, 5}));
        String expectedLine2 = "fibAsString:%s".formatted("55");
        String expectedLine3 = "fibAsString:%s".formatted("6765");
        assertThat(result.get(0)).isEqualTo(expectedLine1);
        assertThat(result.get(1)).isEqualTo(expectedLine2);
        assertThat(result.get(2)).isEqualTo(expectedLine3);
    }
}
