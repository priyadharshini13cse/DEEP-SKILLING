package com.cognizant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/{number}")
    public Map<String, Object> getAccountDetails(@PathVariable String number) {
        // Return dummy JSON response as requested
        Map<String, Object> response = new HashMap<>();
        response.put("number", "00987987973432");
        response.put("type", "savings");
        response.put("balance", 234343);
        
        return response;
    }
}
