package com.example.demo.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientId")
public class ClientIdRemote {

    private String loopbackHost = "";
    private String host = "";

    private String loopbackIp = "";
    private String ip = "";

    @GetMapping("")
    public String hello(HttpServletRequest request) throws UnknownHostException {
        String ipAddress = request.getRemoteAddr();
        System.out.println("IP Address: " + ipAddress);
        System.out.println("request: " + request.getRemoteHost());
        InetAddress IP = Inet4Address.getLocalHost();
        System.out.println(IP.getHostAddress());
        System.out.println(IP);

        Map<String, String> result = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }

        String ipAddresss = null;

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {

                NetworkInterface networkInterface = networkInterfaces.nextElement();

                byte[] hardwareAddress = networkInterface.getHardwareAddress();
                if (null == hardwareAddress || 0 == hardwareAddress.length
                        || (0 == hardwareAddress[0] && 0 == hardwareAddress[1] && 0 == hardwareAddress[2]))
                    continue;

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                if (inetAddresses.hasMoreElements())
                    ipAddresss = inetAddresses.nextElement().toString();

                break;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface i = interfaces.nextElement();
                if (i != null) {
                    Enumeration<InetAddress> addresses = i.getInetAddresses();
                    // System.out.println(i.getDisplayName());
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        String hostAddr = address.getHostAddress();

                        // local loopback
                        if (hostAddr.indexOf("127.") == 0) {
                            this.loopbackIp = address.getHostAddress();
                            this.loopbackHost = address.getHostName();
                        }
                        // internal ip addresses (behind this router)
                        if (hostAddr.indexOf("192.168") == 0 || hostAddr.indexOf("10.") == 0
                                || hostAddr.indexOf("172.16") == 0) {
                            this.host = address.getHostName();
                            this.ip = address.getHostAddress();
                        }
                        System.out.println(this.ip);
                        System.out.println("\t\t-" + address.getHostName() + ":" + address.getHostAddress()
                                + " - getAddress " + address.getAddress());
                    }
                }
            }
        } catch (SocketException e) {

        }
        try {
            InetAddress loopbackIpAddress = InetAddress.getLocalHost();
            this.loopbackIp = loopbackIpAddress.getHostName();
            // System.out.println("LOCALHOST: " + loopbackIp);
        } catch (UnknownHostException e) {
            // System.err.println("ERR: " + e.toString());
        }
        String ipAddressX = request.getHeader("X-FORWARDED-FOR");  
        if (ipAddressX == null) {  
            ipAddressX = request.getRemoteAddr();  
        }
System.out.println("ipAddressX" + ipAddressX);
        // System.out.println("ipAddresss " + ipAddresss);
        return String.format("\t\t- getRemoteAddr: %s, getLocalHost: %s, IP: %s, getRemoteHost: %s, %s", ipAddress,
                IP.getHostAddress(), IP, request.getRemoteHost(), this.ip);
    }

}
