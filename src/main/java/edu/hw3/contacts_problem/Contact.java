package edu.hw3.contacts_problem;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class Contact implements Comparable<Contact> {
    private String name;
    private String surname;

    //other fields

    public Contact() {
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int compareTo(@NotNull Contact o) {
        if (this.surname == null) {
            if (o.getSurname() == null) {
                return this.name.compareTo(o.getName());
            } else {
                return this.name.compareTo(o.getSurname());
            }
        } else {
            if (o.getSurname() == null) {
                return this.surname.compareTo(o.getName());
            } else {
                return this.surname.compareTo(o.getSurname());
            }
        }
    }

    @Override public String toString() {
        return (name != null ? name : "") +
            (name != null && surname != null ? " " : "") +
            (surname != null ? surname : "");
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(surname, contact.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
