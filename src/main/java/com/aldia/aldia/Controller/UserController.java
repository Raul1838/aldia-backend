package com.aldia.aldia.Controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @GetMapping("/")
    public void prueba() {
        System.out.println("Ha llegado la pión");
    }
}
