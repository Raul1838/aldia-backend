package com.aldia.aldia.Controller;

import com.aldia.aldia.model.Usuario;
import com.google.api.client.util.Data;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;

import java.util.*;

import org.apache.commons.logging.Log;

public class GestorUsuarios {

    private static DatabaseReference usersRef;
    private ArrayList<Usuario> usuarios;

    private GestorUsuarios() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("/USUARIOS");
        usuarios = new ArrayList<Usuario>();

    }

    private static GestorUsuarios gestorUsuarios;

    public static GestorUsuarios getInstance() {
        if (gestorUsuarios == null ){
            gestorUsuarios = new GestorUsuarios();
        }
        return gestorUsuarios;
    }

    public void add(Usuario usuario) throws FirebaseAuthException {
        if (!Objects.equals(usuario.getPassword(), "")) {
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
    }

    public void remove(String username) {
        Query usuario = usersRef.orderByKey().equalTo(username);

        usuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
//                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new UnsupportedOperationException("Unimplemented method 'onCancelled'");
            }
        });
    }

    public Usuario getUsuario(String username) {
        final Usuario[] usuario = new Usuario[1];
        usersRef.orderByKey().equalTo(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                usuario[0] = dataSnapshot.getValue(Usuario.class);
                System.out.println(usuario[0]);
            }

            @Override
            public void onCancelled(DatabaseError arg0) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onCancelled'");
            }

            @Override
            public void onChildChanged(DataSnapshot arg0, String arg1) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildChanged'");
            }

            @Override
            public void onChildMoved(DataSnapshot arg0, String arg1) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildMoved'");
            }

            @Override
            public void onChildRemoved(DataSnapshot arg0) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildRemoved'");
            }
        });

        return usuario[0];
    }


    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usersRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                usuarios.add(user);
                System.out.println(usuarios);
            }

            @Override
            public void onCancelled(DatabaseError arg0) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onCancelled'");
            }

            @Override
            public void onChildChanged(DataSnapshot arg0, String arg1) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildChanged'");
            }

            @Override
            public void onChildMoved(DataSnapshot arg0, String arg1) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildMoved'");
            }

            @Override
            public void onChildRemoved(DataSnapshot arg0) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onChildRemoved'");
            }
        });
        return usuarios;
    }


}
