package com.projet.quizizback.app.quizzback.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("Initializing Firebase App...");
        ClassPathResource serviceAccount = new ClassPathResource("quiziz-reactnative-firebase.json");
        log.info("Service account file exists: {}", serviceAccount.exists());

        if (!serviceAccount.exists()) {
            log.error("Service account file not found!");
            throw new IOException("Service account file not found!");
        }

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);
                log.info("Firebase App initialized successfully. App name: {}", app.getName());
                return app;
            }
            FirebaseApp app = FirebaseApp.getInstance();
            log.info("Firebase App already initialized. App name: {}", app.getName());
            return app;
        } catch (IOException e) {
            log.error("Error initializing Firebase App", e);
            throw e;
        }
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        log.info("Creating Firestore bean...");
        Firestore firestore = FirestoreClient.getFirestore(firebaseApp);
        log.info("Firestore bean created successfully.");
        return firestore;
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        log.info("Initializing FirebaseAuth...");
        FirebaseAuth auth = FirebaseAuth.getInstance(firebaseApp);
        log.info("FirebaseAuth initialized successfully.");
        return auth;
    }
}