package edu.hw2.connections_problem.connections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command) {
        LOGGER.info(String.format("Command '%s' executed successfully!", command));
    }

    @Override
    public void close() throws Exception {
        //close logic
    }
}
