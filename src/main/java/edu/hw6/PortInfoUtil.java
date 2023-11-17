package edu.hw6;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class PortInfoUtil {

    private final static Map<Integer, String> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put(80, "HTTP (HyperText Transfer Protocol)");
        INFO.put(21, "FTP (File Transfer Protocol)");
        INFO.put(25, "SMTP (Simple Mail Transfer Protocol)");
        INFO.put(22, "SSH (Secure Shell)");
        INFO.put(443, "HTTPS (HyperText Transfer Protocol Secure)");
        INFO.put(53, "DNS (Domain Name System)");
        INFO.put(3306, "MySQL Database");
        INFO.put(5432, "PostgreSQL Database");
        INFO.put(3389, "Remote Desktop Protocol (RDP)");
        INFO.put(27017, "MongoDB Database");
        INFO.put(1521, "Oracle Database");
        INFO.put(49152, "Windows RPC (Remote Procedure Call)");
        INFO.put(5353, "mDNS (Multicast Domain Name System)");
        INFO.put(5672, "AMQP (Advanced Message Queuing Protocol)");
        INFO.put(5355, "LLMNR (Link-Local Multicast Name Resolution)");
        INFO.put(49153, "Windows RPC (Remote Procedure Call)");
        INFO.put(23, "Telnet protocol to ensure effective communication along with the remote server");
        INFO.put(110, "Post Office Protocol version 3 for email retrieval");
        INFO.put(143, "Internet Message Access Protocol for email retrieval");
        INFO.put(67, "Dynamic Host Configuration Protocol for IP address allocation");
        INFO.put(68, "Dynamic Host Configuration Protocol for IP address allocation");
        INFO.put(123, "Network Time Protocol for time synchronization");
        INFO.put(161, "Simple Network Management Protocol");
        INFO.put(162, "Simple Network Management Protocol");
        INFO.put(445, "Server Message Block protocol for file sharing and printer sharing");
        INFO.put(548, "Apple Filing Protocol for file sharing between Macs");
        INFO.put(137, "NetBIOS protocol for network communication between Windows devices");
        INFO.put(138, "NetBIOS protocol for network communication between Windows devices");
        INFO.put(139, "NetBIOS protocol for network communication between Windows devices");
        INFO.put(8080, "HTTP proxy server");
        INFO.put(1080, "SOCKS proxy server");
        INFO.put(1433, "Microsoft SQL Server database server");
        INFO.put(389, "Lightweight Directory Access Protocol for directory services");
        INFO.put(636, "Lightweight Directory Access Protocol for directory services");
        INFO.put(5722, "SMB version 2 protocol");
        INFO.put(500, "Internet Key Exchange protocol for VPN connections");
        INFO.put(1701, "Layer 2 Tunneling Protocol for VPN connections");
        INFO.put(1723, "Point-to-Point Tunneling Protocol for VPN connections");
        INFO.put(5060, "Session Initiation Protocol for VoIP communication");
        INFO.put(5061, "Session Initiation Protocol for VoIP communication");
        INFO.put(16384, "Real-time Transport Protocol for audio and video transmission");
        INFO.put(3128, "HTTPS proxy server");
        INFO.put(5900, "Virtual Network Computing for remote access");
        INFO.put(6697, "Internet Relay Chat for real-time communication");
        INFO.put(2049, "Network File System for file sharing");
        INFO.put(6379, "Redis key-value store");
        INFO.put(11211, "Memcached distributed memory caching system");
        INFO.put(873, "Remote synchronization for file transfers");
        INFO.put(5222, "Extensible Messaging and Presence Protocol for instant messaging");
        INFO.put(5223, "Extensible Messaging and Presence Protocol for instant messaging");
    }

    private PortInfoUtil() {
    }

    public static String getByPortNum(int port) {
        return INFO.getOrDefault(port, "N/A");
    }
}
