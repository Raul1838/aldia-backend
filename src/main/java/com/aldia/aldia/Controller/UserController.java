package com.aldia.aldia.Controller;


import com.aldia.aldia.model.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @GetMapping("/")
    public void prueba() {
        System.out.println("Ha llegado la pión");
    }

    @GetMapping("/add")
    public String registrar(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Hola");
        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
        gestorUsuarios.add(new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", "testus"));
        return "funciona";
    }

}
