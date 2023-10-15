package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;
import edu.hw2.connections_problem.connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {
    private double probabilityOfFailure;
    private double connectionPof;
    private final static double DEFAULT_PROBABILITY_OF_FAILURE = 0.3;
    private final static double DEFAULT_CONNECTION_PROBABILITY_OF_FAILURE = 0.3;

    public DefaultConnectionManager() {
        probabilityOfFailure = DEFAULT_PROBABILITY_OF_FAILURE;
        connectionPof = DEFAULT_CONNECTION_PROBABILITY_OF_FAILURE;
    }

    public DefaultConnectionManager(double probabilityOfFailure, double connectionPof) {
        setProbabilityOfFailure(probabilityOfFailure);
        setConnectionPof(connectionPof);
    }

    @Override
    public Connection getConnection() {
        double probability = Math.random();
        if (probability < probabilityOfFailure) {
            return new FaultyConnection(connectionPof);
        } else {
            return new StableConnection();
        }
    }

    public void setProbabilityOfFailure(double probabilityOfFailure) {
        checkProbabilityValue(probabilityOfFailure);
        this.probabilityOfFailure = probabilityOfFailure;
    }

    public void setConnectionPof(double connectionPof) {
        checkProbabilityValue(connectionPof);
        this.connectionPof = connectionPof;
    }

    @SuppressWarnings("MagicNumber")
    private void checkProbabilityValue(double probability) {
        if (probability < 0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability value must be in range [0; 1]!");
        }
    }
}
