package com.darku.security.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Catalin on 21.03.2022
 */

@RestController
public class AppController {

    @GetMapping("/protected/main")
    public String main() {
        return "PROTECTED CONTROLLER";
    }

    @GetMapping("/protected-by-super-role/hello")
    public String superRoleProtected() {
        return "Hey";
    }
}
