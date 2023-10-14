package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {

    private static final int DEFAULT_CONNECTION_PERCENT_OF_FAILURE = 30;
    private int connectionPof;

    public FaultyConnectionManager() {
        connectionPof = DEFAULT_CONNECTION_PERCENT_OF_FAILURE;
    }

    public FaultyConnectionManager(int connectionPof) {
        setConnectionPof(connectionPof);
    }

    @Override
    public Connection getConnection() {
        return new FaultyConnection(connectionPof);
    }

    @SuppressWarnings("MagicNumber")
    public void setConnectionPof(int connectionPof) {
        if (connectionPof < 0 || connectionPof > 100) {
            throw new IllegalArgumentException("Percent must be in range [0..100]!");
        }
        this.connectionPof = connectionPof;
    }
}
