package edu.hw2.connections_problem.connection_managers;

import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.connections.FaultyConnection;
import edu.hw2.connections_problem.connections.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {

    private int percentOfFailure;
    private int connectionPoF;
    private final static int defaultPoF = 30;
    private final static int defaultConnectionPoF = 30;

    public DefaultConnectionManager() {
        percentOfFailure = defaultPoF;
        connectionPoF = defaultConnectionPoF;
    }

    public DefaultConnectionManager(int percentOfFailure, int connectionPoF) {
        setPercentOfFailure(percentOfFailure);
        setConnectionPoF(connectionPoF);
    }

    @Override
    public Connection getConnection() {
        int percent = (int) (Math.random() * 100);
        if (percent <= percentOfFailure) {
            return new FaultyConnection(connectionPoF);
        } else {
            return new StableConnection();
        }
    }

    public void setPercentOfFailure(int percentOfFailure) {
        checkPercentValue(percentOfFailure);
        this.percentOfFailure = percentOfFailure;
    }

    public void setConnectionPoF(int connectionPoF) {
        checkPercentValue(connectionPoF);
        this.connectionPoF = connectionPoF;
    }

    private void checkPercentValue(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent value must be in range [0..100]!");
        }
    }
}
