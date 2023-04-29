package com.aldia.aldia.Controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;

public class DatabaseController {

    private DatabaseController() {

    }

    private static DatabaseController database;

    public static DatabaseController getInstance() {
        if (database == null) {
            database = new DatabaseController();
        }
        return database;
    }

    public DatabaseReference inicializarBD() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("token.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://aldia-bbdd-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/USUARIOS");

//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Object document = dataSnapshot.getValue();
//                System.out.println(document);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });

        return ref;
    }
}
