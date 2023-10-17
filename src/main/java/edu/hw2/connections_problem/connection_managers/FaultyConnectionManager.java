package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {

    private static final double DEFAULT_CONNECTION_PROBABILITY_OF_FAILURE = 0.3;
    private double connectionPof;

    public FaultyConnectionManager() {
        connectionPof = DEFAULT_CONNECTION_PROBABILITY_OF_FAILURE;
    }

    public FaultyConnectionManager(double connectionPof) {
        setConnectionPof(connectionPof);
    }

    @Override
    public Connection getConnection() {
        return new FaultyConnection(connectionPof);
    }

    @SuppressWarnings("MagicNumber")
    public void setConnectionPof(double connectionPof) {
        if (connectionPof < 0 || connectionPof > 1.0) {
            throw new IllegalArgumentException("Probability value must be in range [0; 1]!");
        }
        this.connectionPof = connectionPof;
    }
}
