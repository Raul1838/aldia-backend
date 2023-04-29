package com.aldia.aldia;

import com.aldia.aldia.Controller.DatabaseController;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.aldia.aldia.Controller.GestorUsuarios;
import com.aldia.aldia.Controller.Sesion;
import com.aldia.aldia.model.Usuario;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrarUsuarioTest {

	private static GestorUsuarios gestUsers;
    private static Sesion sesion;

	@BeforeAll
    static void init() throws Exception {
        DatabaseController databaseController = DatabaseController.getInstance();
        databaseController.inicializarBD();
        gestUsers = GestorUsuarios.getInstance();
    //     gestUsers.remove("user@mail.com");
    }
     @AfterEach
     void clean() throws Exception {
    //     gestUsers.remove("user@mail.com");
    }

    @Test
    void obtenerUsuarios() throws Exception {
        gestUsers.getUsuarios();
    }

    @Test
    void registrarUsuarioTest_usuarioValido() throws Exception {
        Usuario usuarioTest = new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", "testus");
        gestUsers.add(usuarioTest);
        assertEquals(usuarioTest, gestUsers.getUsuario("UserUS"));
    }

    @Test
    void registrarUsuarioTest_passwordInvalido() throws Exception {
        gestUsers.add(new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", ""));
        assertNull(gestUsers.getUsuario("UserUS"));
    }

    @Test
    void registrarUsuarioTest_nombreUsuarioExistente() throws Exception {
        // Usuario que existe
        gestUsers.add(new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", "testus"));

        // Usuario que registra con nombre de usuario existente
        assertThrows(FirebaseAuthException.class, ()->{gestUsers.add(new Usuario("UserUS", "ContraDeUS", "User", "user@mail.com", "testus"));});
    }

}
