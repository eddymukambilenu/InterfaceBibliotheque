package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/Bibliothequesss"; // Remplacez par l'URL de votre base de données
    private static final String USER = "root"; // Remplacez par votre nom d'utilisateur
    private static final String PASSWORD = "votre_mot_de_passe"; // Remplacez par votre mot de passe

    // Constructeur privé pour empêcher l'instanciation externe
    private DatabaseSingleton() {
        try {
            // Assurez-vous d'avoir le driver JDBC approprié dans votre classpath
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    // Méthode pour obtenir l'unique instance de com.example.demo.DatabaseSingleton
    public static DatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConnection() {
        return connection;
    }

    // Méthode pour fermer la connexion (optionnelle)
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion à la base de données fermée.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
