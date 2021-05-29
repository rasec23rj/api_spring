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

    @GetMapping("")
    public String hello(HttpServletRequest request) throws UnknownHostException {
        String ipAddressX = request.getHeader("X-FORWARDED-FOR");
        if (ipAddressX == null) {
            ipAddressX = request.getRemoteAddr();
        }
        System.out.println("ipAddressX" + ipAddressX);
        // System.out.println("ipAddresss " + ipAddresss);
        return String.format("\t\t-  getRemoteAddr: %s", ipAddressX);
    }

}
