package com.aldia.aldia.Controller;

import com.aldia.aldia.model.Usuario;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorUsuarios {

    private static DatabaseReference usersRef;

    private GestorUsuarios() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("/USUARIOS");
    }

    private static GestorUsuarios gestorUsuarios;

    public static GestorUsuarios getInstance() {
        if (gestorUsuarios == null ){
            gestorUsuarios = new GestorUsuarios();
        }
        return gestorUsuarios;
    }

    public void add(Usuario usuario) {

        /* Crea el usuario en Firebase */
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(usuario.getEmail())
                .setPassword(usuario.getPassword());

        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully created new user: " + userRecord.getUid());
        usuario.setUid(userRecord.getUid());

        /* Crea el usuario en la base de datos */
        Map<String, Usuario> users = new HashMap<>();
        users.put(usuario.getUsername(), usuario);

        usersRef.setValueAsync(users);
    }

    public void remove(String uid) {
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully deleted user.");
    }

    public List<String> getUsuarios() {
        List<String> usuarios = new ArrayList<>();
        try {
            ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
            while (page != null) {
                for (ExportedUserRecord user : page.getValues()) {
                    System.out.println("User: " + user.getEmail());
                    usuarios.add(user.getEmail());
                }
                page = page.getNextPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public String getUsuario(String email) {
        try {
            return FirebaseAuth.getInstance().getUserByEmail(email).getEmail();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }


}
