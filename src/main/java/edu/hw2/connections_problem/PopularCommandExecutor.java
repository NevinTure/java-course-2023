package edu.hw2.connections_problem;

import edu.hw2.connections_problem.connection_managers.ConnectionManager;
import edu.hw2.connections_problem.connections.Connection;
import edu.hw2.connections_problem.exceptions.ConnectionException;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        int attempts = 0;
        Throwable cause = null;
        while (attempts < maxAttempts) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                break;
            } catch (ConnectionException e) {
                cause = e;
                attempts++;
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception!");
            }
        }
        if (attempts >= maxAttempts) {
            throw new ConnectionException(cause);
        }
    }
}
