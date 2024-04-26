package com.project.stone.sha;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SHA256 {

    public static String getSHA256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}