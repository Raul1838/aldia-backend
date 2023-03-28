package com.aldia.aldia.Controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public class Sesion {
    private static Sesion sesion;

    private UserRecord user;

    private Sesion() {

    }

    public static Sesion getInstance() {
        if (sesion == null) {
            sesion = new Sesion();
        }
        return sesion;
    }

    public String login(String idToken) {
        try {
            // idToken comes from the client app (front end)
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            this.user = FirebaseAuth.getInstance().getUser(uid);
            return FirebaseAuth.getInstance().getUser(uid).getEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserRecord getSesion() {
        return user;
    }

    public void cerrarSesion() {
        try {
            FirebaseAuth.getInstance().revokeRefreshTokens(user.getUid());
            // Convert to seconds as the auth_time in the token claims is in seconds too.
            long revocationSecond = user.getTokensValidAfterTimestamp() / 1000;
            System.out.println("Tokens revoked at: " + revocationSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}