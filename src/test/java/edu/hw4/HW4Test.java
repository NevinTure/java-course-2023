package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static edu.hw4.Main.checkIsDogWithHeightGreaterThanKCmPresent;
import static edu.hw4.Main.checkSpidersBitesMoreThanDogs;
import static edu.hw4.Main.countPaws;
import static edu.hw4.Main.countWeightGreaterThanHeight;
import static edu.hw4.Main.getAgeDifferFromPaws;
import static edu.hw4.Main.getAmountByTypes;
import static edu.hw4.Main.getByAgeAsc;
import static edu.hw4.Main.getErrorsByNames;
import static edu.hw4.Main.getFirstKByWeightDesc;
import static edu.hw4.Main.getHeaviestFish;
import static edu.hw4.Main.getHeaviestLowerThatKCm;
import static edu.hw4.Main.getKthByAgeDesc;
import static edu.hw4.Main.getMostSex;
import static edu.hw4.Main.getPrintableErrorsByNames;
import static edu.hw4.Main.getThickestForEachType;
import static edu.hw4.Main.getWeightSumOfAnimalsWithAgeFromKToL;
import static edu.hw4.Main.getWhichBytesAndHeightGreaterThan1M;
import static edu.hw4.Main.getWithLongestName;
import static edu.hw4.Main.getWithNameContainsMoreThan2Words;
import static edu.hw4.Main.sortByTypeThenSexThenName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HW4Test {
    private static final List<Animal> basicAnimalList = List.of(
        new Animal("spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 15, 10, true),
        new Animal("cat1", Animal.Type.CAT, Animal.Sex.M, 4, 40, 100, false),
        new Animal("dog1", Animal.Type.DOG, Animal.Sex.F, 4, 50, 120, false),
        new Animal("dog2", Animal.Type.DOG, Animal.Sex.M, 3, 35, 55, true),
        new Animal("bird1", Animal.Type.BIRD, Animal.Sex.F, 2, 24, 30, false),
        new Animal("fish1", Animal.Type.FISH, Animal.Sex.F, 10, 10, 30, false),
        new Animal("spider2", Animal.Type.SPIDER, Animal.Sex.F, 1, 15, 10, true),
        new Animal("cat 2 2", Animal.Type.CAT, Animal.Sex.F, 4, 40, 43, false),
        new Animal("dog3", Animal.Type.DOG, Animal.Sex.M, 15, 16, 87, false),
        new Animal("dog4", Animal.Type.DOG, Animal.Sex.M, 4, 190, 23, true),
        new Animal("bird2", Animal.Type.BIRD, Animal.Sex.M, 2, 44, 54, false),
        new Animal("fish2", Animal.Type.FISH, Animal.Sex.M, 1, 10, 61, false),
        new Animal("spider 3 3", Animal.Type.SPIDER, Animal.Sex.F, 8, 77, 51, true),
        new Animal("cat3", Animal.Type.CAT, Animal.Sex.M, 4, 500, 91, false),
        new Animal("dog5", Animal.Type.DOG, Animal.Sex.M, 29, 30, 123, false),
        new Animal("dog6", Animal.Type.DOG, Animal.Sex.F, 4, 33, 12, false),
        new Animal("bird3", Animal.Type.BIRD, Animal.Sex.F, 2, 2, 41, false),
        new Animal("fish with longest name", Animal.Type.FISH, Animal.Sex.F, 13, 22, 15, false),
        new Animal("spider4", Animal.Type.SPIDER, Animal.Sex.M, 11, 90, 61, true),
        new Animal("cat4", Animal.Type.CAT, Animal.Sex.M, 90, 73, 112, false),
        new Animal("dog7", Animal.Type.DOG, Animal.Sex.F, 77, 87, 142, false),
        new Animal("dog8", Animal.Type.DOG, Animal.Sex.M, 21, 199, 11, true),
        new Animal("bird4", Animal.Type.BIRD, Animal.Sex.M, 2, 21, 61, false),
        new Animal("fish4", Animal.Type.FISH, Animal.Sex.F, 20, 2, 41, false)
    );

    private final static List<Animal> wrongAnimals = List.of(
        new Animal("test1", null, Animal.Sex.M, -10, 10, 10, null),
        new Animal("test$", Animal.Type.DOG, null, 1, -10, -10, true),
        new Animal("test3", Animal.Type.BIRD, Animal.Sex.F, 1, 1, 1, true)
    );


    @Test
    public void testGetByAgeAsc() {
        //given
        List<Animal> animals = List.of(
            new Animal("spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 15, 10, true),
            new Animal("cat1", Animal.Type.CAT, Animal.Sex.M, 2, 40, 100, false),
            new Animal("dog1", Animal.Type.DOG, Animal.Sex.F, 5, 50, 120, false),
            new Animal("dog2", Animal.Type.DOG, Animal.Sex.M, 3, 35, 55, true)
        );

        //when
        List<Animal> result = getByAgeAsc(animals);

        //then
        assertThat(result).isEqualTo(List.of(
            new Animal("spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 15, 10, true),
            new Animal("cat1", Animal.Type.CAT, Animal.Sex.M, 2, 40, 100, false),
            new Animal("dog2", Animal.Type.DOG, Animal.Sex.M, 3, 35, 55, true),
            new Animal("dog1", Animal.Type.DOG, Animal.Sex.F, 5, 50, 120, false)
        ));
    }

    @Test
    void testGetFirstKByWeightDesc() {
        //given
        int k = 3;

        //when
        List<Animal> result = getFirstKByWeightDesc(k, basicAnimalList);

        //then
        assertThat(result).isEqualTo(List.of(
            new Animal("dog7", Animal.Type.DOG, Animal.Sex.F, 77, 87, 142, false),
            new Animal("dog5", Animal.Type.DOG, Animal.Sex.M, 29, 30, 123, false),
            new Animal("dog1", Animal.Type.DOG, Animal.Sex.F, 4, 50, 120, false)
        ));
    }

    @Test
    void testGetAmountByTypes() {
        //when
        Map<Animal.Type, Long> result = getAmountByTypes(basicAnimalList);

        //then
        assertThat(result).isEqualTo(Map.of(
            Animal.Type.CAT, 4L,
            Animal.Type.FISH, 4L,
            Animal.Type.BIRD, 4L,
            Animal.Type.SPIDER, 4L,
            Animal.Type.DOG, 8L
        ));
    }

    @Test
    void testGetWithLongestName() {
        //when
        Animal result = getWithLongestName(basicAnimalList);

        //then
        assertThat(result.name()).isEqualTo("fish with longest name");
    }

    @Test
    void testGetMostSex() {
        //when
        Animal.Sex result = getMostSex(basicAnimalList);

        //then
        assertThat(result).isEqualTo(Animal.Sex.M);
    }

    @Test
    void testGetThickestForEachType() {
        //when
        Map<Animal.Type, Animal> thickestAnimals = getThickestForEachType(basicAnimalList);

        //then
        assertThat(thickestAnimals).isEqualTo(Map.of(
            Animal.Type.CAT, new Animal("cat4", Animal.Type.CAT, Animal.Sex.M, 90, 73, 112, false),
            Animal.Type.FISH, new Animal("fish2", Animal.Type.FISH, Animal.Sex.M, 1, 10, 61, false),
            Animal.Type.DOG, new Animal("dog7", Animal.Type.DOG, Animal.Sex.F, 77, 87, 142, false),
            Animal.Type.SPIDER, new Animal("spider4", Animal.Type.SPIDER, Animal.Sex.M, 11, 90, 61, true),
            Animal.Type.BIRD, new Animal("bird4", Animal.Type.BIRD, Animal.Sex.M, 2, 21, 61, false)

        ));
    }

    @Test
    void testGetKthByAgeDesc() {
        //given
        int k = 6;

        //when
        Animal result = getKthByAgeDesc(k, basicAnimalList);

        //then
        assertThat(result).isEqualTo(
            new Animal("dog3", Animal.Type.DOG, Animal.Sex.M, 15, 16, 87, false)
        );
    }

    @Test
    void testGetHeaviestLowerThatKCm() {
        //given
        int k = 50;

        //when
        Optional<Animal> result = getHeaviestLowerThatKCm(k, basicAnimalList);

        //then
        assertThat(result).isEqualTo(Optional.of(
            new Animal("dog5", Animal.Type.DOG, Animal.Sex.M, 29, 30, 123, false)
        ));
    }

    @Test
    void testCountPaws() {
        //when
        Integer paws = countPaws(basicAnimalList);

        //then
        Integer expectedResult = 8 * 4 + 4 * 4 + 4 * 2 + 4 * 0 + 4 * 8;
        assertThat(paws).isEqualTo(expectedResult);
    }

    @Test
    void testGetAgeDifferFromPaws() {
        //when
        List<Animal> result = getAgeDifferFromPaws(basicAnimalList);

        //then
        assertThat(result).isEqualTo(List.of(
            new Animal("spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 15, 10, true),
            new Animal("dog2", Animal.Type.DOG, Animal.Sex.M, 3, 35, 55, true),
            new Animal("fish1", Animal.Type.FISH, Animal.Sex.F, 10, 10, 30, false),
            new Animal("spider2", Animal.Type.SPIDER, Animal.Sex.F, 1, 15, 10, true),
            new Animal("dog3", Animal.Type.DOG, Animal.Sex.M, 15, 16, 87, false),
            new Animal("fish2", Animal.Type.FISH, Animal.Sex.M, 1, 10, 61, false),
            new Animal("dog5", Animal.Type.DOG, Animal.Sex.M, 29, 30, 123, false),
            new Animal("fish with longest name", Animal.Type.FISH, Animal.Sex.F, 13, 22, 15, false),
            new Animal("spider4", Animal.Type.SPIDER, Animal.Sex.M, 11, 90, 61, true),
            new Animal("cat4", Animal.Type.CAT, Animal.Sex.M, 90, 73, 112, false),
            new Animal("dog7", Animal.Type.DOG, Animal.Sex.F, 77, 87, 142, false),
            new Animal("dog8", Animal.Type.DOG, Animal.Sex.M, 21, 199, 11, true),
            new Animal("fish4", Animal.Type.FISH, Animal.Sex.F, 20, 2, 41, false)
        ));
    }

    @Test
    void testGetWhichBytesAndHeightGreaterThan1M() {
        //when
        List<Animal> result = getWhichBytesAndHeightGreaterThan1M(basicAnimalList);

        //then
        assertThat(result).isEqualTo(List.of(
            new Animal("dog4", Animal.Type.DOG, Animal.Sex.M, 4, 190, 23, true),
            new Animal("dog8", Animal.Type.DOG, Animal.Sex.M, 21, 199, 11, true)
        ));
    }

    @Test
    void testCountWeightGreaterThanHeight() {
        //when
        Integer counter = countWeightGreaterThanHeight(basicAnimalList);

        //then
        Integer expectedResult = 15;
        assertThat(counter).isEqualTo(expectedResult);
    }

    @Test
    void testGetWithNameContainsMoreThan2Words() {
        //when
        List<Animal> result = getWithNameContainsMoreThan2Words(basicAnimalList);

        //then
        assertThat(result).isEqualTo(List.of(
            new Animal("cat 2 2", Animal.Type.CAT, Animal.Sex.F, 4, 40, 43, false),
            new Animal("spider 3 3", Animal.Type.SPIDER, Animal.Sex.F, 8, 77, 51, true),
            new Animal("fish with longest name", Animal.Type.FISH, Animal.Sex.F, 13, 22, 15, false)
        ));
    }

    @Test
    void testCheckIsDogWithHeightGreaterThanKCmPresent() {
        //given
        int k = 55;

        //when
        boolean check = checkIsDogWithHeightGreaterThanKCmPresent(k, basicAnimalList);

        //then
        assertThat(check).isTrue();
    }

    @Test
    void testGetWeightSumOfAnimalWithAgeFromKToL() {
        //given
        int k = 3;
        int l = 20;

        //when
        Map<Animal.Type, Integer> sums = getWeightSumOfAnimalsWithAgeFromKToL(k, l, basicAnimalList);

        //then
        Map<Animal.Type, Integer> result = Map.of(
            Animal.Type.CAT, 100 + 43 + 91,
            Animal.Type.DOG, 120 + 55 + 87 + 23 + 12,
            Animal.Type.FISH, 30 + 15 + 41,
            Animal.Type.SPIDER, 51 + 61);
        assertThat(sums).isEqualTo(result);
    }

    @Test
    void testSortByTypeThenSexThenName() {
        //given
        List<Animal> animals = List.of(
            new Animal("b", Animal.Type.CAT, Animal.Sex.F, 1, 15, 10, true),
            new Animal("b", Animal.Type.DOG, Animal.Sex.F, 1, 15, 10, true),
            new Animal("a", Animal.Type.CAT, Animal.Sex.M, 1, 15, 10, true),
            new Animal("b", Animal.Type.CAT, Animal.Sex.M, 1, 15, 10, true)
        );

        //when
        List<Animal> sorted = sortByTypeThenSexThenName(animals);

        //then
        assertThat(sorted).isEqualTo(List.of(
            new Animal("a", Animal.Type.CAT, Animal.Sex.M, 1, 15, 10, true),
            new Animal("b", Animal.Type.CAT, Animal.Sex.M, 1, 15, 10, true),
            new Animal("b", Animal.Type.CAT, Animal.Sex.F, 1, 15, 10, true),
            new Animal("b", Animal.Type.DOG, Animal.Sex.F, 1, 15, 10, true)
        ));
    }

    @Test
    void testCheckSpidersBitesMoreThanDogs() {
        //when
        boolean checker = checkSpidersBitesMoreThanDogs(basicAnimalList);

        //then
        assertThat(checker).isTrue();
    }

    @Test
    void testGetHeaviestFish() {
        //given
        Animal heaviestFish = new Animal("Shark", Animal.Type.FISH, Animal.Sex.F, 40, 120, 100, true);
        List<Animal> anotherAnimalList = List.of(
            new Animal("Goldy", Animal.Type.FISH, Animal.Sex.M, 12, 55, 11, false),
            new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 3, 20, 10, false)
        );
        List<Animal> listWithHeaviestFish = List.of(heaviestFish);
        List<List<Animal>> allAnimals = List.of(basicAnimalList, listWithHeaviestFish, anotherAnimalList, wrongAnimals);

        //when
        Animal result = getHeaviestFish(allAnimals);

        //then
        assertThat(result).isSameAs(heaviestFish);

    }

    @Test
    void testGetErrorsByNames() {
        //when
        Map<String, Set<ValidationError>> errors = getErrorsByNames(wrongAnimals);

        //then
        Set<ValidationError> exceptedErrors1 = Set.of(
            new ValidationError(ErrorField.TYPE),
            new ValidationError(ErrorField.AGE)
        );
        assertThat(errors.containsKey("test1")).isTrue();
        assertThat(exceptedErrors1.equals(errors.get("test1"))).isTrue();
        Set<ValidationError> expectedErrors2 = Set.of(
            new ValidationError(ErrorField.NAME),
            new ValidationError(ErrorField.SEX),
            new ValidationError(ErrorField.HEIGHT),
            new ValidationError(ErrorField.WEIGHT)
        );
        assertThat(errors.containsKey("test$")).isTrue();
        assertThat(expectedErrors2.equals(errors.get("test$"))).isTrue();
    }

    @Test
    void testGetPrintableErrorsByNames() {
        //given
        Map<String, Set<ValidationError>> errors = getErrorsByNames(wrongAnimals);

        //when
        Map<String, String> printableAnimalErrors = getPrintableErrorsByNames(errors);

        //then
        assertThat(printableAnimalErrors.containsKey("test1")).isTrue();
        assertThat(printableAnimalErrors.get("test1")).isEqualTo("TYPE, AGE");
        assertThat(printableAnimalErrors.containsKey("test$")).isTrue();
        assertThat(printableAnimalErrors.get("test$")).isEqualTo("NAME, SEX, HEIGHT, WEIGHT");
    }
}
