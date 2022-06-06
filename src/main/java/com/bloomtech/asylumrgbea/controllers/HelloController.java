package com.bloomtech.asylumrgbea.controllers;

import java.util.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        byte[] decodedBytes = Base64.getDecoder().decode("VGltZTJjb2RlIQ==");
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
