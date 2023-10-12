package edu.hw2.connections_problem.connections;

import edu.hw2.connections_problem.exceptions.ConnectionException;

public class FaultyConnection implements Connection {

    private int percentOfFailure;
    private final static int defaultPoF = 30;

    public FaultyConnection() {
        percentOfFailure = defaultPoF;
    }

    public FaultyConnection(int percentOfFailure) {
        setPercentOfFailure(percentOfFailure);
    }

    @Override
    public void execute(String command) {
        int percent = (int) (Math.random() * 100);
        if (percent <= percentOfFailure) {
            throw new ConnectionException();
        }
    }

    public void setPercentOfFailure(int percentOfFailure) {
        if (percentOfFailure < 0 || percentOfFailure > 100) {
            throw new IllegalArgumentException("Percent value must be in range [0..100]!");
        }
        this.percentOfFailure = percentOfFailure;
    }

    @Override
    public void close() throws Exception {
        //close logic
    }
}
