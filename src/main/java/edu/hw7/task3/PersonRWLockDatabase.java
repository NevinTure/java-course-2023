package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PersonRWLockDatabase implements PersonDatabase {

    private final Map<Integer, Person> indexById;
    private final Map<String, List<Person>> indexByName;
    private final Map<String, List<Person>> indexByAddress;
    private final Map<String, List<Person>> indexByPhone;
    private final ReadWriteLock rwLock;

    public PersonRWLockDatabase() {
        indexById = new HashMap<>();
        indexByName = new HashMap<>();
        indexByAddress = new HashMap<>();
        indexByPhone = new HashMap<>();
        rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public void add(Person person) {
        rwLock.writeLock().lock();
        try {
            indexById.putIfAbsent(person.id(), person);
            indexByName.computeIfAbsent(person.name(), v -> new ArrayList<>()).add(person);
            indexByAddress.computeIfAbsent(person.address(), v -> new ArrayList<>()).add(person);
            indexByPhone.computeIfAbsent(person.phoneNumber(), v -> new ArrayList<>()).add(person);
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    @Override
    public void delete(int id) {
        rwLock.writeLock().lock();
        try {
            Person person = indexById.remove(id);
            indexByName.getOrDefault(person.name(), new ArrayList<>()).remove(person);
            indexByAddress.getOrDefault(person.address(), new ArrayList<>()).remove(person);
            indexByPhone.getOrDefault(person.phoneNumber(), new ArrayList<>()).remove(person);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        rwLock.readLock().lock();
        try {
            return indexByName.get(name);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        rwLock.readLock().lock();
        try {
            return indexByAddress.get(address);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        rwLock.readLock().lock();
        try {
            return indexByPhone.get(phone);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
