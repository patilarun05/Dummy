package com.example.Dummy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @GetMapping("/Welcome")
    public String getData() {return  "Welcome to DevOps Arun Patil" ; }
}