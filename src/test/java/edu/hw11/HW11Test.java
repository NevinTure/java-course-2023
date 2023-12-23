package edu.hw11;

import edu.hw11.task2.ArithmeticUtils;
import edu.hw11.task2.ArithmeticUtilsRedefine;
import edu.hw11.task3.FibonacciByteCodeAppender;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class HW11Test {

    @Test
    public void testHelloByteBuddy() throws Exception {
        //when
        Class<?> helloClass = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        String result = helloClass.getDeclaredConstructor().newInstance().toString();

        //then
        String expectedResult = "Hello, ByteBuddy!";
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testArithmeticUtilsRedefine() {
        //when
        ByteBuddyAgent.install();
        ArithmeticUtils arithSum = new ArithmeticUtils();
        new ByteBuddy()
            .redefine(ArithmeticUtilsRedefine.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        int result = arithSum.sum(10, 10);

        //then
        int expectedResult = 100;
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void test() throws Exception {
        Class<?> fibClass = new ByteBuddy().subclass(Object.class).name("FibonacciCalc")
            .defineMethod("calculate", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameter(int.class)
            .intercept(MethodDelegation.to(new FibonacciByteCodeAppender()))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Method calcMethod = fibClass.getDeclaredMethod("calculate", int.class);
        long result = (long) calcMethod.invoke(null, 111);
        System.out.println(result);
    }
}
