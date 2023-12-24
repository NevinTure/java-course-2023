package edu.project5;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaMetafactoryFunc;

    @Setup
    public void setup() {
        NameAccessUtils utils = new NameAccessUtils(Student.class);
        student = new Student("name", "test");
        method = utils.getNameMethod();
        methodHandle = utils.getNameMethodHandle();
        lambdaMetafactoryFunc = utils.getNameLambdaMetafactory();
    }

//    Benchmark                                         Mode  Cnt  Score   Error  Units
//    ReflectionBenchmark.directAccess                  avgt       0,612          ns/op
//    ReflectionBenchmark.reflectionLambdaMetafactory   avgt       0,791          ns/op
//    ReflectionBenchmark.reflectionMethodAccess        avgt       6,938          ns/op
//    ReflectionBenchmark.reflectionMethodHandleAccess  avgt       3,734          ns/op
    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflectionMethodAccess(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void reflectionMethodHandleAccess(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void reflectionLambdaMetafactory(Blackhole bh) {
        String name = lambdaMetafactoryFunc.apply(student);
        bh.consume(name);
    }
}
