package br.com.corpia.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> status() {
        return Map.of(
                "status", "online",
                "service", "corpia-api"
        );
    }
}
