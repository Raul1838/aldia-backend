package com.aldia.aldia.Controller;


import com.aldia.aldia.model.Usuario;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @GetMapping("/add")
    public String registrar(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Registrando usuario");
        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
        try {
            gestorUsuarios.add(new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", "testus"));
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        return "funciona";
    }

    @GetMapping("/listAll")
    public List<Usuario> obtenerTodosUsuarios(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Obteniendo todos los usuarios");
        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
        return gestorUsuarios.getUsuarios();
    }

    @GetMapping("/getUser")
    public Usuario obtenerUsuario(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Obteniendo usuario");
        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
        Usuario usuario = gestorUsuarios.getUsuario("UserUS");
        System.out.println(usuario);
        return usuario;
    }

}
