package edu.hw3.contacts_problem;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record Contact(String name, String surname) implements Comparable<Contact> {

    @Override
    public int compareTo(@NotNull Contact o) {
        if (this.surname == null) {
            if (o.surname() == null) {
                return this.name.compareTo(o.name());
            } else {
                return this.name.compareTo(o.surname());
            }
        } else {
            if (o.surname() == null) {
                return this.surname.compareTo(o.name());
            } else {
                return this.surname.compareTo(o.surname());
            }
        }
    }

    @Override public String toString() {
        return (name != null ? name : "")
            + (name != null && surname != null ? " " : "")
            + (surname != null ? surname : "");
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(surname, contact.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
