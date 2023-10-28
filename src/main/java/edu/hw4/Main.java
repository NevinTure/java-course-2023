package edu.hw4;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static final int ONE_METER = 100;

    private Main() {
    }

    public static List<Animal> getByAgeAsc(List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::age))
            .toList();
    }

    public static List<Animal> getFirstKByWeightDesc(int k, List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::weight).reversed())
            .limit(k)
            .toList();
    }

    public static Map<Animal.Type, Long> getAmountByTypes(List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    public static Animal getWithLongestName(List<Animal> animals) {
        return animals
            .stream()
            .max(Comparator.comparing(animal -> animal.name().length()))
            .orElse(null);
    }

    public static Animal.Sex getMostSex(List<Animal> animals) {
        return Objects.requireNonNull(animals
                .stream()
                .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null))
            .getKey();
    }

    public static Map<Animal.Type, Animal> getThickestForEachType(List<Animal> animals) {
        return animals
            .stream()
            .collect(Collectors
                .toMap(Animal::type,
                    Function.identity(),
                    BinaryOperator.maxBy(Comparator.comparing(Animal::weight))
                ));
    }

    public static Animal getKthByAgeDesc(int k, List<Animal> animals) {
        return animals
            .stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    public static Optional<Animal> getHeaviestLowerThatKCm(int k, List<Animal> animals) {
        return animals
            .stream()
            .filter(v -> v.height() < k)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Integer countPaws(List<Animal> animals) {
        return animals
            .stream()
            .reduce(0, (v1, v2) -> v1 + v2.paws(), Integer::sum);
    }

    public static List<Animal> getAgeDifferFromPaws(List<Animal> animals) {
        return animals
            .stream()
            .filter(v -> v.paws() != v.age())
            .toList();
    }

    public static List<Animal> getWhichBytesAndHeightGreaterThan1M(List<Animal> animals) {
        return animals
            .stream()
            .filter(v -> (v.bites() == null || v.bites()) && v.height() > ONE_METER)
            .toList();
    }

    public static Integer countWeightGreaterThanHeight(List<Animal> animals) {
        return Math.toIntExact(animals
            .stream()
            .filter(v -> v.weight() > v.height())
            .count());
    }

    public static List<Animal> getWithNameContainsMoreThan2Words(List<Animal> animals) {
        return animals
            .stream()
            .filter(v -> v.name() != null && v.name().split("\\s+").length > 2)
            .toList();
    }

    public static Boolean checkIsDogWithHeightGreaterThanKCmPresent(int k, List<Animal> animals) {
        return animals
            .stream()
            .anyMatch(v -> v.height() > k && Objects.equals(v.type(), Animal.Type.DOG));
    }

    public static Integer getWeightSumOfAnimalWithAgeFromKToL(int k, int l, List<Animal> animals) {
        return animals
            .stream()
            .filter(v -> v.age() >= k && v.age() <= l)
            .reduce(0, (v1, v2) -> v1 + v2.weight(), Integer::sum);
    }

    public static List<Animal> sortByTypeThenSexThenName(List<Animal> animals) {
        return animals
            .stream()
            .sorted(
                Comparator.comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name)
            )
            .toList();
    }

    public static Boolean checkSpidersBytesMoreThanDogs(List<Animal> animals) {
        Map<Animal.Type, Integer> spidersAndDogs = animals
            .stream()
            .collect(Collectors
                .groupingBy(Animal::type,
                    Collectors.reducing(0, v1 -> (v1.bites() == null || v1.bites()) ? 1 : 0, Integer::sum)
                )
            );
        if (spidersAndDogs.get(Animal.Type.SPIDER) == null
            || spidersAndDogs.get(Animal.Type.DOG) == null) {
            return false;
        }
        return spidersAndDogs.get(Animal.Type.SPIDER) > spidersAndDogs.get(Animal.Type.DOG);
    }

    public static Animal getHeaviestFish(List<List<Animal>> listOfAnimals) {
        return listOfAnimals
            .stream()
            .flatMap(Collection::stream)
            .reduce((v1, v2) ->
                !Objects.equals(v1.type(), Animal.Type.FISH)
                    ? v2
                    : !Objects.equals(v2.type(), Animal.Type.FISH)
                    ? v1
                    : v1.weight() > v2.weight()
                    ? v1 : v2)
            .orElse(null);
    }

    public static Map<String, Set<ValidationError>> getErrorsByNames(List<Animal> animals) {
        return animals
            .stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toMap(Animal::name, ValidationError::validate),
                    map -> {
                        map.values().removeIf(Set::isEmpty);
                        return map;
                    }
                ));
    }

    public static Map<String, String> getPrintableErrorsByNames(Map<String, Set<ValidationError>> errors) {
        return errors
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                v -> v
                    .getValue()
                    .stream()
                    .map(ValidationError::toString)
                    .collect(Collectors.joining(", ")),
                (v1, v2) -> v1
            ));
    }
}