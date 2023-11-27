package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonInMemoryDatabase implements PersonDatabase {

    private final Map<Integer, Person> indexById;
    private final Map<String, List<Person>> indexByName;
    private final Map<String, List<Person>> indexByAddress;
    private final Map<String, List<Person>> indexByPhone;

    public PersonInMemoryDatabase() {
        indexById = new HashMap<>();
        indexByName = new HashMap<>();
        indexByAddress = new HashMap<>();
        indexByPhone = new HashMap<>();
    }

    @Override
    public synchronized void add(Person person) {
        indexById.putIfAbsent(person.id(), person);
        indexByName.computeIfAbsent(person.name(), v -> new ArrayList<>()).add(person);
        indexByAddress.computeIfAbsent(person.address(), v -> new ArrayList<>()).add(person);
        indexByPhone.computeIfAbsent(person.phoneNumber(), v -> new ArrayList<>()).add(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = indexById.remove(id);
        indexByName.getOrDefault(person.name(), new ArrayList<>()).remove(person);
        indexByAddress.getOrDefault(person.address(), new ArrayList<>()).remove(person);
        indexByPhone.getOrDefault(person.phoneNumber(), new ArrayList<>()).remove(person);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return indexByName.get(name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return indexByAddress.get(address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return indexByPhone.get(phone);
    }
}
