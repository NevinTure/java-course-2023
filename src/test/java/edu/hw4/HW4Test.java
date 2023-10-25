package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.List;
import static edu.hw4.Main.checkSpidersBytesMoreThanDogs;

public class HW4Test {

    @Test
    public void test() {
        List<Animal> animals = List.of(
            new Animal("kek", Animal.Type.SPIDER,  Animal.Sex.M,1,1,1,true ));
        System.out.println(checkSpidersBytesMoreThanDogs(animals));
    }
}
