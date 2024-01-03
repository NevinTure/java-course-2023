package edu.project5;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccessTest {

    @Test
    public void testGetNameMethod() throws InvocationTargetException, IllegalAccessException {
        //when
        NameAccessUtils utils = new NameAccessUtils(Student.class);
        Student student = new Student("test", "surname");
        Method method = utils.getNameMethod();
        String result = (String) method.invoke(student);

        //then
        String expectedResult = "test";
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetNameMethodHandle() throws Throwable {
        //when
        NameAccessUtils utils = new NameAccessUtils(Student.class);
        Student student = new Student("test", "surname");
        MethodHandle method = utils.getNameMethodHandle();
        String result = (String) method.invoke(student);

        //then
        String expectedResult = "test";
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetNameLambdaMetafactory() {
        //when
        NameAccessUtils utils = new NameAccessUtils(Student.class);
        Student student = new Student("test", "surname");
        Function<Student, String> lambda = utils.getNameLambdaMetafactory();
        String result = lambda.apply(student);

        //then
        String expectedResult = "test";
        assertThat(result).isEqualTo(expectedResult);
    }
}
