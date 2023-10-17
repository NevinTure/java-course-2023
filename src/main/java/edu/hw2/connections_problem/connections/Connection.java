package edu.hw2.connections_problem.connections;

public interface Connection extends AutoCloseable {
    void execute(String command);
}
