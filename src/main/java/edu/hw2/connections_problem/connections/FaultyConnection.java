package edu.hw2.connections_problem.connections;

import edu.hw2.connections_problem.exceptions.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {

    private double probabilityOfFailure;
    private final static double DEFAULT_PROBABILITY_OF_FAILURE = 0.3;
    private final static Logger LOGGER = LogManager.getLogger();

    public FaultyConnection() {
        probabilityOfFailure = DEFAULT_PROBABILITY_OF_FAILURE;
    }

    public FaultyConnection(double probabilityOfFailure) {
        setProbabilityOfFailure(probabilityOfFailure);
    }

    @Override
    public void execute(String command) {
        double probability = Math.random();
        if (probability < probabilityOfFailure) {
            throw new ConnectionException();
        }
        LOGGER.info(String.format("Command '%s' executed successfully!", command));
    }

    @SuppressWarnings("MagicNumber")
    public void setProbabilityOfFailure(double probabilityOfFailure) {
        if (probabilityOfFailure < 0 || probabilityOfFailure > 1.0) {
            throw new IllegalArgumentException("Probability value must be in range [0; 1]!");
        }
        this.probabilityOfFailure = probabilityOfFailure;
    }

    @Override
    public void close() throws Exception {
        //close logic
    }
}
