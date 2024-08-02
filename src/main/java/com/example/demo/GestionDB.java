package com.example.demo;

import jdk.internal.foreign.SystemLookup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionDB{
    private Connection connection;

    public GestionDB() throws SQLException {
        // Assume that com.example.demo.DatabaseSingleton est déjà configuré pour fournir une connexion
        this.connection = (Connection) DatabaseSingleton.getInstance();
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Livre ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "titre VARCHAR(100), "
                + "auteur VARCHAR(100))"; // Assure une longueur suffisante pour les colonnes

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'Livre' créée.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertLivre(String titre, String auteur) {
        String insertSQL = "INSERT INTO Livre (titre, auteur) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, titre);
            pstmt.setString(2, auteur);
            pstmt.executeUpdate();
            System.out.println("Livre inséré.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateLivre(int id, String titre, String auteur) {
        String updateSQL = "UPDATE Livre SET titre = ?, auteur = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, titre);
            pstmt.setString(2, auteur);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Livre mis à jour.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteLivre(int id) {
        String deleteSQL = "DELETE FROM Livre WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Livre supprimé.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultSet selectLivres() {
        String selectSQL = "SELECT * FROM Livre";

        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(selectSQL);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sélection des livres : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet researchLivre(String titre) {
        String sql = "SELECT * FROM Livre WHERE titre = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titre);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du livre : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet listerLivres(String titrePrefix) {
        String sql = "SELECT * FROM Livre WHERE titre LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titrePrefix + "%");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la liste des livres : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet afficherLivres(String categorie) {
        String sql = "SELECT * FROM Livre WHERE categorie = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, categorie);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des livres : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet afficherDetail(int id) {
        String sql = "SELECT * FROM Livre WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des détails du livre : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
