package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;
import edu.hw2.connections_problem.connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {
    private int percentOfFailure;
    private int connectionPof;
    private final static int DEFAULT_PERCENT_OF_FAILURE = 30;
    private final static int DEFAULT_CONNECTION_PERCENT_OF_FAILURE = 30;

    public DefaultConnectionManager() {
        percentOfFailure = DEFAULT_PERCENT_OF_FAILURE;
        connectionPof = DEFAULT_CONNECTION_PERCENT_OF_FAILURE;
    }

    public DefaultConnectionManager(int percentOfFailure, int connectionPof) {
        setPercentOfFailure(percentOfFailure);
        setConnectionPof(connectionPof);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public Connection getConnection() {
        int percent = (int) (Math.random() * 100);
        if (percent < percentOfFailure) {
            return new FaultyConnection(connectionPof);
        } else {
            return new StableConnection();
        }
    }

    public void setPercentOfFailure(int percentOfFailure) {
        checkPercentValue(percentOfFailure);
        this.percentOfFailure = percentOfFailure;
    }

    public void setConnectionPof(int connectionPof) {
        checkPercentValue(connectionPof);
        this.connectionPof = connectionPof;
    }

    @SuppressWarnings("MagicNumber")
    private void checkPercentValue(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent value must be in range [0..100]!");
        }
    }
}
