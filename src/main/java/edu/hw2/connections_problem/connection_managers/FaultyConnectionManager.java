package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {

    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
