package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("EmptyBlock")
public class PortScanner {

    private final List<PortInfo> occupiedPorts;
    private final static int FIRST_PORT_NUMBER = 0;
    private final static int LAST_PORT_NUMBER = 49151;
    private final static int AMOUNT_OF_SPACES = 7;

    public PortScanner() {
        occupiedPorts = new ArrayList<>();
    }

    public void scanAllPorts() {
        for (int i = FIRST_PORT_NUMBER; i <= LAST_PORT_NUMBER; i++) {
            checkTCPPort(i);
            checkUDPPort(i);
        }
    }

    private void checkTCPPort(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
        } catch (IOException e) {
            occupiedPorts.add(new PortInfo(Protocol.TCP, port, PortInfoUtil.getByPortNum(port)));
        }
    }

    private void checkUDPPort(int port) {
        try (DatagramSocket socket = new DatagramSocket(port)) {
        } catch (IOException e) {
            occupiedPorts.add(new PortInfo(Protocol.UDP, port, PortInfoUtil.getByPortNum(port)));
        }
    }

    public List<PortInfo> getOccupiedPorts() {
        return occupiedPorts;
    }

    public String printOccupiedPorts() {
        if (occupiedPorts.isEmpty()) {
            scanAllPorts();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Протокол  Порт   Сервис\n");
        occupiedPorts.forEach(v -> builder
            .append(v.protocol())
            .append("       ")
            .append(v.port())
            .append(" ".repeat(AMOUNT_OF_SPACES - Integer.toString(v.port()).length()))
            .append(v.info())
            .append("\n"));
        return builder.toString();
    }
}
